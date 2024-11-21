package com.example.currency_converter.controller

import com.example.currency_converter.model.get_latest_rates.LatestRateFetcher

class LatestRateController(private val getLatestRateFetcher: LatestRateFetcher) {

    fun getLatestRateController(
        onResult: (Map<String, String>, String) -> Unit,
        onError: (String) -> Unit
    ) {
        getLatestRateFetcher.fetchLatestRate(
            onSuccess = {latestRate, timeLastUp -> onResult(latestRate, timeLastUp)},
            onError = {error -> onError(error)}
        )
    }

}