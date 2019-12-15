package com.stankarp0.albumratings.services

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private val moshi_converter = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private const val BASE_URL = "https://musicrating.herokuapp.com/"


private val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(20, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .build();


private val retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .addConverterFactory(MoshiConverterFactory.create(moshi_converter))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()


interface ApiService {

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
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java) }
}
