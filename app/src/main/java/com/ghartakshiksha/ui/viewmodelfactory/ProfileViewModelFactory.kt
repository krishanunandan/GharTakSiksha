package com.ghartakshiksha.ui.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ghartakshiksha.network.repository.ProfileRepository
import com.ghartakshiksha.ui.viewmodel.ProfileViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ProfileViewModelFactory @Inject constructor(
    private val repository: ProfileRepository?
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(repository!!) as T
    }
}