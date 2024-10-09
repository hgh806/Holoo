package com.holoo.map.core.data.remote.api

import com.holoo.map.core.data.remote.response.DirectionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface DirectionApiService {
    @GET("direction/no-traffic")
    suspend fun getDirection(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Header("Api-Key") key: String,
    ): Response<DirectionResponse>
}