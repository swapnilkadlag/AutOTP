package com.sk.autotp.screens.history

import androidx.lifecycle.ViewModel
import com.sk.autotp.data.Log
import com.sk.autotp.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    val logs = repository.getLogs()

    fun deleteLogs() {
        repository.deleteLogs()
    }

    fun deleteLog(log: Log) {
        repository.deleteLog(log)
    }
}