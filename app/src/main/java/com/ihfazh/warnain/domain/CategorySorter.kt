package com.ihfazh.warnain.domain


enum class CategorySorter {
    TITLE,
    ACCESS,
    FREQ
}

fun CategorySorter.toQueryString(): String {
    return when(this){
        CategorySorter.TITLE -> "title"
        CategorySorter.ACCESS -> "access"
        CategorySorter.FREQ -> "freq"
    }
}