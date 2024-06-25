package com.example.firewatch.presentation.views.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.firewatch.BuildConfig
import com.example.firewatch.R
import com.example.firewatch.databinding.ActivityMapBinding
import com.example.firewatch.domain.valueObjects.Coordinates
import com.example.firewatch.presentation.viewModels.map.MapViewModel
import com.example.firewatch.shared.extensions.getProblem
import com.example.firewatch.shared.extensions.toCoordinates
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap.SnapshotReadyCallback
import com.tomtom.sdk.location.GeoPoint
import com.tomtom.sdk.location.android.AndroidLocationProvider
import com.tomtom.sdk.location.android.AndroidLocationProviderConfig
import com.tomtom.sdk.map.display.MapOptions
import com.tomtom.sdk.map.display.TomTomMap
import com.tomtom.sdk.map.display.camera.CameraOptions
import com.tomtom.sdk.map.display.copyright.CopyrightsFetchingCallback
import com.tomtom.sdk.map.display.copyright.CopyrightsFetchingFailure
import com.tomtom.sdk.map.display.image.ImageFactory
import com.tomtom.sdk.map.display.location.LocationMarkerOptions
import com.tomtom.sdk.map.display.marker.Marker
import com.tomtom.sdk.map.display.marker.MarkerOptions
import com.tomtom.sdk.map.display.style.StandardStyles
import com.tomtom.sdk.map.display.style.StyleMode
import com.tomtom.sdk.map.display.ui.MapFragment
import com.tomtom.sdk.map.display.ui.MapView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.time.Duration.Companion.seconds


@AndroidEntryPoint
class MapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapBinding
    private lateinit var tomTomMap: TomTomMap
    private var burnLocation: Marker? = null

    private val viewModel: MapViewModel by viewModels()

    companion object {
        private const val BURN_LOCATION_MARKER = "burn-location"

        const val LAT_RESULT = "lat-result"
        const val LON_RESULT = "lon-result"
        const val PICTURE_RESULT = "picture"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_map)

        val mapOptions = MapOptions(
            mapKey = BuildConfig.TOMTOM_API_KEY,

            mapStyle = StandardStyles.SATELLITE
        )

        val mapFragment = MapFragment.newInstance(mapOptions)

        supportFragmentManager.beginTransaction()
            .replace(R.id.map_fragment, mapFragment)
            .commit()

        mapFragment.getMapAsync { tomTomMap: TomTomMap ->
            this.tomTomMap = tomTomMap

            setUpMap(mapFragment)
            setUserLocation()

            tomTomMap.addMapLongClickListener { coordinate: GeoPoint ->
                if (isMarkerSet()) {
                    removeMarker()
                }

                return@addMapLongClickListener addMarker(coordinate)
            }

            tomTomMap.addMarkerClickListener { _ ->
                setPosition()
            }
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.saveBtn.setOnClickListener {
            if (!isMarkerSet()) {
                Toast.makeText(this, "Por favor introduza uma coordenada", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val picture = takePicture(mapFragment)

            val intent = Intent().apply {
                putExtra(LAT_RESULT, burnLocation?.coordinate?.latitude)
                putExtra(LON_RESULT, burnLocation?.coordinate?.longitude)
                putExtra(PICTURE_RESULT, picture)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun setUpMap(mapFragment: MapFragment) {
        mapFragment.currentLocationButton.addCurrentLocationButtonClickListener {
            setPosition()
        }

        tomTomMap.setStyleMode(StyleMode.DARK)
    }

    @SuppressLint("ResourceAsColor")
    private fun takePicture(mapFragment: MapFragment): File {
        val mapView = mapFragment.view ?: throw Exception("")

        val bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        mapView.draw(canvas)

        val filename = "screenshot_${System.currentTimeMillis()}.png"
        val directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File(directory, filename)

        try {
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return file
    }

    private fun addMarker(coordinate: GeoPoint): Boolean {
        lifecycleScope.launch {
            val checkBurn = viewModel.check(coordinate.toCoordinates()).await()

            if (checkBurn.isFailure) {
                Toast.makeText(this@MapActivity, checkBurn.getProblem(), Toast.LENGTH_LONG).show()

                return@launch
            }

            if (!checkBurn.getOrThrow()) {
                Toast.makeText(this@MapActivity, "Is not possible to make an burn there", Toast.LENGTH_LONG).show()

                return@launch
            }

            val markerOptions = MarkerOptions(
                tag = BURN_LOCATION_MARKER,
                coordinate = coordinate,
                pinImage = ImageFactory.fromResource(R.drawable.location_dot_solid),
                balloonText = "Local da Queimada",
            )

            burnLocation = tomTomMap.addMarker(markerOptions)
        }

        return true
    }

    private fun removeMarker(): Boolean {
        tomTomMap.removeMarkers(BURN_LOCATION_MARKER)
        burnLocation = null

        return true
    }

    private fun isMarkerSet(): Boolean = burnLocation != null

    private fun setPosition() {
        tomTomMap.animateCamera(CameraOptions(
            position = burnLocation?.coordinate,
            zoom = 15.0,
            tilt = 45.0,
            rotation = 90.0,
        ), 3.seconds
        )
    }

    private fun setUserLocation() {
        val locationProvider = AndroidLocationProvider(this, AndroidLocationProviderConfig())
        tomTomMap.setLocationProvider(locationProvider)

        val locationMarkerOptions =
            LocationMarkerOptions(
                type = LocationMarkerOptions.Type.Chevron,
                markerMagnification = 20.0
            )

        tomTomMap.disableLocationMarker()
        tomTomMap.enableLocationMarker(locationMarkerOptions)

        val userLocation = LocationServices.getFusedLocationProviderClient(this)

        val checkFineLocation = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED

        val checkCoarse =  ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED

        if (checkFineLocation && checkCoarse) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
        }

        userLocation.lastLocation.addOnSuccessListener { location ->
            tomTomMap.moveCamera(
                CameraOptions(
                    position = GeoPoint(
                        location.latitude,
                        location.longitude
                    ),
                    zoom = 15.0
                )
            )
        }
    }
}