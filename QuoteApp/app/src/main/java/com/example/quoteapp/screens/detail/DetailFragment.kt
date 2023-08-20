package com.example.quoteapp.screens.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.quoteapp.MainActivity
import com.example.quoteapp.R
import com.example.quoteapp.database.Quote
import com.example.quoteapp.databinding.FragmentDetailBinding
import com.example.quoteapp.screens.dashboard.QuoteViewModel
import com.example.quoteapp.screens.dashboard.QuoteViewModelFactory

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var quoteViewModel: QuoteViewModel // Declare the ViewModel instance

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val args: DetailFragmentArgs by navArgs()
        val selectedQuote: Quote = args.quoteArg

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = QuoteViewModelFactory(application)
        quoteViewModel = ViewModelProvider(this, viewModelFactory)[QuoteViewModel::class.java]
        binding.quoteViewModel = quoteViewModel

        binding.textViewQuote.text = selectedQuote.text
        binding.textViewAuthor.text = selectedQuote.author

        val favouriteSwitch = binding.isFavouriteSwitch
        favouriteSwitch.isChecked = selectedQuote.isFavourite

        favouriteSwitch.setOnClickListener{
            selectedQuote.isFavourite = !selectedQuote.isFavourite
            quoteViewModel.update(quote = selectedQuote)
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as? MainActivity)?.updateTitle("Quote Details")

        return binding.root
    }
}
