package com.example.final_examapplication.data.remote.api

import com.example.final_examapplication.data.remote.models.ExchangeRateResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeRateApi {

    // https://v6.exchangerate-api.com/v6/{apiKey}/latest/{base}
    @GET("v6/{apiKey}/latest/{base}")
    suspend fun getRates(
        @Path("apiKey") apiKey: String,
        @Path("base") base: String
    ): ExchangeRateResponse
}



