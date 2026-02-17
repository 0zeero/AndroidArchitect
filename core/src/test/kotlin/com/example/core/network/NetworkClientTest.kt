package com.example.core.network

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import retrofit2.Call
import retrofit2.http.GET

class NetworkClientTest {

    @Test
    fun createService_returnsNonNullInstance() {
        val service = NetworkClient.createService(TestApi::class.java)
        assertThat(service).isNotNull()
    }

    @Test
    fun createService_returnsSameTypeForSameClass() {
        val service = NetworkClient.createService(TestApi::class.java)
        assertThat(service).isInstanceOf(TestApi::class.java)
    }

    private interface TestApi {
        @GET("/")
        fun get(): Call<Unit>
    }
}
