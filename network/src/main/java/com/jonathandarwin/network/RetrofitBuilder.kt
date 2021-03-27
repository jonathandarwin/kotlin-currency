package com.jonathandarwin.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
object RetrofitBuilder {

    private val retrofit = Retrofit
        .Builder()
        .baseUrl("https://api.fastforex.io")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val remoteService = retrofit.create(RemoteService::class.java)
}