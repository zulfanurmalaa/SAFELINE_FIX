package com.capstone.fall_guard.ui.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.fall_guard.data.repo.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileViewModel(private val repository: MainRepository) : ViewModel() {
    val usernameProfile = runBlocking { repository.getUsername().first() }
    val telpProfile = runBlocking { repository.getTelpNumber().first() }

    fun getImageProfile() = repository.getImageProfile()

    fun saveImageProfile(image: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveImage(image)
        }
    }

    fun saveUsername(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveUsername(username)
        }
    }

    fun saveTelp(telp: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveTelp(telp)
        }
    }
}