package com.enes.hospitalfeedbacksystem.model

data class LoginRequest(val email: String, val password: String) {
    override fun toString(): String {
        return "LoginRequest(email='$email', password='********')"
    }
}