package com.ihfazh.warnain.repositories

import androidx.paging.PagingData
import com.ihfazh.warnain.domain.Category
import com.ihfazh.warnain.domain.CategoryDetail
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(search: String): Flow<PagingData<Category>>
    suspend fun getLastAccess(): List<Category>
    suspend fun getCategoryDetail(id: Int): List<CategoryDetail>
    suspend fun print(id: Int, copies: Int): Boolean
}