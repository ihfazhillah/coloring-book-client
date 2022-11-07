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

data class CategoryDetailResponse(
	@Json(name = "id")
	val id: Int,

	@Json(name = "source")
	val source: String,

	@Json(name = "image")
	val image: String

)
data class PrintImageBody(
	@Json(name = "copies")
	val copies: String,
)

data class PrintImageResponse(
	@Json(name = "status")
	val status: String,
)
