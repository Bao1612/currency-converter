package com.example.currency_converter.controller

import com.example.currency_converter.model.get_paired_currency.PairCurrencyFetcher

class PairedCurrencyController(private val getPairCurrency: PairCurrencyFetcher) {
    fun getPairedCurrency(
        onResult: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        getPairCurrency.fetchPairedCurrency(
            onSuccess = {pairCurrency -> onResult(pairCurrency)},
            onError = { error -> onError(error)}
        )
    }
}