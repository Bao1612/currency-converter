package com.example.currency_converter.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currency_converter.R
import com.example.currency_converter.api.RetrofitInstance
import com.example.currency_converter.controller.CurrencySymbolsController
import com.example.currency_converter.controller.LatestRateController
import com.example.currency_converter.databinding.FragmentLatestCurrencyBinding
import com.example.currency_converter.model.get_currency_symbols.CurrencySymbolsFetcher
import com.example.currency_converter.model.get_latest_rates.LatestRateAdapter
import com.example.currency_converter.model.get_latest_rates.LatestRateFetcher
import com.example.currency_converter.service.APiService

class LatestCurrencyFragment : Fragment() {

    private lateinit var binding: FragmentLatestCurrencyBinding
    private lateinit var adapter: LatestRateAdapter
    private lateinit var latestRateController: LatestRateController
    private val api = RetrofitInstance.retrofit.create(APiService::class.java)
    private var result: Map<String, String> = emptyMap()
    private lateinit var currencySymbolsController: CurrencySymbolsController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLatestCurrencyBinding.inflate(inflater, container, false)

        openCurrencyPopup()
        setupRecyclerView()

        getLatestRate("USD")

        return binding.root
    }

    private fun openCurrencyPopup() {
        binding.textInputEditText.setOnClickListener {
            chooseCurrencySymbol()
        }
    }

    private fun chooseCurrencySymbol() {
        // Lấy LayoutInflater
        val assetDetailWindow =
            requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Inflate layout của popup
        val assetDetailView = assetDetailWindow.inflate(R.layout.pop_up_symbol_list, null)

        // Tạo PopupWindow
        val popupWindow = PopupWindow(
            assetDetailView,
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT,
            true // Đặt focusable = true để popup nhận focus
        )

        val closeBtn: Button = assetDetailView.findViewById(R.id.closeBtn)
        val symbolList: ListView = assetDetailView.findViewById(R.id.symbolList)


        val symbolsFetcher = CurrencySymbolsFetcher(api)
        currencySymbolsController = CurrencySymbolsController(symbolsFetcher)

        currencySymbolsController.getCurrencySymbols(
            onResult = { symbols ->
                context?.let { ctx ->
                    val adapter = ArrayAdapter(
                        ctx,
                        android.R.layout.simple_list_item_1,
                        symbols
                    )

                    symbolList.adapter = adapter

                    symbolList.setOnItemClickListener { _, _, position, _ ->
                        val selectedItem = symbols[position]
                        val symbol: String = selectedItem
                        binding.textInputEditText.setText(symbol)
                        getLatestRate(symbol)
                        popupWindow.dismiss()
                    }

                }
            },
            onError = { errorMessage ->
                Log.e("PairCurrency", "Error fetching symbols: $errorMessage")
            }
        )

        closeBtn.setOnClickListener {
            popupWindow.dismiss()
        }
        // Hiển thị popup bên dưới anchorView
        popupWindow.showAsDropDown(binding.root, 0, 0)
    }


    private fun setupRecyclerView() {
        // Initialize RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = LatestRateAdapter(result)
        binding.recyclerView.adapter = adapter
    }

    private fun getLatestRate(symbol: String) {
        val latestRateFetcher = LatestRateFetcher(api, symbol)
        latestRateController = LatestRateController(latestRateFetcher)

        latestRateController.getLatestRateController(
            onResult = { latestRate, timeLastUp ->
                result = latestRate
                binding.latestTimeUp.text = "Time update: $timeLastUp"
                updateRecyclerView()
            },
            onError = { errorMessage ->
                Log.e("LatestRate", "Error: $errorMessage")
            }
        )
    }

    private fun updateRecyclerView() {
        // Update adapter data and notify changes
        adapter = LatestRateAdapter(result)
        binding.recyclerView.adapter = adapter
    }


}