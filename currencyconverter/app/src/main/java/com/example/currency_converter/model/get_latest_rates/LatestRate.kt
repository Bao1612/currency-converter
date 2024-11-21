package com.example.currency_converter.model.get_latest_rates

import com.google.gson.annotations.SerializedName

class LatestRate (

    @SerializedName("time_last_update_utc")
    val timeLastUp: String,

    @SerializedName("conversion_rates")
    val conversionResult: Map<String, String>
)

