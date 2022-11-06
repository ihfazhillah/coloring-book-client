package com.ihfazh.warnain.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ihfazh.warnain.domain.Category
import com.ihfazh.warnain.remote.WarnainService
import com.ihfazh.warnain.remote.paging_source.CategoriesPagingSource
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class DefaultCategoryRepository(
    private val remote: WarnainService
): CategoryRepository {
    override fun getCategories(search: String): Flow<PagingData<Category>> {
        val pagingSource = CategoriesPagingSource(
            remote, search
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

}