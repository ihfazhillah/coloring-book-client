package com.ihfazh.warnain.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ihfazh.warnain.domain.Category
import com.ihfazh.warnain.domain.CategoryDetail
import com.ihfazh.warnain.domain.CategorySorter
import com.ihfazh.warnain.remote.WarnainService
import com.ihfazh.warnain.remote.data.PrintImageBody
import com.ihfazh.warnain.remote.paging_source.CategoriesPagingSource
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class DefaultCategoryRepository(
    private val remote: WarnainService
): CategoryRepository {
    override fun getCategories(search: String, sort: CategorySorter): Flow<PagingData<Category>> {
        val pagingSource = CategoriesPagingSource(
            remote, search, sort
        )

        val pager = Pager(
            config = PagingConfig(pageSize = 20),
        ) {
            pagingSource
        }.flow

        return pager
    }

    override suspend fun getLastAccess(): List<Category> {
        return remote.getLastAccess().map{
            Category(it.id, it.title, it.thumbnail)
        }
    }

    override suspend fun getCategoryDetail(id: Int): List<CategoryDetail> {
        return remote.getCategory(id).map{CategoryDetail(it.id, it.source, it.image)}
    }

    override suspend fun print(id: Int, copies: Int): Boolean {
        return try {
            val resp = remote.printImage(id, PrintImageBody(copies.toString()))
            true
        } catch (e: Exception){
            false
        }
    }

}