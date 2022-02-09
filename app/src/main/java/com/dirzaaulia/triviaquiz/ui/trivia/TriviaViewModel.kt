package com.dirzaaulia.triviaquiz.ui.trivia

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

  private val _questions: MutableStateFlow<List<Questions>?> = MutableStateFlow(null)
  val questions = _questions.asStateFlow()

  fun getQuestions(
    numberOfQuestions: Int,
    category: Int,
    difficulty: String,
    type: String
  ) {
    repository.getQuestions(numberOfQuestions, category, difficulty, type)
      .onEach {
        it.success { data ->
          _questions.value = data.results
        }
      }
      .launchIn(viewModelScope)
  }
}