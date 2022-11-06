package com.ihfazh.warnain.remote

import com.ihfazh.warnain.remote.data.CategoriesResponse
import com.ihfazh.warnain.remote.data.CategoryItemResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WarnainService {
    @GET("categories/")
    suspend fun getCategories(
        @Query("search") search: String = "",
        @Query("page") page: Int = 1
    ): CategoriesResponse

    @GET("categories/last-access/")
    suspend fun getLastAccess(): List<CategoryItemResponse>
}