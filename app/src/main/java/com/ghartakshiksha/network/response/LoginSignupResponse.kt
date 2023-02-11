package com.ghartakshiksha.network.response

import com.ghartakshiksha.network.model.LoginSignupDataModel

data class LoginSignupResponse(
    val success: Boolean?,
    val message: String?,
    val data: LoginSignupDataModel?
)