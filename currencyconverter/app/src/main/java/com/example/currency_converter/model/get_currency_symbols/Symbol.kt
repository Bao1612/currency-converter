package com.example.currency_converter.model.get_currency_symbols

import com.google.gson.annotations.SerializedName

data class Symbol(
    @SerializedName("conversion_rates")
    val conversionRates: Map<String, String>,
)
