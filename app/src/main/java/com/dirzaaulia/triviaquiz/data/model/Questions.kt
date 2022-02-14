package com.dirzaaulia.triviaquiz.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class Questions(
  val category: String?,
  val type: String?,
  val question: String?,
  @Json(name = "correct_answer")
  val correctAnswer: String?,
  @Json(name = "incorrect_answers")
  val incorrectAnswer: List<String>?,
  var allAnswer: List<String>?
)