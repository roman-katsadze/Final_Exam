package com.example.final_examapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.final_examapplication.R
import com.example.final_examapplication.databinding.FragmentCurrencyConvertBinding
import com.example.final_examapplication.viewmodels.CurrencyViewModel
import com.example.final_examapplication.viewmodels.CurrencyViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CurrencyConvertFragment : Fragment() {

    private var _binding: FragmentCurrencyConvertBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CurrencyViewModel by viewModels {
        CurrencyViewModelFactory(requireContext())
    }

    private val currencies = listOf("USD", "EUR", "GEL", "GBP")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyConvertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, currencies)
        binding.spinnerFrom.setAdapter(adapter)
        binding.spinnerFrom.setText("USD", false)


        binding.chipGel.isChecked = true


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.convertResult.collectLatest { text ->
                if (text.isNotBlank()) binding.tvResult.text = text
            }
        }

        binding.btnConvert.setOnClickListener {
            val amount = binding.etAmount.text?.toString()?.trim()?.toDoubleOrNull()
            if (amount == null || amount <= 0) {
                binding.tvResult.text = getString(R.string.error_amount)
                return@setOnClickListener
            }

            val from = binding.spinnerFrom.text?.toString()?.trim().orEmpty()
            if (from.isBlank()) {
                binding.tvResult.text = getString(R.string.error_amount)
                return@setOnClickListener
            }


            val checkedId = binding.chipGroupTo.checkedChipId
            if (checkedId == View.NO_ID) {
                binding.tvResult.text = getString(R.string.to_currency_label)
                return@setOnClickListener
            }

            val to = view.findViewById<com.google.android.material.chip.Chip>(checkedId).text.toString()
            viewModel.convertAndSave(amount, from, to)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





