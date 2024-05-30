package com.example.firewatch.shared.models

import androidx.room.PrimaryKey

open class BaseEntity protected constructor(
    @PrimaryKey() val id: String
)