package com.ihfazh.warnain.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryDetail(
    val id: Int,
    val source: String,
    val image: String
): Parcelable
