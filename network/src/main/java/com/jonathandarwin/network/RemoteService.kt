package com.jonathandarwin.network

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
interface RemoteService {

    @GET("/convert")
    fun convert(@Query("from") from: String,
                @Query("to") to: String,
                @Query("apiKey") apiKey: String)
}