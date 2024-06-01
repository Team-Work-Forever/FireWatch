package com.example.firewatch.shared.extensions

import com.example.firewatch.domain.valueObjects.BurnReason
import com.example.firewatch.domain.valueObjects.BurnState
import com.example.firewatch.domain.valueObjects.BurnType
import com.example.firewatch.domain.valueObjects.Coordinates
import com.example.firewatch.shared.utils.DateUtils
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.math.BigDecimal
import java.time.LocalDateTime

fun String.toFormData(field: String): MultipartBody.Part {
    return MultipartBody.Part.createFormData(field, this)
}

fun Int.toFormData(field: String): MultipartBody.Part {
    return MultipartBody.Part.createFormData(field, this.toString())
}

fun Boolean.toFormData(field: String): MultipartBody.Part {
    return MultipartBody.Part.createFormData(field, this.toString())
}

fun LocalDateTime.toFormData(field: String): MultipartBody.Part {
    return MultipartBody.Part.createFormData(field, DateUtils.toString(this))
}

fun BigDecimal.toFormData(field: String): MultipartBody.Part {
    return MultipartBody.Part.createFormData(field, this.toPlainString())
}

fun BurnReason.toFormData(): MultipartBody.Part {
    return MultipartBody.Part.createFormData("reason", this.value)
}

fun BurnType.toFormData(): MultipartBody.Part {
    return MultipartBody.Part.createFormData("type", this.value)
}

fun BurnState.toFormData(): MultipartBody.Part {
    return MultipartBody.Part.createFormData("state", this.state)
}

fun File.toFormData(field: String): MultipartBody.Part {
    return MultipartBody.Part.createFormData(
        field,
        this.name,
        RequestBody.create(MediaType.get("image/${this.extension}"), this)
    )
}