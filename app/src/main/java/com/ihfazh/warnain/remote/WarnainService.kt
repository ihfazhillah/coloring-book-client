package com.ihfazh.warnain.remote

import androidx.compose.runtime.ComposableTarget
import com.ihfazh.warnain.remote.data.*
import retrofit2.http.*

interface WarnainService {
    @GET("categories/")
    suspend fun getCategories(
        @Query("search") search: String = "",
        @Query("page") page: Int = 1
    ): CategoriesResponse

    @GET("categories/last-access/")
    suspend fun getLastAccess(): List<CategoryItemResponse>

    @GET("categories/{id}/")
    suspend fun getCategory(@Path("id") id: Int): List<CategoryDetailResponse>

    @POST("categories/print-image/{id}/")
    suspend fun printImage(
        @Path("id") id : Int,
        @Body body: PrintImageBody
    ): PrintImageResponse
}