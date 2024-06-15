package com.example.firewatch.context.auth.dtos

import com.example.firewatch.shared.MultipartRequest
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

data class SignUpInput(
    var nif: String,
    var email: String,
    var userName: String,
    var password: String,
    var firstName: String,
    var lastName: String,
    var phoneCode: String,
    var phoneNumber: String,
    var street: String,
    var streetNumber: String,
    var zipCode: String,
    var city: String,
    var avatarFile: File,
) : MultipartRequest {

    override fun toMultipart(): MultipartBody {
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
                RequestBody.create("image/${avatarFile.extension}".toMediaType(), avatarFile)
            )
            .build()
    }
}