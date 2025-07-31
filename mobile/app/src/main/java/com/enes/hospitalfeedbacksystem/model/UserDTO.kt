package com.enes.hospitalfeedbacksystem.model

data class UserDTO(val id : Int, val fullName : String,
                   val email: String, val role: String,
                   val doctorFeedbacks : List<DoctorFeedbackDTO>,
                   val hospitalFeedbacks : List<HospitalFeedbackDTO>)
