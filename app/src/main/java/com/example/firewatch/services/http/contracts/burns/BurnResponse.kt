package com.example.firewatch.services.http.contracts.burns

import com.example.firewatch.domain.entities.Burn
import com.example.firewatch.domain.valueObjects.BurnReason
import com.example.firewatch.domain.valueObjects.BurnState
import com.example.firewatch.domain.valueObjects.BurnType
import com.example.firewatch.domain.valueObjects.Coordinates
import com.example.firewatch.services.http.contracts.profile.PublicProfileResponse
import com.example.firewatch.services.http.contracts.valueObjects.AddressResponse
import com.example.firewatch.shared.errors.BurnReasonNotExists
import com.example.firewatch.shared.errors.BurnStateNotExists
import com.example.firewatch.shared.errors.BurnTypeNotExists
import com.example.firewatch.shared.utils.DateUtils
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class BurnResponse(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("has_aid_team") val hasAidTeam: Boolean,
    @SerializedName("reason") val reason: String,
    @SerializedName("type") val type: String,
    @SerializedName("begin_at") val beginAt: String,
    @SerializedName("completedAt") val completedAt: String?,
    @SerializedName("map_picture") val mapPicture: String,
    @SerializedName("state") val state: String,
    @SerializedName("address") val address: AddressResponse,
    @SerializedName("author") val publicProfile: PublicProfileResponse? = null,
) {
    fun toBurn(coordinates: Coordinates): Burn {
        val reason = BurnReason.get(reason) ?: throw BurnReasonNotExists(reason)
        val type = BurnType.get(type) ?: throw BurnTypeNotExists(type)
        val state = BurnState.get(state) ?: throw BurnStateNotExists(state)

        return Burn.createWithProfile(
            id,
            title,
            coordinates,
            hasAidTeam,
            reason,
            type,
            address.toAddress(),
            DateUtils.convertFromISO(beginAt),
            completedAt?.let { DateUtils.convertFromISO(completedAt) },
            mapPicture,
            state,
            publicProfile?.toPublicProfile()
        )
    }
}