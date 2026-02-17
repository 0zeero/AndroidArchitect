package com.example.features.home.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object for the Home API response.
 * These models are specific to the Home feature.
 */
data class HomeResponse(
    @SerializedName("title")
    val title: String,
    
    @SerializedName("description")
    val description: String,
    
    @SerializedName("items")
    val items: List<HomeItemDto>
)

data class HomeItemDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("image_url")
    val imageUrl: String
)

data class FeaturedItemDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("title")
    val title: String,
    
    @SerializedName("price")
    val price: Double
)
