package com.ghartakshiksha.ui.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ghartakshiksha.network.repository.SignupRepository
import com.ghartakshiksha.ui.viewmodel.SignupViewModel
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
class SignupViewModelFactory @Inject constructor(
    private val repository: SignupRepository?
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignupViewModel(repository!!) as T
    }
}