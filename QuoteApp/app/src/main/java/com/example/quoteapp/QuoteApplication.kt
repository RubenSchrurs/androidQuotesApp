package com.example.quoteapp

import android.app.Application
import com.example.quoteapp.network.RetrofitClient
import retrofit2.Retrofit

class QuoteApplication: Application() {
    private lateinit var retrofit: Retrofit

    override fun onCreate() {
        super.onCreate()
        retrofit = RetrofitClient.getRetrofitInstance()
    }
}