package com.stankarp0.albumratings.services

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://musicrating.herokuapp.com/performers/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi_converter))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface PerformerApiService {

    @GET("/")
    fun all(): Deferred<PerformerObject>

    @GET("{performerId}")
    fun performer(@Path("performerId") performerId: Int): Deferred<PerformerProperty>

    @GET("query")
    fun query(@Query("query") query: String): Deferred<PerformerObject>

}

object PerformerApi {

    val retrofitService : PerformerApiService by lazy {
        retrofit.create(PerformerApiService::class.java) }

}