package com.example.mobiledatalogger

data class AiSummaryRequest(
    val sleep: String,
    val activity: String,
    val mood: String,
    val notes: String
)

data class AiSummaryResponse(
    val suggestion: String
)