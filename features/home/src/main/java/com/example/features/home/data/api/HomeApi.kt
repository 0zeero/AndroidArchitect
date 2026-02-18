package com.example.features.home.data.api

import com.example.features.home.data.model.FeaturedItemDto
import com.example.features.home.data.model.HomeResponse
import retrofit2.http.GET

/**
 * Home Feature API Interface.
 * 
 * This interface defines the endpoints specific to the Home feature.
 * It is created using the shared Retrofit instance from the :core module.
 */
interface HomeApi {

    @GET("home")
    suspend fun getHomeData(): HomeResponse

    @GET("featured")
    suspend fun getFeaturedItems(): List<FeaturedItemDto>
}
