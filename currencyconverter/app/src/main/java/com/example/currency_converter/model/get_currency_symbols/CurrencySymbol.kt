package com.example.currency_converter.model.get_currency_symbols

import com.example.currency_converter.service.APiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CurrencySymbolsFetcher(private val api: APiService) {

    fun fetchCurrencySymbols(
        onSuccess: (List<String>) -> Unit,
        onError: (String) -> Unit
    ) {
        api.getSymbol().enqueue(object : Callback<Symbol> {
            override fun onResponse(p0: Call<Symbol>, response: Response<Symbol>) {
                if(response.isSuccessful) {
                    val symbols = response.body()?.conversionRates;
                    if(symbols != null) {
                        val currencySymbols = symbols.keys.toList();
                        onSuccess(currencySymbols);
                    } else {
                        onError("Symbols are null");
                    }
                } else {
                    onError("Symbols ${response.code()}")
                }
            }

            override fun onFailure(p0: Call<Symbol>, t: Throwable) {
                onError("Failed to fetch data: ${t.message}")
            }

        })
    }
}

