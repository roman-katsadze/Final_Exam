package com.example.final_examapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_examapplication.R
import com.example.final_examapplication.databinding.FragmentHistoryBinding
import com.example.final_examapplication.ui.adapters.TransactionAdapter
import com.example.final_examapplication.viewmodels.FinanceViewModel
import com.example.final_examapplication.viewmodels.FinanceViewModelFactory

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FinanceViewModel by viewModels {
        FinanceViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TransactionAdapter { t ->
            viewModel.deleteTransaction(t)
        }

        binding.recyclerTransactions.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerTransactions.adapter = adapter

        viewModel.transactions.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)

            binding.textCount.text = getString(R.string.transactions_count, list.size)
            binding.textEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }

        binding.btnClearAll.setOnClickListener {
            viewModel.clearAllTransactions()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




