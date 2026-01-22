package com.example.final_examapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_examapplication.data.model.Currency
import com.example.final_examapplication.databinding.FragmentRatesListBinding
import com.example.final_examapplication.ui.adapters.CurrencyAdapter
import com.example.final_examapplication.viewmodels.CurrencyViewModel
import com.example.final_examapplication.viewmodels.CurrencyViewModelFactory
import com.example.final_examapplication.viewmodels.RatesState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RatesListFragment : Fragment() {

    private var _binding: FragmentRatesListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CurrencyViewModel by viewModels {
        CurrencyViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRatesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CurrencyAdapter { /* optional click */ }

        binding.recyclerViewCurrencies.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewCurrencies.adapter = adapter


        viewModel.loadRatesUsd()


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.ratesState.collectLatest { state ->
                when (state) {
                    is RatesState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is RatesState.Loaded -> {
                        binding.progressBar.visibility = View.GONE

                        val list: List<Currency> = state.rates.entries.map { (code, rate) ->
                            Currency(
                                code = code,
                                name = code,
                                flag = "",
                                rate = rate
                            )
                        }

                        adapter.submitList(list)
                    }

                    is RatesState.Error -> {
                        binding.progressBar.visibility = View.GONE

                        adapter.submitList(emptyList())
                    }

                    else -> {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


