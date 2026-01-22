package com.example.final_examapplication.data.model

data class Currency(
    val code: String,
    val name: String,
    val flag: String,
    var rate: Double = 0.0
)