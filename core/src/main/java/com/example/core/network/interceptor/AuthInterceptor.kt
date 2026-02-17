package com.example.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Authentication Interceptor.
 * 
 * Adds authorization headers to all requests.
 * In a real app, this would inject a TokenManager or SessionRepository.
 */
@Singleton
class AuthInterceptor @Inject constructor() : Interceptor {

    // In production, inject this from a secure storage
    private var authToken: String? = null

    fun setToken(token: String?) {
        authToken = token
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        // If no token, proceed without auth header
        val token = authToken ?: return chain.proceed(originalRequest)
        
        val authenticatedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        
        return chain.proceed(authenticatedRequest)
    }
}
