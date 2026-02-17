package com.example.features.home.data.repository

import com.example.features.home.data.api.HomeApi
import com.example.features.home.data.model.FeaturedItemDto
import com.example.features.home.data.model.HomeResponse
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for the Home feature.
 * 
 * Acts as a single source of truth for Home data.
 * Uses the HomeApi (which is backed by the shared Retrofit instance from :core).
 */
@Singleton
class HomeRepository @Inject constructor(
    private val homeApi: HomeApi
) {

    /**
     * Fetches the main home screen data.
     */
    suspend fun getHomeData(): Result<HomeResponse> {
        return try {
            val response = homeApi.getHomeData()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Fetches featured items for the home screen.
     */
    suspend fun getFeaturedItems(): Result<List<FeaturedItemDto>> {
        return try {
            val response = homeApi.getFeaturedItems()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
