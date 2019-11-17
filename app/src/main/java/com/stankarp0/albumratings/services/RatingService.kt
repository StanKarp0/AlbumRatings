package com.stankarp0.albumratings.services

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://musicrating.herokuapp.com/ratings/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi_converter))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface RatingApiService {

    @GET("/")
    fun all(): Deferred<RatingObject>

    @GET("user/")
    fun user(@Query("user") user: String): Deferred<RatingObject>

    @GET("album/")
    fun album(@Query("albumId") albumId: Int): Deferred<RatingObject>
}

object RatingApi {

    val retrofitService : RatingApiService by lazy {
        retrofit.create(RatingApiService::class.java) }

}