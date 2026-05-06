package com.example.mobiledatalogger

import retrofit2.http.Body
import retrofit2.http.POST

interface AiApiService {
    @POST("ai-summary")
    suspend fun getAiSummary(
        @Body request: AiSummaryRequest
    ): AiSummaryResponse
}