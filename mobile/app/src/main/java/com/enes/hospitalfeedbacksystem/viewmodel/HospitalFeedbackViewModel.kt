package com.enes.hospitalfeedbacksystem.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enes.hospitalfeedbacksystem.model.AnswerDTO
import com.enes.hospitalfeedbacksystem.model.HospitalFeedbackCreateDTO
import com.enes.hospitalfeedbacksystem.model.HospitalFeedbackDTO
import com.enes.hospitalfeedbacksystem.service.APIClient
import kotlinx.coroutines.launch

class HospitalFeedbackViewModel : ViewModel() {
    val allFeedbacks = MutableLiveData<List<HospitalFeedbackDTO>>() // For admins
    val myFeedbacks = MutableLiveData<List<HospitalFeedbackDTO>>() // For users
    val errorMessage = MutableLiveData<String>()
    val submitResult = MutableLiveData<HospitalFeedbackDTO>()
    val result = MutableLiveData<HospitalFeedbackDTO>()
    val isAnswerSent = MutableLiveData<Boolean>()
    val isFeedbackDeleted = MutableLiveData<Boolean>()

    fun getAllFeedbacks(context: Context) {
        allFeedbacks.value = emptyList() // Clear previous data
        errorMessage.value = "" // Clear previous error message
        viewModelScope.launch {
            try {
                val feedbacks = APIClient.getAuthApiService(context).getAllHospitalFeedbacks()
                allFeedbacks.postValue(feedbacks)
            } catch (e : Exception){
                errorMessage.postValue("Hata oluştu: ${e.localizedMessage}")
            }
        }
    }

    fun getMyFeedbacks(context: Context) {
        myFeedbacks.value = emptyList() // Clear previous data
        errorMessage.value = "" // Clear previous error message
        viewModelScope.launch {
            try {
                val feedbacks = APIClient.getAuthApiService(context).getMyHospitalFeedbacks()
                myFeedbacks.postValue(feedbacks)
            } catch (e : Exception){
                errorMessage.postValue("Hata oluştu: ${e.localizedMessage}")
            }
        }
    }

    fun submitFeedback(context: Context, dto: HospitalFeedbackCreateDTO) {
        viewModelScope.launch {
            try {
                val feedback = APIClient.getAuthApiService(context).createHospitalFeedback(dto)
                submitResult.postValue(feedback)
            } catch (e : Exception){
                errorMessage.postValue("Hata oluştu: ${e.localizedMessage}")
            }
        }
    }

    fun deleteFeedback(context: Context, feedbackId: Int) {
        viewModelScope.launch {
            try {
                APIClient.getAuthApiService(context).deleteHospitalFeedback(feedbackId)
                getMyFeedbacks(context)
                isFeedbackDeleted.postValue(true)
            } catch (e : Exception){
                errorMessage.postValue("Hata oluştu: ${e.localizedMessage}")
            }
        }
    }

    fun updateFeedback(context: Context, feedback: HospitalFeedbackDTO) {
        viewModelScope.launch {
            try {
                APIClient.getAuthApiService(context).updateHospitalFeedback(feedback.id, feedback)
                getMyFeedbacks(context)
            } catch (e : Exception){
                errorMessage.postValue("Hata oluştu: ${e.localizedMessage}")
            }
        }
    }

    fun answerFeedback(context: Context, feedbackId: Int, answer: String) {
        viewModelScope.launch {
            try {
                val answerDto = AnswerDTO(answer)
                APIClient.getAuthApiService(context).answerHospitalFeedback(feedbackId, answerDto)
                getAllFeedbacks(context) // Refresh all feedbacks after answering
                isAnswerSent.postValue(true)
            } catch (e : Exception){
                errorMessage.postValue("Hata oluştu: ${e.localizedMessage}")
            }
        }
    }

    fun getFeedbackById(context: Context, feedbackId: Int) {
        viewModelScope.launch {
            try {
                val feedback = APIClient.getAuthApiService(context).getHospitalFeedbackById(feedbackId)
                result.postValue(feedback)
            } catch (e : Exception){
                errorMessage.postValue("${e.localizedMessage}")
            }
        }
    }
}