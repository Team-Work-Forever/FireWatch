package com.example.firewatch.shared

import okhttp3.MultipartBody

interface MultipartRequest {
    fun toMultipart() : MultipartBody
}