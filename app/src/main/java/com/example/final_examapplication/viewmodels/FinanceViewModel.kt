package com.example.final_examapplication.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.final_examapplication.data.local.entities.Transaction
import com.example.final_examapplication.data.repository.FinanceRepository
import kotlinx.coroutines.launch

class FinanceViewModel(context: Context) : ViewModel() {

    private val repository = FinanceRepository(context.applicationContext)

    val transactions = repository.getAllTransactions().asLiveData()

    fun deleteTransaction(t: Transaction) {
        viewModelScope.launch {
            repository.deleteTransaction(t)
        }
    }

    fun clearAllTransactions() {
        viewModelScope.launch {
            repository.deleteAllTransactions()
        }
    }
}




