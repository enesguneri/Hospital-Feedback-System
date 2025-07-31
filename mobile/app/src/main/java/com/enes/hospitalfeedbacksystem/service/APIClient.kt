package com.enes.hospitalfeedbacksystem.service

import android.content.Context
import com.enes.hospitalfeedbacksystem.util.AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {

    private val BASE_URL = "http://10.0.2.2:5077/"

    val noAuthApiService : APIService by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build().create(APIService::class.java)
    }

    fun getAuthApiService(context: Context): APIService {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(APIService::class.java)
    }
}