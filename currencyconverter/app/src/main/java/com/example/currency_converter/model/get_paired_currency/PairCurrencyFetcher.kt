package com.example.currency_converter.model.get_paired_currency

import com.example.currency_converter.service.APiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PairCurrencyFetcher(private val api: APiService,
                          private val base: String,
                          private val target: String,
                          private val amount: String) {
    fun fetchPairedCurrency(
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        api.getPairCurrency(base = base, target = target, amount = amount).enqueue(object : Callback<PairCurrency> {
            override fun onResponse(p0: Call<PairCurrency>, response: Response<PairCurrency>) {
                if(response.isSuccessful) {
                    val conversionResult = response.body()?.conversionResult;
                    if(conversionResult != null) {
                        onSuccess(conversionResult.toString())
                    } else {
                        onError("Conversion result is null")
                    }
                } else {
                    onError("Error: ${response.code()}")
                }
            }

            override fun onFailure(p0: Call<PairCurrency>, t: Throwable) {
                onError("Failed to fetch data: ${t.message}")
            }

        })
    }
}