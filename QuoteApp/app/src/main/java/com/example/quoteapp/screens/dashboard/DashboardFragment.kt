package com.example.quoteapp.screens.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quoteapp.MainActivity
import com.example.quoteapp.R
import com.example.quoteapp.databinding.FragmentDashboardBinding


class DashboardFragment: Fragment() {
    lateinit var binding: FragmentDashboardBinding
    lateinit var quoteViewModel: QuoteViewModel // Declare the ViewModel instance

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = QuoteViewModelFactory(application)
        quoteViewModel = ViewModelProvider(this, viewModelFactory)[QuoteViewModel::class.java]
        binding.quoteViewModel = quoteViewModel

        val adapter = QuoteAdapter(QuoteAdapter.OnClickListener{ quote, toDetailsFragment ->
            if (!toDetailsFragment){
                quoteViewModel.displayDetails(quote)
            } else {
                quoteViewModel.update(quote)
            }
        })

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        quoteViewModel.allQuotes.observe(viewLifecycleOwner) {
            adapter.setQuotes(it)
        }

        quoteViewModel.navigateToSelectedQuote.observe(viewLifecycleOwner) {
            if (it != null) {
                this.findNavController().navigate(
                    DashboardFragmentDirections.actionDashboardFragmentToDetailFragment(it)
                )
                quoteViewModel.displayDetailsComplete()
            }
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as? MainActivity)?.updateTitle("Quotes")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        quoteViewModel.allQuotes.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                // Fetch quotes and insert only if not already fetched
                Log.d("DashboardFragment", "Fetching and inserting quotes from API")
                quoteViewModel.fetchQuotesAndInsertIntoDatabaseOnce(limit = 10)
            }
        }

    }
}