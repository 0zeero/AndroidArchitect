package com.example.features.home.di

import com.example.features.home.data.api.HomeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Hilt Module for the Home Feature.
 * 
 * This module demonstrates Scenario A:
 * - It receives the Retrofit instance from the :core module (via DI).
 * - It uses that instance to create its own feature-specific API interface.
 * 
 * This keeps the Home feature decoupled from the network configuration,
 * while still allowing it to define its own endpoints.
 */
@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    /**
     * Provides the HomeApi interface.
     * 
     * @param retrofit The shared Retrofit instance provided by :core's NetworkModule.
     * @return An implementation of HomeApi.
     */
    @Provides
    @Singleton
    fun provideHomeApi(retrofit: Retrofit): HomeApi {
        return retrofit.create(HomeApi::class.java)
    }
}
