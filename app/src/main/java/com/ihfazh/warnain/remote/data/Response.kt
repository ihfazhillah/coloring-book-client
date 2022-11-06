package com.ihfazh.warnain.remote.data

import com.squareup.moshi.Json


data class CategoriesResponse(

	@Json(name="next")
	val next: String? = null,

	@Json(name="previous")
	val previous: String? = null,

	@Json(name="count")
	val count: Int,

	@Json(name="results")
	val results: List<CategoryItemResponse> = listOf()
)

data class CategoryItemResponse(
	@Json(name="thumbnail")
	val thumbnail: String,

	@Json(name="id")
	val id: Int,

	@Json(name="title")
	val title: String
)
