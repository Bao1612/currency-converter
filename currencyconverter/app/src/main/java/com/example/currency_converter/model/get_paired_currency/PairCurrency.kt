package com.example.currency_converter.model.get_paired_currency

import com.google.gson.annotations.SerializedName

data class PairCurrency (
    @SerializedName("conversion_result")
    val conversionResult: Double
)