package com.dirzaaulia.triviaquiz.utils

import android.text.Html
import android.text.Spanned

fun htmlToTextFormatter(value: String?): Spanned? {
  value.let {
    return Html.fromHtml(value, Html.FROM_HTML_MODE_COMPACT)
  }
}