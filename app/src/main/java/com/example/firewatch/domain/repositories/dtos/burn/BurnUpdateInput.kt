package com.example.firewatch.domain.repositories.dtos.burn

import com.example.firewatch.domain.valueObjects.BurnReason
import com.example.firewatch.domain.valueObjects.BurnType
import com.example.firewatch.domain.valueObjects.Coordinates
import com.example.firewatch.shared.MultipartRequest
import com.example.firewatch.shared.extensions.toFormData
import okhttp3.MultipartBody
import java.time.LocalDateTime

data class BurnUpdateInput(
    val id: String,
    val title: String?,
    val coordinates: Coordinates?,
    val reason: BurnReason?,
    val type: BurnType?,
    val hasBackUpTeam: Boolean?,
    val initialPropose: String?,
    val initDate: LocalDateTime?,
) : MultipartRequest {
    override fun toMultipart(): MultipartBody {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)

        title?.let {
            requestBody.addPart(it.toFormData("title"))
        }

        reason?.let {
            requestBody.addPart(reason.toFormData())
        }

        coordinates?.let {
            requestBody.addPart(coordinates.lat.toFormData("lat"))
        }

        coordinates?.let {
            requestBody.addPart(coordinates.lon.toFormData("lon"))
        }

        type?.let {
            requestBody.addPart(type.toFormData())
        }

        hasBackUpTeam?.let {
            requestBody.addPart(hasBackUpTeam.toFormData("has_backup_team"))
        }

        initialPropose?.let {
            requestBody.addPart(initialPropose.toFormData("initial_propose"))
        }

        initDate?.let {
            requestBody.addPart(initDate.toFormData("init_date"))
        }

        return requestBody.build()
    }
}