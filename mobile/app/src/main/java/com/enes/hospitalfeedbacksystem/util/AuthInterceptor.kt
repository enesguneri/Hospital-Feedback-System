package com.enes.hospitalfeedbacksystem.util

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val prefs = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val token = prefs.getString("jwt_token", null)

        val newRequest = if (token != null) {
            chain.request().newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else {
            chain.request()
        }

        return chain.proceed(newRequest)
    }
}
