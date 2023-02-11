package com.ghartakshiksha.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ghartakshiksha.network.repository.LoginRepository
import com.ghartakshiksha.network.response.LoginSignupResponse
import com.ghartakshiksha.utility.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
) : ViewModel() {

    var authListener: AuthListener? = null

//7651925690
    var loginSignupResponse = MutableLiveData<LoginSignupResponse>()


    fun tutorLogin(
        mobile: String,
        password: String
    ) {
        authListener?.onStarted()
        Coroutines.main {
            try {
                val response = repository.tutorLogin(
                    mobile,
                    password
                )
                Log.e("Login", "" + response.toString())
                if (response.success!!) {
                    loginSignupResponse.value = response
                    authListener?.onSuccess(response.message!!, MethodName.tutorLogin)

                } else {
                    authListener?.onFailure(response.message!!, MethodName.tutorLogin)

                }
            } catch (e: ApiException) {
                authListener?.onFailure("Server Not Responding", MethodName.tutorLogin)
                e.printStackTrace()
            } catch (e: NoInternetException) {
                authListener?.onInternetInfo(
                    Constant.noInternetConnection,
                    MethodName.tutorLogin
                )
                e.printStackTrace()
            }
        }
    }
}