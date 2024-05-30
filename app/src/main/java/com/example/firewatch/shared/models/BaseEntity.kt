package com.example.firewatch.shared.models

import androidx.room.PrimaryKey

open class BaseEntity(
    @PrimaryKey() val id: String
)