package com.example.final_examapplication.data.model

object CurrencyData {
    val currencies = listOf(
        Currency("USD", "US Dollar", "🇺🇸"),
        Currency("EUR", "Euro", "🇪🇺"),
        Currency("GBP", "British Pound", "🇬🇧"),
        Currency("JPY", "Japanese Yen", "🇯🇵"),
        Currency("CAD", "Canadian Dollar", "🇨🇦"),
        Currency("AUD", "Australian Dollar", "🇦🇺"),
        Currency("CHF", "Swiss Franc", "🇨🇭"),
        Currency("CNY", "Chinese Yuan", "🇨🇳"),
        Currency("GEL", "Georgian Lari", "🇬🇪"),
        Currency("TRY", "Turkish Lira", "🇹🇷"),
        Currency("RUB", "Russian Ruble", "🇷🇺"),
        Currency("AED", "UAE Dirham", "🇦🇪"),
        Currency("INR", "Indian Rupee", "🇮🇳"),
        Currency("KRW", "South Korean Won", "🇰🇷"),
        Currency("MXN", "Mexican Peso", "🇲🇽"),
        Currency("BRL", "Brazilian Real", "🇧🇷"),
        Currency("ZAR", "South African Rand", "🇿🇦")
    )

    fun getCurrencyByCode(code: String): Currency? {
        return currencies.find { it.code == code }
    }
}