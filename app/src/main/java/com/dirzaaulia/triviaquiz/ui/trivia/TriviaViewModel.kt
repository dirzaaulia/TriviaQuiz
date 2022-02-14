package com.dirzaaulia.triviaquiz.ui.trivia

import androidx.annotation.MainThread
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dirzaaulia.triviaquiz.data.model.Questions
import com.dirzaaulia.triviaquiz.repository.Repository
import com.dirzaaulia.triviaquiz.utils.success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TriviaViewModel @Inject constructor(
  private val repository: Repository
): ViewModel() {

  val loading = mutableStateOf(true)

  private val _questions: MutableStateFlow<List<Questions>?> = MutableStateFlow(null)
  val questions = _questions.asStateFlow()

  val currentQuestion = MutableStateFlow(0)

  @MainThread
  fun setCurrentQuestion(value: Int) {
    currentQuestion.value = value
  }

  fun getQuestions(
    numberOfQuestions: Int,
    category: Int,
    difficulty: String,
    type: String
  ) {
    repository.getQuestions(numberOfQuestions, category, difficulty, type)
      .onEach {
        it.success { data ->
          data.results?.map { questions ->
            val allAnswers = ArrayList<String>().apply {
              add(questions.correctAnswer.toString())
              questions.incorrectAnswer?.let { list -> addAll(list) }
            }
            questions.allAnswer = allAnswers.shuffled()
          }
          _questions.value = data.results
          loading.value = false
        }
      }
      .launchIn(viewModelScope)
  }
}