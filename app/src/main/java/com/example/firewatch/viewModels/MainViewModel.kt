package com.example.firewatch.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.context.AuthService
import com.example.firewatch.context.dtos.ResetPasswordInput
import com.example.firewatch.context.dtos.SignUpInput
import kotlinx.coroutines.launch
import java.io.File

class MainViewModel(
    private val authService: AuthService,
    private val appContext: Context
):ViewModel() {
    fun login() {
        viewModelScope.launch {
            val file = File(appContext.cacheDir, "Firedeadshot.png")
            file.createNewFile()
            file.outputStream().use {
                appContext.assets.open("FireDeadshot.png").copyTo(it)
            }

            val signUpResult = authService.signUp(SignUpInput(
                nif = "123456789",
                email = "diogoassuncao@ipvc.pt",
                password = "Password12",
                firstName = "Diogo",
                lastName = "Assunção",
                userName = "CavasCallahan",
                zipCode = "4490-683",
                city = "Póvoa de Varzim",
                street = "Rua de Viriato Barbosa",
                phoneCode = "+351",
                phoneNumber = "914653128",
                streetNumber = "780",
                avatarFile = file
            ))

            if (signUpResult.isFailure) {
                println("Carambolas colegas")
            }

            val result = authService.login("diogoassuncao@ipvc.pt", "Password12")

            if (result.isFailure) {
                println("Viva ardeu")
            }

            val accessToken = result.getOrThrow()
            var resultRefresh = authService.checkAuth(accessToken)

            if (resultRefresh.isFailure) {
                println("No refresh no drive!")
            }

            var forgotResult = authService.forgotPassword("diogoassuncao@ipvc.pt")

            if (forgotResult.isFailure) {
                println("Nem pensar agora chora")
            } else
            {
                println(forgotResult.getOrThrow())
            }

            var resetResult = authService.resetPassword(
                ResetPasswordInput(
                    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJmaXJld2F0Y2guY29tIiwic3ViIjoiMGQ5YjU4ZGYtMDZlOC00YWNjLWE4MWYtYTQxZDU2OWM4NmE3IiwiYXVkIjpbIkZpcmUgV2F0Y2giXSwiZXhwIjoxNzE2ODk5MDc2LCJpYXQiOjE3MTY4OTgxNzYsImp0aSI6ImE2OTM5YzAyLWQ3ZTEtNDY3OC1hZGY3LTQzZWZlNTE4YWIwMiIsImVtYWlsIjoiZGlvZ29hc3N1bmNhb0BpcHZjLnB0Iiwicm9sZSI6InVzZXIifQ.04f7AkGhPnPl9mKod_6P1KsCzULNKrnfFKNaf-jV-gg",
                    "Password14",
                    "Password14"
                )
            )

            if (resetResult.isFailure) {
                println("U sure to destroy the sith not join them!")
            } else {
                println(resetResult.getOrThrow())
            }
        }
    }
}