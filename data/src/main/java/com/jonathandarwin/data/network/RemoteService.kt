package com.jonathandarwin.data.network

import com.jonathandarwin.domain.dto.convert.ConvertResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
interface RemoteService {

    @GET("/convert")
    suspend fun convert(@Query("from") from: String,
                @Query("to") to: String,
                @Query("amount") amount: String,
                @Query("api_key") apiKey: String): Response<ConvertResponse>
}