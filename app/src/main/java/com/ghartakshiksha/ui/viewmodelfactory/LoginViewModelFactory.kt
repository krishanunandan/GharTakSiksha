package com.ghartakshiksha.ui.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ghartakshiksha.network.repository.LoginRepository
import com.ghartakshiksha.ui.viewmodel.LoginViewModel
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory @Inject constructor(
    private val repository: LoginRepository?
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(repository!!) as T
    }
}