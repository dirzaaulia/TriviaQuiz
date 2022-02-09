package com.dirzaaulia.triviaquiz.data.response

import androidx.annotation.Keep
import com.dirzaaulia.triviaquiz.data.model.Questions
import com.squareup.moshi.Json

@Keep
data class QuestionsResponse(
  @Json(name = "response_code")
  val responseCode: Int?,
  val results: List<Questions>?
)