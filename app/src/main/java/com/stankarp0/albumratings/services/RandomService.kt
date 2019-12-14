package com.stankarp0.albumratings.services

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://musicrating.herokuapp.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi_converter))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface RandomApiService {

    @GET("/")
    fun randomAlbum(): Deferred<AlbumObject>

    @GET("year/{year}")
    fun randomFromYear(@Path("year") year: Long): Deferred<AlbumObject>

    @GET("decade/{decade}")
    fun randomFromDecade(@Path("decade") decade: Long): Deferred<AlbumObject>

    @GET("performers")
    fun allPerformers(@Query("page") page: Int = 0): Deferred<PerformerObject>

    @GET("performers/{performerId}")
    fun performer(@Path("performerId") performerId: Int): Deferred<PerformerProperty>

    @GET("performers/query")
    fun performersQuery(@Query("query") query: String, @Query("page") page: Int = 0): Deferred<PerformerObject>

    @GET("albums")
    fun allAlbums(@Query("page") page: Int = 0): Deferred<AlbumObject>

    @GET("albums/query")
    fun albumsQuery(@Query("query") query: String, @Query("page") page: Int = 0): Deferred<AlbumObject>

    @GET("albums/{albumId}")
    fun album(@Path("albumId") albumId: Int): Deferred<AlbumProperty>

    @GET("albums/performer")
    fun performerAlbums(@Query("performerId") performer: Int): Deferred<AlbumObject>

    @GET("ratings")
    fun allRatings(@Query("page") page: Int = 0): Deferred<RatingObject>

    @GET("ratings/user")
    fun userRatings(@Query("user") user: String): Deferred<RatingObject>

    @GET("ratings/album")
    fun albumRatings(@Query("albumId") albumId: Int): Deferred<RatingObject>

}

object RandomApi {
    val retrofitService : RandomApiService by lazy {
        retrofit.create(RandomApiService::class.java) }
}