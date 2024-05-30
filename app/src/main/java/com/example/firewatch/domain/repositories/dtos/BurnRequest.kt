package com.example.firewatch.domain.repositories.dtos

import com.example.firewatch.domain.valueObjects.BurnState

data class BurnRequest(
    val id: String,
    val state: BurnState
)