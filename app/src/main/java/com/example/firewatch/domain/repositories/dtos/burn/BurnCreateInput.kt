package com.example.firewatch.domain.repositories.dtos.burn

import com.example.firewatch.domain.valueObjects.*
import com.example.firewatch.shared.MultipartRequest
import com.example.firewatch.shared.extensions.toFormData
import okhttp3.MultipartBody
import java.time.LocalDateTime

data class BurnCreateInput(
    val title: String,
    val coordinates: Coordinates,
    val reason: BurnReason,
    val type: BurnType,
    val hasBackUpTeam: Boolean,
    val initialPropose: String,
    val initDate: LocalDateTime,
) : MultipartRequest {
    override fun toMultipart(): MultipartBody {
        return MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addPart(title.toFormData("title"))
            .addPart(reason.toFormData())
            .addPart(coordinates.lat.toFormData("lat"))
            .addPart(coordinates.lon.toFormData("lon"))
            .addPart(type.toFormData())
            .addPart(hasBackUpTeam.toFormData("has_backup_team"))
            .addPart(initialPropose.toFormData("initial_propose"))
            .addPart(initDate.toFormData("init_date"))
            .build()
    }
}