package com.example.core.network

import com.example.core.network.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Core Network Module - Provides customizable network clients.
 * 
 * This module demonstrates maximum flexibility for feature customization:
 * 
 * 1. @DefaultClient       - Standard client with auth & logging (30s timeout)
 * 2. @LongTimeoutClient   - Extended timeout client (60s) for uploads/downloads
 * 3. @NoAuthClient        - Client without auth for public endpoints
 * 4. @BaseClientBuilder   - Pre-configured builder features can extend
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.example.com/"
    
    private const val DEFAULT_TIMEOUT = 30L
    private const val LONG_TIMEOUT = 60L

    // ========================================================================
    // LOGGING INTERCEPTOR (Shared)
    // ========================================================================
    
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // ========================================================================
    // AUTH INTERCEPTOR (Shared, Injectable)
    // ========================================================================
    
    @Provides
    @Singleton
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    // ========================================================================
    // STRATEGY 1: QUALIFIER-BASED CLIENTS
    // ========================================================================

    /**
     * Default OkHttpClient - Most common use case.
     * Includes: Auth header, logging, 30s timeout.
     */
    @Provides
    @Singleton
    @DefaultClient
    fun provideDefaultOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    /**
     * Long Timeout Client - For file uploads/downloads.
     * Includes: Auth header, logging, 60s timeout.
     */
    @Provides
    @Singleton
    @LongTimeoutClient
    fun provideLongTimeoutOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(LONG_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(LONG_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(LONG_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    /**
     * No-Auth Client - For public endpoints (login, register, etc.).
     * Includes: Logging, 30s timeout. NO auth header.
     */
    @Provides
    @Singleton
    @NoAuthClient
    fun provideNoAuthOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            // No auth interceptor!
            .build()
    }

    // ========================================================================
    // STRATEGY 2: BUILDER PROVIDER (Maximum Feature Flexibility)
    // ========================================================================

    /**
     * Provides a pre-configured OkHttpClient.Builder.
     * 
     * Features can inject this and add their own interceptors:
     * 
     * ```kotlin
     * @Provides
     * fun provideCustomClient(
     *     @BaseClientBuilder builder: OkHttpClient.Builder
     * ): OkHttpClient {
     *     return builder
     *         .addInterceptor(MyFeatureInterceptor())
     *         .build()
     * }
     * ```
     */
    @Provides
    @BaseClientBuilder
    fun provideBaseClientBuilder(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
    }

    // ========================================================================
    // RETROFIT INSTANCES
    // ========================================================================

    /**
     * Default Retrofit instance using the default OkHttpClient.
     */
    @Provides
    @Singleton
    fun provideRetrofit(
        @DefaultClient okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Retrofit instance for long-running operations.
     */
    @Provides
    @Singleton
    @LongTimeoutClient
    fun provideLongTimeoutRetrofit(
        @LongTimeoutClient okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Retrofit instance without authentication.
     */
    @Provides
    @Singleton
    @NoAuthClient
    fun provideNoAuthRetrofit(
        @NoAuthClient okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
