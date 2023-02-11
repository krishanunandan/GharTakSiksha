package com.ghartakshiksha.network.api

import com.ghartakshiksha.network.response.*
import com.ghartakshiksha.utility.MethodName
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


interface MyApi {

    @POST(MethodName.tutorLogin)
    suspend fun tutorLogin(
        @Query("mobile") mobile: String,
        @Query("password") password: String
    ): Response<LoginSignupResponse>

    @POST(MethodName.tutorRegistration)
    suspend fun tutorRegistration(
        @Query("name") name: String,
        @Query("email") email: String,
        @Query("mobile") mobile: String,
        @Query("password") password: String
    ): Response<LoginSignupResponse>

    @POST(MethodName.saveTutorProfile)
    suspend fun saveTutorProfile(
        @Query("tutorid") tutorid: String,
        @Query("dob") dob: String,
        @Query("gender") gender: String,
        @Query("alternateNumber") alternateNumber: String,
        @Query("permanentHouseNo") permanentHouseNo: String,
        @Query("permanentStreet") permanentStreet: String,
        @Query("permanentArea") permanentArea: String,
        @Query("permanentPincode") permanentPincode: String,
        @Query("currentHouseNo") currentHouseNo: String,
        @Query("currentStreet") currentStreet: String,
        @Query("currentArea") currentArea: String,
        @Query("currentPincode") currentPincode: String,
        @Query("qualification") qualification: String,
        @Query("teachingExperience") teachingExperience: String,
        @Query("fees") fees: String,
        @Query("vehicleOwned") vehicleOwned: String,
        @Query("teachingMode") teachingMode: String,
    ): Response<BaseResponse>


    @GET(MethodName.getClassAndSubject)
    suspend fun getClassAndSubject(): Response<ClassAndSubjectResponse>


    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): MyApi {

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .callTimeout(3, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build()
            val gson = GsonBuilder()
                .setLenient()
                .create()
            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("https://www.homebakers.app/API/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(MyApi::class.java)
        }
    }

}


