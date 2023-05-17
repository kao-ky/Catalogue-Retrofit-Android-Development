package com.example.t2kao.api

import com.example.t2kao.models.Product
import com.example.t2kao.models.ProductsListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("products/category/{type}")
    suspend fun getProductsList(@Path("type") productType: String): Response<ProductsListResponse>
}