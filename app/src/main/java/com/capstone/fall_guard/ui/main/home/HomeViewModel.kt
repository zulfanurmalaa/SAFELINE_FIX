package com.capstone.fall_guard.ui.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.fall_guard.data.repo.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MainRepository) : ViewModel() {
    private val _userActivity = MutableLiveData<String>()
    val userActivity: LiveData<String> = _userActivity

    init {
        observeUserActivity()
    }

    private fun observeUserActivity() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.observeUserActivity()
                .catch { error ->
                    Log.e("HomeViewModel", "Error observing user activity", error)
                }
                .collect { status ->
                    _userActivity.postValue(status)
                }
        }
    }
}