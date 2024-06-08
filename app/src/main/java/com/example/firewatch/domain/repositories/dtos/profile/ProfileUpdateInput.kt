package com.example.firewatch.domain.repositories.dtos.profile

import com.example.firewatch.domain.valueObjects.Address
import com.example.firewatch.domain.valueObjects.Phone
import com.example.firewatch.shared.MultipartRequest
import com.example.firewatch.shared.extensions.toFormData
import okhttp3.MultipartBody
import java.io.File

data class ProfileUpdateInput(
    val email: String?,
    val avatar: File?,
    val userName: String?,
    val firstName: String?,
    val lastName: String?,
    val phone: Phone?,
    val address: Address?,
) : MultipartRequest {
    override fun toMultipart(): MultipartBody {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)

        email?.let {
            requestBody.addPart(it.toFormData("email"))
        }

        userName?.let {
            requestBody.addPart(it.toFormData("user_name"))
        }

        phone?.let {
            requestBody.addPart(it.countryCode.toFormData("phone_code"))
            requestBody.addPart(it.number.toFormData("phone_number"))
        }

        address?.let {
            requestBody.addPart(address.street.toFormData("street"))
            requestBody.addPart(address.number.toFormData("street_port"))
            requestBody.addPart(address.zipCode.toFormData("zip_code"))
            requestBody.addPart(address.city.toFormData("city"))
        }

        avatar?.let {
            requestBody.addPart(avatar.toFormData("avatar"))
        }

        return requestBody.build()
    }
}