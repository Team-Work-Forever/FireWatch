package com.example.firewatch.domain.repositories.dtos.burn

import com.example.firewatch.domain.entities.Burn
import com.example.firewatch.domain.valueObjects.*
import com.example.firewatch.shared.MultipartRequest
import com.example.firewatch.shared.extensions.toFormData
import okhttp3.MultipartBody
import java.io.File
import java.time.LocalDateTime

data class BurnCreateInput(
    val title: String,
    val coordinates: Coordinates,
    val reason: BurnReason,
    val type: BurnType,
    val hasBackUpTeam: Boolean,
    val initialPropose: String,
    val initDate: LocalDateTime,
    val mapPicture: File? = null
) : MultipartRequest {
    fun toBurn(id: String, state: BurnState): Burn {
        return Burn.create(
            id,
            title,
            coordinates,
            hasBackUpTeam,
            reason,
            type,
            Address.empty(),
            initDate,
            null,
            "",
            state
        )
    }
    override fun toMultipart(): MultipartBody {
        val builder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addPart(title.toFormData("title"))
            .addPart(reason.toFormData())
            .addPart(coordinates.lat.toFormData("lat"))
            .addPart(coordinates.lon.toFormData("lon"))
            .addPart(type.toFormData())
            .addPart(hasBackUpTeam.toFormData("has_backup_team"))
            .addPart(initialPropose.toFormData("initial_propose"))
            .addPart(initDate.toFormData("init_date"))
            .addPart(true.toFormData("ignore"))

        if (mapPicture != null) {
            builder.addPart(mapPicture.toFormData("avatar"))
        }

        return builder.build()
    }
}