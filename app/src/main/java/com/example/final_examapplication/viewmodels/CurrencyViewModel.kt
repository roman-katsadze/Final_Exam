package com.example.final_examapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_examapplication.data.local.entities.Transaction
import com.example.final_examapplication.data.repository.FinanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class RatesState {
    object Idle : RatesState()
    object Loading : RatesState()
    data class Loaded(val base: String, val rates: Map<String, Double>) : RatesState()
    data class Error(val message: String) : RatesState()
}

class CurrencyViewModel(
    private val repo: FinanceRepository
) : ViewModel() {

    private val _ratesState = MutableStateFlow<RatesState>(RatesState.Idle)
    val ratesState: StateFlow<RatesState> = _ratesState

    private val _convertResult = MutableStateFlow("")
    val convertResult: StateFlow<String> = _convertResult


    fun loadRatesUsd() {
        viewModelScope.launch {
            _ratesState.value = RatesState.Loading

            val res = repo.getExchangeRates("USD")
            res.fold(
                onSuccess = { map ->
                    _ratesState.value = RatesState.Loaded("USD", map)
                },
                onFailure = { e ->
                    _ratesState.value = RatesState.Error(e.message ?: "Failed to load rates")
                }
            )
        }
    }


    fun convertAndSave(amount: Double, from: String, to: String) {
        viewModelScope.launch {


            if (amount <= 0) {
                _convertResult.value = "Invalid amount"
                return@launch
            }
            if (from.isBlank() || to.isBlank()) {
                _convertResult.value = "Currency not selected"
                return@launch
            }


            val current = _ratesState.value
            val needLoad = current !is RatesState.Loaded || current.base != "USD"

            if (needLoad) {
                _convertResult.value = "Loading rates..."
                _ratesState.value = RatesState.Loading

                val res = repo.getExchangeRates("USD")
                res.fold(
                    onSuccess = { map ->
                        _ratesState.value = RatesState.Loaded("USD", map)
                    },
                    onFailure = { e ->
                        _ratesState.value =
                            RatesState.Error(e.message ?: "Failed to load rates")
                        _convertResult.value = "Rates not loaded: ${e.message}"
                        return@launch
                    }
                )
            }


            val loaded = _ratesState.value as? RatesState.Loaded
            val rates = loaded?.rates
            if (rates == null || rates.isEmpty()) {
                _convertResult.value = "Rates not loaded"
                return@launch
            }


            val rateFrom = if (from == "USD") 1.0 else rates[from]
            val rateTo = if (to == "USD") 1.0 else rates[to]

            if (rateFrom == null || rateTo == null) {
                _convertResult.value = "Rate not found: from=$from to=$to"
                return@launch
            }


            val crossRate = rateTo / rateFrom
            val result = amount * crossRate

            _convertResult.value = "%.2f %s = %.2f %s".format(amount, from, result, to)


            repo.insertTransaction(
                Transaction(
                    amount = amount,
                    fromCurrency = from,
                    toCurrency = to,
                    rate = crossRate,
                    result = result
                )
            )
        }
    }
}




