package com.enes.hospitalfeedbacksystem.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enes.hospitalfeedbacksystem.model.DoctorFeedbackDTO
import com.enes.hospitalfeedbacksystem.service.APIClient
import kotlinx.coroutines.launch

class DoctorFeedbackViewModel : ViewModel() {
    val allFeedbacks = MutableLiveData<List<DoctorFeedbackDTO>>()
    val myFeedbacks = MutableLiveData<List<DoctorFeedbackDTO>>()
    val errorMessage = MutableLiveData<String>()
    val submitResult = MutableLiveData<DoctorFeedbackDTO>()

    fun getAllFeedbacks(context: Context) {
        viewModelScope.launch {
            try {
                val feedbacks = APIClient.getAuthApiService(context).getAllDoctorFeedbacks()
                allFeedbacks.postValue(feedbacks)
            } catch (e : Exception){
                errorMessage.postValue("Hata oluştu: ${e.localizedMessage}")
            }
        }
    }

    fun getMyFeedbacks(context: Context) {
        viewModelScope.launch {
            try {
                val feedbacks = APIClient.getAuthApiService(context).getMyDoctorFeedbacks()
                myFeedbacks.postValue(feedbacks)
            } catch (e : Exception){
                errorMessage.postValue("Hata oluştu: ${e.localizedMessage}")
            }
        }
    }

    fun submitFeedback(context: Context, dto: DoctorFeedbackDTO) {
        viewModelScope.launch {
            try {
                val feedback = APIClient.getAuthApiService(context).createDoctorFeedback(dto)
                submitResult.postValue(feedback)
            } catch (e : Exception){
                errorMessage.postValue("Hata oluştu: ${e.localizedMessage}")
            }
        }
    }

    fun deleteFeedback(context: Context, feedbackId: Int) {
        viewModelScope.launch {
            try {
                APIClient.getAuthApiService(context).deleteDoctorFeedback(feedbackId)
                getMyFeedbacks(context)
            } catch (e : Exception){
                errorMessage.postValue("Hata oluştu: ${e.localizedMessage}")
            }
        }
    }

    fun getFeedbackByDoctorId(context: Context, doctorId: Int) {
        viewModelScope.launch {
            try {
                val feedbacks = APIClient.getAuthApiService(context).getDoctorsFeedbacks(doctorId)
                myFeedbacks.postValue(feedbacks)
            } catch (e : Exception){
                errorMessage.postValue("Hata oluştu: ${e.localizedMessage}")
            }
        }
    }

    fun updateFeedback(context: Context, id : Int, feedback: DoctorFeedbackDTO) {
        viewModelScope.launch {
            try {
                APIClient.getAuthApiService(context).updateDoctorFeedback(id, feedback)
                getMyFeedbacks(context)
            } catch (e : Exception){
                errorMessage.postValue("Hata oluştu: ${e.localizedMessage}")
            }
        }
    }

    fun getFeedbackById(context: Context, id: Int) {
        viewModelScope.launch {
            try {
                val feedback = APIClient.getAuthApiService(context).getDoctorFeedbackById(id)
                submitResult.postValue(feedback)
            } catch (e : Exception){
                errorMessage.postValue("Hata oluştu: ${e.localizedMessage}")
            }
        }
    }

    fun deleteFeedbackById(context: Context, id: Int) {
        viewModelScope.launch {
            try {
                APIClient.getAuthApiService(context).deleteDoctorFeedback(id)
                getMyFeedbacks(context)
            } catch (e : Exception){
                errorMessage.postValue("Hata oluştu: ${e.localizedMessage}")
            }
        }
    }

}