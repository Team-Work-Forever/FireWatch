package com.example.firewatch.context.auth.dtos

import com.example.firewatch.domain.valueObjects.Address
import com.example.firewatch.domain.valueObjects.Coordinates
import com.example.firewatch.domain.valueObjects.Phone
import com.example.firewatch.shared.MultipartRequest
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

data class AutarchyUpdateInput(
    val id: String,
    val email: String,
    val name: String,
    val coordinates: Coordinates,
    val phone: Phone,
    val address: Address,
    val avatarFile: File,
) : MultipartRequest {
    override fun toMultipart(): MultipartBody {
       return MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("email", email)
            .addFormDataPart("title", name)
            .addFormDataPart("lat", coordinates.lat.toPlainString())
            .addFormDataPart("lon", coordinates.lon.toPlainString())
            .addFormDataPart("phone_code", phone.countryCode)
            .addFormDataPart("phone_number", phone.number)
            .addFormDataPart("street", address.street)
            .addFormDataPart("street_port", address.number.toString())
            .addFormDataPart("zip_code", address.zipCode)
            .addFormDataPart("city", address.city)
            .addFormDataPart(
                "avatar",
                avatarFile.name,
                RequestBody.create(MediaType.get("image/${avatarFile.extension}"), avatarFile)
            )
            .build()
    }
}