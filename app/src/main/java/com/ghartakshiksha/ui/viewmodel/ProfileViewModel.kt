package com.ghartakshiksha.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ghartakshiksha.network.repository.ProfileRepository
import com.ghartakshiksha.network.response.ClassAndSubjectResponse
import com.ghartakshiksha.network.response.LoginSignupResponse
import com.ghartakshiksha.utility.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
) : ViewModel() {

    var authListener: AuthListener? = null

    var classAndSubjectResponse = MutableLiveData<ClassAndSubjectResponse>()


    fun getClassAndSubject() {
        authListener?.onStarted()
        Coroutines.main {
            try {
                val response = repository.getClassAndSubject()
                Log.e("ClassAndSubject", "" + response.toString())
                if (response.success) {
                    classAndSubjectResponse.value = response
                    authListener?.onSuccess(response.message, MethodName.getClassAndSubject)

                } else {
                    authListener?.onFailure(response.message, MethodName.getClassAndSubject)

                }
            } catch (e: ApiException) {
                authListener?.onFailure("Server Not Responding", MethodName.getClassAndSubject)
                e.printStackTrace()
            } catch (e: NoInternetException) {
                authListener?.onInternetInfo(
                    Constant.noInternetConnection,
                    MethodName.getClassAndSubject
                )
                e.printStackTrace()
            }
        }
    }
}