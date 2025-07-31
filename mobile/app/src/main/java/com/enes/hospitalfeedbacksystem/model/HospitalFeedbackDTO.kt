package com.enes.hospitalfeedbacksystem.model

import java.time.LocalDateTime

data class HospitalFeedbackDTO(val id : Int, val subject : String, val message : String,
                               val createdAt : LocalDateTime, val answer : String?)
