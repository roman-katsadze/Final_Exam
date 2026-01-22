package com.example.final_examapplication.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.final_examapplication.data.repository.FinanceRepository

class CurrencyViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyViewModel::class.java)) {
            val repo = FinanceRepository(context.applicationContext)
            return CurrencyViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}







