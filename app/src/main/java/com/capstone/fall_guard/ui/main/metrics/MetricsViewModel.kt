package com.capstone.fall_guard.ui.main.metrics

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.fall_guard.data.repo.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MetricsViewModel(private val repository: MainRepository) : ViewModel() {
    private val _fallHistoryList = MutableLiveData<MutableList<Long>>(mutableListOf())
    val fallHistoryList: LiveData<MutableList<Long>> = _fallHistoryList

    init {
        observeUserFallHistory()
    }

    private fun observeUserFallHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.observeUserFallHistory()
                .catch { error ->
                    Log.e("MetricsViewModel", "Error observing fall history", error)
                }
                .collect { status ->
                    _fallHistoryList.postValue(status.toMutableList())
                }
        }
    }
}