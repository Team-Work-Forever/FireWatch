package com.example.firewatch.services.persistence

import com.example.firewatch.domain.daos.AutarchyDao

interface DatabaseContext {
    fun autarcharyDao(): AutarchyDao
}