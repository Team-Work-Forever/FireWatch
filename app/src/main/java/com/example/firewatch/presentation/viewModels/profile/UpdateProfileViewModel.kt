package com.example.firewatch.presentation.viewModels.profile

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firewatch.context.auth.AuthService
import com.example.firewatch.domain.entities.User
import com.example.firewatch.domain.repositories.dtos.profile.ProfileUpdateInput
import com.example.firewatch.domain.repositories.interfaces.ProfileRepository
import com.example.firewatch.domain.valueObjects.Address
import com.example.firewatch.domain.valueObjects.Phone
import com.example.firewatch.shared.extensions.getProblem
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authService: AuthService,
    private val profileRepository: ProfileRepository
) : ViewModel() {
    val authUser: User? = authService.getIdentity<User>().getOrNull()

    val nif = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val userName = MutableLiveData<String>()
    val phoneCode = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()
    val street = MutableLiveData<String>()
    val streetNumber = MutableLiveData<String>()
    val zipCode = MutableLiveData<String>()
    val city = MutableLiveData<String>()
    val avatarFile = MutableLiveData<File>()

    fun updateProfile(): Deferred<Boolean> {
        return viewModelScope.async {
            var phone: Phone? = null
            var address: Address? = null

            if (phoneCode.value != null && phoneNumber.value != null) {
                phone = Phone.create(phoneCode.value!!, phoneNumber.value!!)
            }

            if (street.value != null && streetNumber.value != null && zipCode.value != null && city.value != null) {
                address = Address.create(
                    street.value!!,
                    streetNumber.value!!.toInt(),
                    zipCode.value!!,
                    city.value!!
                )
            }

            val response = profileRepository.update(ProfileUpdateInput(
                email.value,
                avatarFile.value,
                userName.value,
                null,
                null,
                phone,
                address
            ))

            withContext(Dispatchers.Main) {
                if (response.isFailure) {
                    Toast.makeText(context, response.getProblem(), Toast.LENGTH_LONG).show()
                    return@withContext false
                }

                return@withContext authService.fetchProfile().isSuccess
            }
        }
    }
}