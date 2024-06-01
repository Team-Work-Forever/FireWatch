package com.example.firewatch.domain.repositories.dtos.profile

import com.example.firewatch.domain.valueObjects.Address
import com.example.firewatch.domain.valueObjects.Phone
import com.example.firewatch.shared.MultipartRequest
import com.example.firewatch.shared.extensions.toFormData
import okhttp3.MultipartBody
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
            .addPart(email.toFormData("email"))
            .addPart(userName.toFormData("user_name"))
            .addPart(firstName.toFormData("first_name"))
            .addPart(lastName.toFormData("last_name"))
            .addPart(phone.countryCode.toFormData("phone_code"))
            .addPart(phone.number.toFormData("phone_number"))
            .addPart(address.street.toFormData("street"))
            .addPart(address.number.toFormData("street_port"))
            .addPart(address.zipCode.toFormData("zip_code"))
            .addPart(address.city.toFormData("city"))
            .addPart(avatar.toFormData("avatar"))
            .build()
    }
}