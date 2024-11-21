package com.example.currency_converter.controller

import com.example.currency_converter.model.get_currency_symbols.CurrencySymbolsFetcher

class CurrencySymbolsController(private val getCurrencySymbolsFetcher: CurrencySymbolsFetcher) {

    fun getCurrencySymbols(
        onResult: (List<String>) -> Unit,
        onError: (String) -> Unit
    ) {
        getCurrencySymbolsFetcher.fetchCurrencySymbols(
            onSuccess = { symbols -> onResult(symbols)},
            onError = {error -> onError(error)}
            )
    }


}