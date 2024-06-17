package com.example.firewatch.services.http.serializeres

import com.example.firewatch.domain.valueObjects.UserType
import com.example.firewatch.services.http.contracts.profile.AutarchyProfileResponse
import com.example.firewatch.services.http.contracts.profile.ProfileResultResponse
import com.example.firewatch.services.http.contracts.profile.UserProfileResponse
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class ProfileSerializer : JsonDeserializer<ProfileResultResponse> {
    companion object {
        private const val EXCEPTION_MESSAGE = "Could not parse the following json"
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ProfileResultResponse {
        if (json == null || context == null) {
            throw JsonParseException(EXCEPTION_MESSAGE)
        }

        val jsonObject = json.asJsonObject
        val userTypeString = jsonObject!!.get("user_type")?.asString ?: ""

        val userType = UserType.get(userTypeString) ?: throw JsonParseException(EXCEPTION_MESSAGE)

        return when (userType) {
            UserType.AUTARCHY -> context.deserialize(json, AutarchyProfileResponse::class.java)
            UserType.USER -> context.deserialize(json, UserProfileResponse::class.java)
            UserType.ADMIN -> context.deserialize(json, UserProfileResponse::class.java)
            else -> throw JsonParseException("Unknown user type: $userType")
        }
    }
}