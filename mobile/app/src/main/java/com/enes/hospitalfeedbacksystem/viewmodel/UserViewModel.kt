package com.enes.hospitalfeedbacksystem.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enes.hospitalfeedbacksystem.model.UserDTO
import com.enes.hospitalfeedbacksystem.service.APIClient
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    val user = MutableLiveData<UserDTO>()
    val errorMessage = MutableLiveData<String>()

    fun getUserById(context: Context,userId: Int) {
        viewModelScope.launch {
            try {
                val response = APIClient.getAuthApiService(context).getUserById(userId)
                user.postValue(response)
            } catch (e: Exception) {
                errorMessage.postValue("Error occurred: ${e.localizedMessage}")
            }
        }
    }
}