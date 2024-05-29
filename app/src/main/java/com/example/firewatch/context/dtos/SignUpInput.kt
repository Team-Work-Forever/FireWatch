package com.example.firewatch.context.dtos

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

data class SignUpInput(
    val nif: String,
    val email: String,
    val userName: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val phoneCode: String,
    val phoneNumber: String,
    val street: String,
    val streetNumber: String,
    val zipCode: String,
    val city: String,
    val avatarFile: File,
) {

    fun getResponseBody(): MultipartBody {
        return MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("nif", nif)
            .addFormDataPart("email", email)
            .addFormDataPart("user_name", userName)
            .addFormDataPart("password", password)
            .addFormDataPart("first_name", firstName)
            .addFormDataPart("last_name", lastName)
            .addFormDataPart("phone_code", phoneCode)
            .addFormDataPart("phone_number", phoneNumber)
            .addFormDataPart("street", street)
            .addFormDataPart("street_port", streetNumber)
            .addFormDataPart("zip_code", zipCode)
            .addFormDataPart("city", city)
            .addFormDataPart(
                "avatar",
                avatarFile.name,
                RequestBody.create(MediaType.get("image/${avatarFile.extension}"), avatarFile)
            )
            .build()
    }
}