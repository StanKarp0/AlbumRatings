package com.stankarp0.albumratings.services

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

val moshi_converter = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
