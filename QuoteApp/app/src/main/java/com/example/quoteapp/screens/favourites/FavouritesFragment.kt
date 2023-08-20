package com.example.quoteapp.screens.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quoteapp.MainActivity
import com.example.quoteapp.R
import com.example.quoteapp.databinding.FragmentFavoritesBinding

class FavouritesFragment: Fragment() {
    private lateinit var binding: FragmentFavoritesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = FavouriteViewModelFactory(application)
        val favouriteViewModel = ViewModelProvider(this, viewModelFactory)[FavouriteViewModel::class.java]
        binding.favouriteViewModel = favouriteViewModel

        val adapter = FavouriteAdapter{ updatedQuote ->
            favouriteViewModel.update(updatedQuote)
        }
        binding.recyclerView.adapter = adapter

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        favouriteViewModel.favouriteQuotes.observe(viewLifecycleOwner) { quotes ->
            adapter.setFavourites(quotes)
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as? MainActivity)?.updateTitle("Favourites")

        return binding.root
    }

}