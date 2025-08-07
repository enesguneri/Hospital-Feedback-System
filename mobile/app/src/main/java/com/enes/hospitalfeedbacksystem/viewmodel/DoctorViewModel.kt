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
    val doctor = MutableLiveData<DoctorDTO>()
    val errorMessage = MutableLiveData<String>()

    fun getDoctorList(context: Context) {
        try {
            viewModelScope.launch {
                val doctors = APIClient.getAuthApiService(context).getDoctors()
                doctorList.postValue(doctors)
            }
        } catch (e : Exception) {
            errorMessage.postValue("${e.localizedMessage}")
        }
    }

    fun getDoctorById(context: Context, id: Int) {
        try {
            viewModelScope.launch {
                val doctorData = APIClient.getAuthApiService(context).getDoctorById(id)
                doctor.postValue(doctorData)
            }
        } catch (e : Exception) {
            errorMessage.postValue("${e.localizedMessage}")
        }
    }
}