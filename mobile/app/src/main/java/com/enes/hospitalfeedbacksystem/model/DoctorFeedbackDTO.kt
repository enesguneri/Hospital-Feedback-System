package com.enes.hospitalfeedbacksystem.model

import java.time.LocalDateTime

data class DoctorFeedbackDTO(val doctorId : Int, val score: Int, val comment: String?, val dateTime : LocalDateTime)
