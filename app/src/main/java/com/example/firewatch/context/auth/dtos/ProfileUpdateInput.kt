package com.example.firewatch.context.auth.dtos

import com.example.firewatch.domain.valueObjects.Address
import com.example.firewatch.domain.valueObjects.Phone
import com.example.firewatch.shared.MultipartRequest
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

data class ProfileUpdateInput(
    val email: String,
    val avatar: File,
    val userName: String,
    val firstName: String,
    val lastName: String,
    val phone: Phone,
    val address: Address,
) : MultipartRequest {
    override fun toMultipart(): MultipartBody {
        return MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("email", email)
            .addFormDataPart("user_name", userName)
            .addFormDataPart("first_name", firstName)
            .addFormDataPart("last_name", lastName)
            .addFormDataPart("phone_code", phone.countryCode)
            .addFormDataPart("phone_number", phone.number)
            .addFormDataPart("street", address.street)
            .addFormDataPart("street_port", address.number.toString())
            .addFormDataPart("zip_code", address.zipCode)
            .addFormDataPart("city", address.city)
            .addFormDataPart(
                "avatar",
                avatar.name,
                RequestBody.create(MediaType.get("image/${avatar.extension}"), avatar)
            )
            .build()
    }
}