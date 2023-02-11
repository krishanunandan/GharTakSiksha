package com.ghartakshiksha.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ghartakshiksha.network.repository.SignupRepository
import com.ghartakshiksha.network.response.LoginSignupResponse
import com.ghartakshiksha.utility.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: SignupRepository,
) : ViewModel() {

    var authListener: AuthListener? = null

    //7651925690
    var loginSignupResponse = MutableLiveData<LoginSignupResponse>()


    fun tutorRegistration(
        name: String,
        email: String,
        mobile: String,
        password: String
    ) {
        authListener?.onStarted()
        Coroutines.main {
            try {
                val response = repository.tutorRegistration(
                    name,
                    email,
                    mobile,
                    password
                )
                Log.e("Registration", "" + response.toString())
                if (response.success!!) {
                    loginSignupResponse.value = response
                    authListener?.onSuccess(response.message!!, MethodName.tutorRegistration)

                } else {
                    authListener?.onFailure(response.message!!, MethodName.tutorRegistration)

                }
            } catch (e: ApiException) {
                authListener?.onFailure("Server Not Responding", MethodName.tutorRegistration)
                e.printStackTrace()
            } catch (e: NoInternetException) {
                authListener?.onInternetInfo(
                    Constant.noInternetConnection,
                    MethodName.tutorRegistration
                )
                e.printStackTrace()
            }
        }
    }
}