package com.example.currency_converter.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private const val API_KEY = "917155d5860c5a4e286da2bb"

    val retrofit = Retrofit.Builder()
        .baseUrl("https://v6.exchangerate-api.com/v6/${API_KEY}/")
        .client(
            OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                val newUrl = request.url.newBuilder()
                    .addQueryParameter("access_key", API_KEY)
                    .build()
                chain.proceed(request.newBuilder().url(newUrl).build())
            }
            .build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}