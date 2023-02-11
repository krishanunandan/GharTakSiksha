package com.ghartakshiksha.utility

interface AuthListener {
    fun onStarted()
    fun onSuccess(message: String, type: String)
    fun onFailure(message: String, type: String)
    fun onInternetInfo(message: String, type: String)
}