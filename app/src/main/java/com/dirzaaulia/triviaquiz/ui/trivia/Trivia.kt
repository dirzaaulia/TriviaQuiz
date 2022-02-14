package com.dirzaaulia.triviaquiz.ui.trivia

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dirzaaulia.triviaquiz.data.model.Questions
import com.dirzaaulia.triviaquiz.ui.common.CommonLoading
import com.dirzaaulia.triviaquiz.ui.home.HomeTopBar
import com.dirzaaulia.triviaquiz.ui.main.MainActivity
import com.dirzaaulia.triviaquiz.utils.htmlToTextFormatter
import com.google.accompanist.insets.navigationBarsPadding

@Composable
fun Trivia(
  numberOfQuestions: Int,
  category: Int?,
  difficulty: String?,
  type: String?
) {

  //TODO Navigate back and set Home to default

  val activity = (LocalContext.current as? MainActivity)
  val viewModel: TriviaViewModel = hiltViewModel()
  val loading = viewModel.loading.value
  val questions by viewModel.questions.collectAsState()
  val currentQuestion by viewModel.currentQuestion.collectAsState()

  LaunchedEffect(viewModel) {
    category?.let { category ->
      difficulty?.let { difficulty ->
        type?.let { type ->
          viewModel.getQuestions(numberOfQuestions, category, difficulty, type)
        }
      }
    }
  }

  CommonLoading(visibility = loading)
  AnimatedVisibility(visible = !loading) {
    Scaffold(
      modifier = Modifier.navigationBarsPadding(),
      topBar = { HomeTopBar() }
    ) {
      if (questions?.isNotEmpty() == true) {
        Question(questions, currentQuestion)
      } else {
        //TODO Shown question empty error and back to Main
        activity?.finish()
      }
    }
  }
}

@Composable
fun Question(
  questions: List<Questions>?,
  currentQuestion: Int
) {

  val question = questions?.get(currentQuestion)
  val (selectedOption, onOptionSelected) = remember {
    mutableStateOf(question?.allAnswer?.get(0) ?: "")
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
  ) {
    Text(
      text = question?.question.toString(),
      style = MaterialTheme.typography.h6,
      modifier = Modifier.padding(24.dp)
    )
    Column(
      modifier = Modifier
        .selectableGroup()
        .padding(top = 8.dp)
    ) {
      question?.allAnswer?.forEach { text ->
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .selectable(
              selected = text == selectedOption,
              onClick = { onOptionSelected(text) },
              role = Role.RadioButton
            )
            .padding(horizontal = 24.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          RadioButton(selected = (text == selectedOption), onClick = null)
          Text(
            text = htmlToTextFormatter(text).toString(),
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(start = 16.dp)
          )
        }
      }
    }
  }
}