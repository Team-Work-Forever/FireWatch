package com.example.firewatch.domain.repositories.dtos.autarchy

import com.example.firewatch.domain.valueObjects.Address
import com.example.firewatch.domain.valueObjects.Coordinates
import com.example.firewatch.domain.valueObjects.Phone
import com.example.firewatch.shared.MultipartRequest
import com.example.firewatch.shared.extensions.toFormData
import okhttp3.MultipartBody
import java.io.File

data class AutarchyUpdateInput(
    val id: String,
    val email: String? = null,
    val name: String? = null,
    val coordinates: Coordinates? = null,
    val phone: Phone? = null,
    val address: Address? = null,
    val avatarFile: File? = null,
) : MultipartRequest {
    override fun toMultipart(): MultipartBody {
        var requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)

        email?.let {
            requestBody.addPart(email.toFormData("email"))
        }

        name?.let {
            requestBody.addPart(name.toFormData("title"))
        }

        coordinates?.let {
            requestBody.addPart(coordinates.lat.toFormData("lat"))
            requestBody.addPart(coordinates.lon.toFormData("lon"))
        }

        phone?.let {
            requestBody.addPart(phone.countryCode.toFormData("phone_code"))
            requestBody.addPart(phone.number.toFormData("phone_number"))
        }

        address?.let {
            requestBody.addPart(address.street.toFormData("street"))
            requestBody.addPart(address.number.toFormData("street_port"))
            requestBody.addPart(address.zipCode.toFormData("zip_code"))
            requestBody.addPart(address.city.toFormData("city"))
        }

        avatarFile?.let {
            requestBody.addPart(avatarFile.toFormData("avatar"))
        }

        return requestBody.build()
    }
}