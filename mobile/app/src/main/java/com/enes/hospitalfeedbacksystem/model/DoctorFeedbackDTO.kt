package com.enes.hospitalfeedbacksystem.model

data class DoctorFeedbackDTO(val id : Int, val doctorId: Int, val score: Int, val comment: String?, val createdAt: String?)
