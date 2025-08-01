package com.enes.hospitalfeedbacksystem.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enes.hospitalfeedbacksystem.model.LoginRequest
import com.enes.hospitalfeedbacksystem.model.LoginResponse
import com.enes.hospitalfeedbacksystem.service.APIClient
import kotlinx.coroutines.launch



class LoginViewModel : ViewModel() {
    val loginResult = MutableLiveData<LoginResponse>()
    val error = MutableLiveData<String>()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(email, password)
                val response = APIClient.noAuthApiService.login(request)
                loginResult.postValue(response)

            } catch (e: Exception) {
                error.postValue(e.localizedMessage ?: "Bir hata oluştu")
            }
        }
    }


}