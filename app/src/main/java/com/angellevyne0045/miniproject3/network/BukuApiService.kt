package com.angellevyne0045.miniproject3.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://raw.githubusercontent.com/angelevy/booksMobproAngel/refs/heads/main/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface BukuApiService {
    @GET("bookss.json")
    suspend fun getBuku(): String
}

object BukuApi {
    val service: BukuApiService by lazy {
        retrofit.create(BukuApiService::class.java)
    }
}