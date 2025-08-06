package com.enes.hospitalfeedbacksystem.model

sealed class FeedbackItem {
    data class DoctorFeedback(val data: DoctorFeedbackDTO) : FeedbackItem()
    data class HospitalFeedback(val data: HospitalFeedbackDTO) : FeedbackItem()
}