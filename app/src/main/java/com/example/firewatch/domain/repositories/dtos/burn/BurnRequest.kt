package com.example.firewatch.domain.repositories.dtos.burn

import com.example.firewatch.domain.valueObjects.BurnState

data class BurnRequest(
    val id: String,
    val state: BurnState
)