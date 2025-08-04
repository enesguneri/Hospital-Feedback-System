package com.enes.hospitalfeedbacksystem.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enes.hospitalfeedbacksystem.model.RegisterUserDTO
import com.enes.hospitalfeedbacksystem.model.UserDTO
import com.enes.hospitalfeedbacksystem.service.APIClient
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    val registerResult = MutableLiveData<UserDTO>()
    val registerError = MutableLiveData<String>()

    fun registerUser(user : RegisterUserDTO) {
        viewModelScope.launch {
            try {
                val response = APIClient.noAuthApiService.createPatient(user)
                registerResult.postValue(response)
            } catch (e: Exception) {
                registerError.postValue(e.localizedMessage ?: "Bir hata oluştu")
            }
        }
    }
}