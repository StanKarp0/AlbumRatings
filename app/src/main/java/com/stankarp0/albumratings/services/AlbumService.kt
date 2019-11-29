package com.stankarp0.albumratings.services

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://musicrating.herokuapp.com/albums/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi_converter))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface AlbumApiService {

    @GET("/")
    fun all(): Deferred<AlbumObject>

    @GET("query")
    fun query(@Query("query") query: String): Deferred<AlbumObject>

    @GET("performer")
    fun performer(@Query("performerId") performer: Int): Deferred<AlbumObject>

}

object AlbumApi {

    val retrofitService : AlbumApiService by lazy {
        retrofit.create(AlbumApiService::class.java) }

}