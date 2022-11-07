package com.ihfazh.warnain.category_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihfazh.warnain.domain.CategoryDetail
import com.ihfazh.warnain.repositories.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class CategoryDetailViewModel(
    private val repository: CategoryRepository
): ViewModel() {

    private val _images = MutableStateFlow(listOf<CategoryDetail>())
    val images: StateFlow<List<CategoryDetail>> = _images


    fun getDetail(id: Int){
        viewModelScope.launch{
            _images.value = repository.getCategoryDetail(id)
        }
    }

    private val _count = MutableStateFlow(1)
    val count = _count.asStateFlow()
    fun setCount(value: Int) { _count.value = value }

    fun print(id: Int){
        viewModelScope.launch{
            repository.print(id, count.value)
        }

    }

}