package com.dirzaaulia.triviaquiz.data.response

import androidx.annotation.Keep
import com.dirzaaulia.triviaquiz.data.model.Category
import com.squareup.moshi.Json

@Keep
data class CategoryResponse(
  @Json(name = "trivia_categories")
  val category: List<Category>? = null
)