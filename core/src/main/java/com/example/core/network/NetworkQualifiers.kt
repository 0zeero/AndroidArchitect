package com.example.core.network

import javax.inject.Qualifier

/**
 * Network Qualifiers for Dependency Injection.
 * 
 * These qualifiers allow features to request specific network configurations
 * while Core maintains control over the base setup.
 */

/**
 * Default OkHttpClient with standard timeouts (30s) and auth headers.
 * Use this for most API calls.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultClient

/**
 * OkHttpClient with extended timeouts (60s).
 * Use for file uploads, large downloads, or slow endpoints.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LongTimeoutClient

/**
 * OkHttpClient without authentication interceptor.
 * Use for public endpoints that don't require auth (login, registration).
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoAuthClient

/**
 * Pre-configured OkHttpClient.Builder that features can customize further.
 * Features can add their own interceptors while inheriting core config.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseClientBuilder
