package com.example.firewatch.domain.repositories.dtos.autarchy

import com.example.firewatch.domain.valueObjects.Address
import com.example.firewatch.domain.valueObjects.Coordinates
import com.example.firewatch.domain.valueObjects.Phone
import com.example.firewatch.shared.MultipartRequest
import com.example.firewatch.shared.extensions.toFormData
import okhttp3.MultipartBody
import java.io.File

data class AutarchyCreateInput(
    val nif: String,
    val email: String,
    val name: String,
    val coordinates: Coordinates,
    val phone: Phone,
    val address: Address,
    val avatarFile: File,
) : MultipartRequest {
    override fun toMultipart(): MultipartBody {
        return MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addPart(nif.toFormData("nif"))
            .addPart(email.toFormData("email"))
            .addPart(name.toFormData("title"))
            .addPart(coordinates.lat.toFormData("lat"))
            .addPart(coordinates.lon.toFormData("lon"))
            .addPart(phone.countryCode.toFormData("phone_code"))
            .addPart(phone.number.toFormData("phone_number"))
            .addPart(address.street.toFormData("street"))
            .addPart(address.number.toFormData("street_port"))
            .addPart(address.zipCode.toFormData("zip_code"))
            .addPart(address.city.toFormData("city"))
            .addPart(avatarFile.toFormData("avatar"))
            .build()
    }
}