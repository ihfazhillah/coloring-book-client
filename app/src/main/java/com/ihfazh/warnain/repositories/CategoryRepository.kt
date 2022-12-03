package com.ihfazh.warnain.repositories

import androidx.paging.PagingData
import com.ihfazh.warnain.domain.Category
import com.ihfazh.warnain.domain.CategoryDetail
import com.ihfazh.warnain.domain.CategorySorter
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(search: String, sort: CategorySorter): Flow<PagingData<Category>>
    suspend fun getLastAccess(): List<Category>
    suspend fun getCategoryDetail(id: Int): List<CategoryDetail>
    suspend fun print(id: Int, copies: Int): Boolean
}