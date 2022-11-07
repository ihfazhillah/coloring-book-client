package com.ihfazh.warnain.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Int,
    val title: String,
    val thumbnail: String
): Parcelable
