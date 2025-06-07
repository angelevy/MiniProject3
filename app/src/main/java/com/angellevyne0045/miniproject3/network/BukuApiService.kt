package com.angellevyne0045.miniproject3.network

import com.angellevyne0045.miniproject3.model.Buku
import com.angellevyne0045.miniproject3.model.OpStatus
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

private const val BASE_URL = "https://rest-buku-api-angel-production.up.railway.app/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface BukuApiService {
    @GET("books")
    suspend fun getBuku(): List<Buku>
    @Multipart
    @POST("books")
    suspend fun postBuku(
        @Header("authorization") userId: String,
        @Part("title") title: RequestBody,
        @Part("author") author: RequestBody,
        @Part image: MultipartBody.Part
    ): OpStatus
}

object BukuApi {
    val service: BukuApiService by lazy {
        retrofit.create(BukuApiService::class.java)
    }
    enum class ApiStatus { LOADING, SUCCESS, FAILED }
}
