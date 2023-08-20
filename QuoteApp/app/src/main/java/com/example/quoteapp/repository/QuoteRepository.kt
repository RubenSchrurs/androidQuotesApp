package com.example.quoteapp.repository

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.quoteapp.database.Quote
import com.example.quoteapp.database.QuoteDao
import com.example.quoteapp.database.QuoteDatabase
import com.example.quoteapp.network.QuoteResponse
import com.example.quoteapp.network.RetrofitClient
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuoteRepository(application: Application){
    private val quoteDao: QuoteDao
    private val quoteApiService = RetrofitClient.getDynamicUrlService()
    val allQuotes: LiveData<List<Quote>>
    val favouriteQuotes: LiveData<List<Quote>>
    private var initialInsertDone: Boolean = false

    init {
        val database = QuoteDatabase.getInstance(application)
        quoteDao = database.quoteDao()
        allQuotes = quoteDao.getAllQuotes()
        favouriteQuotes = quoteDao.getAllFavouriteQuotes()
    }

    @WorkerThread
    suspend fun update(quote: Quote){
        withContext(Dispatchers.IO){
            quoteDao.update(quote)
        }
    }

    @WorkerThread
    suspend fun fetchQuotesFromApi(limit: Int): List<Quote> {
        val response = quoteApiService.getQuotes(limit)
        if (response.isSuccessful){
            val jsonBody = response.body()?.toString()
            if (jsonBody != null){
                val objectMapper = ObjectMapper()
                val quotesResponse: List<QuoteResponse> = objectMapper.readValue(jsonBody,
                    objectMapper.typeFactory.constructCollectionType(List::class.java, QuoteResponse::class.java))

                val quotes: List<Quote> = quotesResponse.map { quoteResponse ->
                    Quote(text = quoteResponse.quote, author = quoteResponse.author, isFavourite = false)
                }
                return quotes
            }
        }
        return emptyList()
    }

    @WorkerThread
    suspend fun fetchQuotesAndInsertIntoDatabaseOnce(limit: Int) {
        if (!initialInsertDone) {
            val quotes = fetchQuotesFromApi(limit)

            // Insert the new quotes into the database
            withContext(Dispatchers.IO) {
                for (quote in quotes) {
                    quoteDao.insert(quote)
                }
            }

            initialInsertDone = true
        }
    }
}