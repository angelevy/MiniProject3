package com.angellevyne0045.miniproject3.network

import com.angellevyne0045.miniproject3.model.Buku
import com.angellevyne0045.miniproject3.model.OpStatus
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

private const val BASE_URL = "https://rest-buku-api-angel-production-2f79.up.railway.app/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface BukuApiService {
    @GET("books")
    suspend fun getBuku(
        @Header("Authorization") userId: String
    ): List<Buku>

    @Multipart
    @POST("books")
    suspend fun postBuku(
        @Header("Authorization") userId: String,
        @Part("title") title: RequestBody,
        @Part("author") author: RequestBody,
        @Part image: MultipartBody.Part
    ): OpStatus

    @DELETE("books/{id}")
    suspend fun deleteBuku(
        @Header("Authorization") userId: String,
        @Path("id") id: String
    ): OpStatus

    @Multipart
    @PUT("/books/{id}")
    suspend fun editBuku(
        @Header("Authorization") email: String,
        @Path("id") id: String,
        @Part("title") title: RequestBody,
        @Part("author") author: RequestBody,
        @Part image: MultipartBody.Part? = null
    ): OpStatus
}


object BukuApi {
    val service: BukuApiService by lazy {
        retrofit.create(BukuApiService::class.java)
    }

    fun getBukuUrl(image: String): String {
        return if (image.startsWith("http")) image
        else "https://rest-buku-api-angel-production-2f79.up.railway.app$image"
    }
}

    enum class ApiStatus { LOADING, SUCCESS, FAILED }
