package com.example.quoteapp.screens.dashboard

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quoteapp.database.Quote
import com.example.quoteapp.repository.QuoteRepository
import kotlinx.coroutines.launch

class QuoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: QuoteRepository = QuoteRepository(application)
    val allQuotes: LiveData<List<Quote>> = repository.allQuotes

    private val _navigateToSelectedQuote = MutableLiveData<Quote?>()

    val navigateToSelectedQuote: MutableLiveData<Quote?>
        get() = _navigateToSelectedQuote

    fun displayDetails(quote: Quote){
        _navigateToSelectedQuote.value = quote
    }

    fun displayDetailsComplete(){
        _navigateToSelectedQuote.value = null
    }

    fun update(quote: Quote) {
        viewModelScope.launch {
            repository.update(quote)
        }
    }

    fun fetchQuotesAndInsertIntoDatabaseOnce(limit: Int){
        viewModelScope.launch {
            try {
                repository.fetchQuotesAndInsertIntoDatabaseOnce(limit)
            } catch (e: Exception){
                Log.e("FetchQuotesAndInsertError", "ERROR", e)
            }
        }
    }
}