package com.stankarp0.albumratings.services

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://musicrating.herokuapp.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi_converter))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface RandomApiService {

    @GET("/")
    fun random(): Deferred<AlbumObject>

    @GET("/year/{year}")
    fun year(@Path("year") year: Long): Deferred<AlbumObject>

    @GET("/decade/{decade}")
    fun decade(@Path("decade") decade: Long): Deferred<AlbumObject>

}

object RandomApi {
    val retrofitService : RandomApiService by lazy {
        retrofit.create(RandomApiService::class.java) }
}