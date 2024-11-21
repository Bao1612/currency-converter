package com.example.currency_converter.model.get_latest_rates

import com.example.currency_converter.service.APiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class LatestRateFetcher(private val api: APiService,
                        private val symbol: String) {

    fun fetchLatestRate(
        onSuccess: (Map<String, String>, String) -> Unit,
        onError: (String) -> Unit
    ) {
        api.getLatestRate(symbol = symbol).enqueue(object : Callback<LatestRate> {
            override fun onResponse(p0: Call<LatestRate>, response: Response<LatestRate>) {
                if(response.isSuccessful) {
                    val conversionResult = response.body()?.conversionResult
                    val timeLastUp = response.body()?.timeLastUp

                    val inputFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z")

                    // Chuyển chuỗi thành đối tượng ZonedDateTime
                    val zonedDateTime = ZonedDateTime.parse(timeLastUp, inputFormatter)

                    // Định dạng chuỗi đầu ra chỉ lấy ngày
                    val outputFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy")

                    // Chuyển đối tượng ZonedDateTime thành chuỗi với định dạng mong muốn
                    val formattedDate = zonedDateTime.format(outputFormatter)

                    if(conversionResult != null && timeLastUp != null) {
                        onSuccess(conversionResult, formattedDate)
                    } else {
                        onError("Symbols are null")
                    }
                } else {
                    onError("Symbols ${response.code()}")
                }
            }

            override fun onFailure(p0: Call<LatestRate>, t: Throwable) {
                onError("Failed to fetch data: ${t.message}")
            }

        })
    }
}