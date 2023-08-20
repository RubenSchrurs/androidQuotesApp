package com.example.quoteapp.api

import com.fasterxml.jackson.databind.JsonNode
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteApiService {
    @GET("quotes")
    suspend fun getQuotes(@Query("limit") limit: Int): Response<JsonNode>
}