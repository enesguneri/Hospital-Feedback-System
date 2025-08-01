package com.enes.hospitalfeedbacksystem.service

import com.enes.hospitalfeedbacksystem.model.DoctorDTO
import com.enes.hospitalfeedbacksystem.model.DoctorFeedbackDTO
import com.enes.hospitalfeedbacksystem.model.HospitalFeedbackDTO
import com.enes.hospitalfeedbacksystem.model.LoginRequest
import com.enes.hospitalfeedbacksystem.model.LoginResponse
import com.enes.hospitalfeedbacksystem.model.RegisterUserDTO
import com.enes.hospitalfeedbacksystem.model.UserDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface APIService {

    //Login Endpoint
    @POST("api/Login")
    suspend fun login(@Body request: LoginRequest): LoginResponse


    //User and Admin Endpoints
    @GET("api/User")
    suspend fun getUsers(): List<UserDTO>

    @GET("api/User/me")
    suspend fun getCurrentUser(): UserDTO

    @GET("api/User/{id}")
    suspend fun getUserById(id: Int): UserDTO

    @GET("api/User/role/{role}")
    suspend fun getUsersByRole(role: String): List<UserDTO>

    @POST("api/User/create-patient")
    suspend fun createPatient(@Body user: RegisterUserDTO): UserDTO

    @POST("api/User/create-admin")
    suspend fun createAdmin(@Body user: RegisterUserDTO): UserDTO

    @PUT("api/User/{id}")
    suspend fun updateUser(@Body user: UserDTO): UserDTO

    @DELETE("api/User/{id}")
    suspend fun deleteUser(id: Int)


    //Doctor Endpoints
    @POST("api/Doctor")
    suspend fun createDoctor(@Body user: DoctorDTO): DoctorDTO

    @GET("api/Doctor")
    suspend fun getDoctors(): List<DoctorDTO>

    @GET("api/Doctor/{id}")
    suspend fun getDoctorById(id: Int): DoctorDTO

    @GET("api/Doctor/search")
    suspend fun searchDoctorsByName(name: String): List<DoctorDTO>

    @PUT("api/Doctor/{id}")
    suspend fun updateDoctor(@Body doctor: DoctorDTO)

    @DELETE("api/Doctor/{id}")
    suspend fun deleteDoctor(id: Int)


    //Doctor Feedback Endpoints
    @POST("api/DoctorFeedback")
    suspend fun createDoctorFeedback(@Body feedback: DoctorFeedbackDTO): DoctorFeedbackDTO

    @PUT("api/DoctorFeedback/{id}")
    suspend fun updateDoctorFeedback(@Body feedback: DoctorFeedbackDTO)

    @GET("api/DoctorFeedback/my-feedback")
    suspend fun getMyDoctorFeedbacks(): List<DoctorFeedbackDTO>

    @GET("api/DoctorFeedback")
    suspend fun getAllDoctorFeedbacks(): List<DoctorFeedbackDTO>

    @GET("api/DoctorFeedback/doctor/{doctorId}")
    suspend fun getDoctorsFeedbacks(doctorId: Int): List<DoctorFeedbackDTO>

    @GET("api/DoctorFeedback/{id}")
    suspend fun getDoctorFeedbackById(id: Int): DoctorFeedbackDTO

    @DELETE("api/DoctorFeedback/{id}")
    suspend fun deleteDoctorFeedback(id: Int)


    //Hospital Feedback Endpoints
    @POST("api/HospitalFeedback")
    suspend fun createHospitalFeedback(@Body feedback: HospitalFeedbackDTO): HospitalFeedbackDTO

    @GET("api/HospitalFeedback")
    suspend fun getAllHospitalFeedbacks(): List<HospitalFeedbackDTO>

    @GET("api/HospitalFeedback/my-feedback")
    suspend fun getMyHospitalFeedbacks(): List<HospitalFeedbackDTO>

    @GET("api/HospitalFeedback/{id}")
    suspend fun getHospitalFeedbackById(id: Int): HospitalFeedbackDTO

    @PUT("api/HospitalFeedback/{id}")
    suspend fun updateHospitalFeedback(@Body feedback: HospitalFeedbackDTO)

    @PUT("api/HospitalFeedback/{id}/answer")
    suspend fun answerHospitalFeedback(@Body feedback: HospitalFeedbackDTO)

    @DELETE("api/HospitalFeedback/{id}")
    suspend fun deleteHospitalFeedback(id: Int)
}