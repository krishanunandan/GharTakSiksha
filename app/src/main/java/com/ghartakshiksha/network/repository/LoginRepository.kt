package com.ghartakshiksha.network.repository

import com.ghartakshiksha.network.api.MyApi
import com.ghartakshiksha.network.api.SafeApiRequest
import com.ghartakshiksha.network.response.LoginSignupResponse
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val api: MyApi
) : SafeApiRequest() {

    suspend fun tutorLogin(
        mobile: String,
        password: String
    ): LoginSignupResponse {
        return apiRequest {
            api.tutorLogin(
                mobile,
                password
            )
        }
    }
}