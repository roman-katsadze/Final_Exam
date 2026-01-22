package com.example.final_examapplication.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
    val fromCurrency: String,
    val toCurrency: String,
    val rate: Double,
    val result: Double,
    val timestamp: Long = System.currentTimeMillis()
)


