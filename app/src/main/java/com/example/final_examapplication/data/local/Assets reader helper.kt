package com.example.final_examapplication.data.local

import android.content.Context

object AssetsReader {
    fun readText(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }
}
