package com.example.firewatch.shared.helpers

import android.content.Context
import com.example.firewatch.context.auth.AuthException
import com.example.firewatch.context.auth.AuthService
import com.example.firewatch.domain.entities.IdentityUser
import com.example.firewatch.domain.valueObjects.UserType
import com.example.firewatch.presentation.views.AutarchyHome
import com.example.firewatch.presentation.views.HomeActivity
import com.example.firewatch.presentation.views.ICFNHome

class Router(
    private val authService: AuthService
) {
    fun routeHome(context: Context): Boolean {
        val identityResult = authService.getIdentity<IdentityUser>()

        if (identityResult.isFailure) {
            return false
        }

        val identity = identityResult.getOrThrow()

        when (identity.userType) {
            UserType.USER -> HomeActivity.new(context)
            UserType.ADMIN -> ICFNHome.new(context)
            else -> throw AuthException("There isn't any user with that type")
        }

        return true
    }
}