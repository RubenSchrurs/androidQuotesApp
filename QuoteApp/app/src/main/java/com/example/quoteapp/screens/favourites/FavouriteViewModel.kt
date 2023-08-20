package com.example.quoteapp.screens.favourites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.quoteapp.database.Quote
import com.example.quoteapp.repository.QuoteRepository
import kotlinx.coroutines.launch

class FavouriteViewModel(application: Application): AndroidViewModel(application) {
    private val repository: QuoteRepository = QuoteRepository(application)
    val favouriteQuotes: LiveData<List<Quote>> = repository.favouriteQuotes

    fun update(quote: Quote) {
        viewModelScope.launch {
            repository.update(quote)
        }
    }
}