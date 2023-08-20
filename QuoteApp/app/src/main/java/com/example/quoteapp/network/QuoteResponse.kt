package com.example.quoteapp.network

import com.fasterxml.jackson.annotation.JsonProperty

data class QuoteResponse (
    @JsonProperty("quote") val quote: String,
    @JsonProperty("author") val author: String,
    @JsonProperty("category") val category: String
)