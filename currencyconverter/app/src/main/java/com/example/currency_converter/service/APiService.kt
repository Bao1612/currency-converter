package com.example.currency_converter.service


import com.example.currency_converter.model.get_paired_currency.PairCurrency
import com.example.currency_converter.model.get_currency_symbols.Symbol
import com.example.currency_converter.model.get_latest_rates.LatestRate
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APiService {
    @GET("latest/USD")
    fun getSymbol(): Call<Symbol>

    @GET("pair/{base}/{target}/{amount}")
    fun getPairCurrency(
        @Path("base") base: String,
        @Path("target") target: String,
        @Path("amount") amount: String
    ): Call<PairCurrency>

    @GET("latest/{symbol}")
    fun getLatestRate(@Path("symbol") symbol: String): Call<LatestRate>

}