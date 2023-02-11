package com.ghartakshiksha.network.repository

import com.ghartakshiksha.network.api.MyApi
import com.ghartakshiksha.network.api.SafeApiRequest
import com.ghartakshiksha.network.response.LoginSignupResponse
import javax.inject.Inject

class SignupRepository @Inject constructor(
    private val api: MyApi
) : SafeApiRequest() {

    suspend fun tutorRegistration(
        name: String,
        email: String,
        mobile: String,
        password: String
    ): LoginSignupResponse {
        return apiRequest {
            api.tutorRegistration(
                name,
                email,
                mobile,
                password
            )
        }
    }
}