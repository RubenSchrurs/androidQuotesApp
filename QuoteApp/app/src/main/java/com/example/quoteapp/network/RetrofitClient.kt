package com.example.quoteapp.network

import com.example.quoteapp.api.QuoteApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.api-ninjas.com/v1/" //API base URL
    private val httpClient = OkHttpClient.Builder()
    private const val API_KEY = "X8Qp2F/79tmIRGAVLt68Qg==KmDQuQfF1bHugBPH"

    init {
        //Logging interceptor for debugging
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(loggingInterceptor)

        //Add API_KEY
        httpClient.addInterceptor{ chain: Interceptor.Chain ->
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .header("accept", "application/json")
                .header("X-Api-Key", API_KEY)
                .build()
            chain.proceed(newRequest)
        }
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient.build())
        .addConverterFactory(JacksonConverterFactory.create())
        .build()

    fun getRetrofitInstance(): Retrofit {
        return retrofit
    }

    fun getDynamicUrlService(): QuoteApiService {
        val dynamicUrlRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

        return dynamicUrlRetrofit.create(QuoteApiService::class.java)
    }
}