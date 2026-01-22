package com.example.final_examapplication.data.repository

import android.content.Context
import com.example.final_examapplication.BuildConfig
import com.example.final_examapplication.data.local.AppDatabase
import com.example.final_examapplication.data.local.dao.TransactionDao
import com.example.final_examapplication.data.local.entities.Transaction
import com.example.final_examapplication.data.remote.RetrofitClient
import kotlinx.coroutines.flow.Flow

class FinanceRepository(context: Context) {

    private val dao: TransactionDao =
        AppDatabase.getDatabase(context).transactionDao()

    fun getAllTransactions(): Flow<List<Transaction>> =
        dao.getAllTransactions()

    suspend fun insertTransaction(transaction: Transaction) {
        dao.insertTransaction(transaction)
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        dao.deleteTransaction(transaction)
    }

    suspend fun deleteAllTransactions() {
        dao.deleteAllTransactions()
    }

    suspend fun getExchangeRates(base: String): Result<Map<String, Double>> {
        return try {
            val apiKey = com.example.final_examapplication.BuildConfig.EXCHANGE_RATE_API_KEY.trim()

            if (apiKey.isEmpty()) {
                return Result.failure(IllegalStateException("API key is empty (BuildConfig.EXCHANGE_RATE_API_KEY)"))
            }

            val response = RetrofitClient.api.getRates(
                apiKey = apiKey,
                base = base
            )

            if (response.result == "success") {
                Result.success(response.conversionRates)
            } else {
                Result.failure(RuntimeException("API returned result='${response.result}'"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}







