package com.example.firewatch.services.persistence

import com.example.firewatch.domain.daos.AutarchyDao
import com.example.firewatch.domain.daos.BurnDao
import com.example.firewatch.domain.daos.UserDao

interface DatabaseContext {
    fun autarcharyDao(): AutarchyDao
    fun burnDao(): BurnDao
}