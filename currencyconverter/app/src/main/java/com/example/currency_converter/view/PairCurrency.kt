package com.example.currency_converter.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.currency_converter.databinding.FragmentPairCurrencyBinding
import com.example.currency_converter.api.RetrofitInstance
import com.example.currency_converter.controller.CurrencySymbolsController
import com.example.currency_converter.controller.PairedCurrencyController
import com.example.currency_converter.model.get_currency_symbols.CurrencySymbolsFetcher
import com.example.currency_converter.model.get_paired_currency.PairCurrencyFetcher
import com.example.currency_converter.service.APiService

class PairCurrency : Fragment() {

    private lateinit var binding: FragmentPairCurrencyBinding
    private val api = RetrofitInstance.retrofit.create(APiService::class.java)
    private lateinit var currencySymbolsController: CurrencySymbolsController
    private lateinit var pairedCurrencyController: PairedCurrencyController

    private var baseSymbol: String = "USD"
    private var targetSymbol: String = "USD"
    private var amount: String = "1"

    private var basePosition: Int = 0
    private var targetPosition: Int = 0
    private var pairCurrencyFetcher: PairCurrencyFetcher? = null;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPairCurrencyBinding.inflate(inflater, container, false)

        fetchCurrencySymbols()
        getBaseAndTargetCurrencySymbol()
        onInputAmount()
        switchSelectedSymbol()

        return binding.root
    }

    private fun fetchCurrencySymbols() {
        val symbolsFetcher = CurrencySymbolsFetcher(api)
        currencySymbolsController = CurrencySymbolsController(symbolsFetcher)

        currencySymbolsController.getCurrencySymbols(
            onResult = { symbols ->
                context?.let { ctx ->
                    val adapter = ArrayAdapter(
                        ctx,
                        android.R.layout.simple_spinner_item,
                        symbols
                    ).apply {
                        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    }

                    binding.baseSymbol.adapter = adapter
                    binding.targetSymbol.adapter = adapter
                }
            },
            onError = { errorMessage ->
                Log.e("PairCurrency", "Error fetching symbols: $errorMessage")
            }
        )
    }

    private fun getBaseAndTargetCurrencySymbol() {
        binding.baseSymbol.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                baseSymbol = parent?.getItemAtPosition(position).toString()
                basePosition = position
                getPairedCurrency()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.targetSymbol.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                targetSymbol = parent?.getItemAtPosition(position).toString()
                targetPosition = position
                getPairedCurrency()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

    }

    private fun onInputAmount() {
        binding.amount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                amount = binding.amount.text.toString()
                getPairedCurrency()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    private fun getPairedCurrency() {
        pairCurrencyFetcher =
            PairCurrencyFetcher(
                api,
                baseSymbol,
                targetSymbol,
                amount)

        pairedCurrencyController = PairedCurrencyController(pairCurrencyFetcher!!)

        pairedCurrencyController.getPairedCurrency(
            onResult = { result ->
                binding.result.setText(result)
            },
            onError = { error ->
                Toast.makeText(requireContext(), "Something: $error", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun switchSelectedSymbol() {
        binding.switchSelectedSymbol.setOnClickListener {
            binding.baseSymbol.setSelection(targetPosition)
            binding.targetSymbol.setSelection(basePosition)

            baseSymbol = binding.baseSymbol.getItemAtPosition(basePosition).toString()
            targetSymbol = binding.targetSymbol.getItemAtPosition(targetPosition).toString()


            getPairedCurrency()

        }
    }

}