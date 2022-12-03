package com.ihfazh.warnain.remote.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ihfazh.warnain.domain.Category
import com.ihfazh.warnain.domain.CategorySorter
import com.ihfazh.warnain.domain.toQueryString
import com.ihfazh.warnain.remote.WarnainService

class CategoriesPagingSource(
    private val remote: WarnainService,
    private val query: String,
    private val sort: CategorySorter
) : PagingSource<Int, Category>() {
    override fun getRefreshKey(state: PagingState<Int, Category>): Int? {
        return state.anchorPosition?.let { pos ->
            val anchorPage = state.closestPageToPosition(pos)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Category> {
        return try{
            val nextPageNumber = params.key ?: 1
            val response = remote.getCategories(query, sort.toQueryString(), nextPageNumber)
            LoadResult.Page(
                response.results.map { respItem -> Category(respItem.id, respItem.title, respItem.thumbnail)},
                nextKey = if ( response.next != null ) nextPageNumber + 1 else null,
                prevKey =  if ( response.previous != null ) nextPageNumber - 1 else null,
            )
        } catch (e: Exception){
            // TODO
            LoadResult.Error(e)
        }
    }
}