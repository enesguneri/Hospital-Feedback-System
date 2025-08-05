package com.enes.hospitalfeedbacksystem.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enes.hospitalfeedbacksystem.model.DoctorDTO
import com.enes.hospitalfeedbacksystem.service.APIClient
import kotlinx.coroutines.launch

class DoctorViewModel : ViewModel() {
    val doctorList = MutableLiveData<List<DoctorDTO>>()
    val errorMessage = MutableLiveData<String>()

    fun getDoctorList(context: Context) {
        try {
            viewModelScope.launch {
                val doctors = APIClient.getAuthApiService(context).getDoctors()
                doctorList.postValue(doctors)
            }
        } catch (e : Exception) {
            errorMessage.postValue("Hata oluştu: ${e.localizedMessage}")
        }
    }
}