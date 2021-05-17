package com.zaidzakir.androidtestingsample.shoppingListApp.data.remote

import com.zaidzakir.androidtestingsample.BuildConfig
import com.zaidzakir.androidtestingsample.shoppingListApp.data.remote.responses.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *Created by Zaid Zakir
 */
interface PixabayAPI {

    @GET("/api/")
    suspend fun searchImage(
        @Query("q") searchQuery:String,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ):Response<ImageResponse>
}