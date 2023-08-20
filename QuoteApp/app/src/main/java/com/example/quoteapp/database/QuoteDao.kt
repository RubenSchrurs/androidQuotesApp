package com.example.quoteapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuoteDao {

    @Insert
    fun insert(quote: Quote)

    @Update
    fun update(quote: Quote)

    @Delete
    fun delete(quote: Quote)

    @Query("DELETE FROM quote_table")
    fun deleteAllQuotes()

    @Query("SELECT * FROM quote_table")
    fun getAllQuotes() : LiveData<List<Quote>>

    @Query("SELECT * FROM quote_table WHERE isFavourite = 1")
    fun getAllFavouriteQuotes() : LiveData<List<Quote>>

    @Query("SELECT * FROM quote_table")
    fun getAllQuotesNonLiveData(): List<Quote>

}