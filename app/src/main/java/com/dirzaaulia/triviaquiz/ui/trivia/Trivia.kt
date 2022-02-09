package com.dirzaaulia.triviaquiz.ui.trivia

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.statusBarsHeight

@Composable
fun Trivia(
  numberOfQuestions: Int,
  category: Int?,
  difficulty: String?,
  type: String?
) {

  val viewModel: TriviaViewModel = hiltViewModel()

//  LaunchedEffect(viewModel) {
//    category?.let { category ->
//      difficulty?.let { difficulty ->
//        viewModel.getQuestions(numberOfQuestions, category, difficulty) }
//    }
//  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .navigationBarsHeight()
      .statusBarsHeight(),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = "$numberOfQuestions | $category | $difficulty | $type",
      color = MaterialTheme.colors.onSurface
    )
  }

}