package com.ihfazh.warnain.repositories

import androidx.paging.PagingData
import com.ihfazh.warnain.domain.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(search: String): Flow<PagingData<Category>>
    suspend fun getLastAccess(): List<Category>
}