package com.ghartakshiksha.network.repository

import com.ghartakshiksha.network.api.MyApi
import com.ghartakshiksha.network.api.SafeApiRequest
import com.ghartakshiksha.network.response.ClassAndSubjectResponse
import javax.inject.Inject


class ProfileRepository @Inject constructor(
    private val api: MyApi
) : SafeApiRequest() {

    suspend fun getClassAndSubject(): ClassAndSubjectResponse {
        return apiRequest {
            api.getClassAndSubject()
        }
    }
}