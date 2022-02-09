package com.dirzaaulia.triviaquiz.ui.home

import androidx.annotation.MainThread
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dirzaaulia.triviaquiz.data.model.Category
import com.dirzaaulia.triviaquiz.repository.Repository
import com.dirzaaulia.triviaquiz.utils.success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val repository: Repository
): ViewModel() {

  val loading = mutableStateOf(true)

  private val selectedCategory = MutableStateFlow(0)
  private val selectedDifficulty = MutableStateFlow<String?>(null)
  private val selectedType = MutableStateFlow<String?>(null)

  private val _category: MutableStateFlow<List<Category>?> = MutableStateFlow(null)
  val category = _category.asStateFlow()

  init {
    getCategory()
  }

  @MainThread
  fun setSelectedCategory(category: Int) {
    selectedCategory.value = category
  }

  @MainThread
  fun setSelectedDifficulty(difficulty: String) {
    selectedDifficulty.value = difficulty
  }

  @MainThread
  fun setSelectedType(type: String) {
    selectedType.value = type
  }

  private fun getCategory() {
    repository.getCategory()
      .onEach {
        it.success { data ->
          val anyCategory = Category(0, "Any Category")
          val mutableList = data.category?.toMutableList()?.apply {
            add(anyCategory)
          }
          val sortedList = mutableList?.sortedWith(compareBy { item -> item.id })
          _category.value = sortedList?.toList()
          loading.value = false
        }
      }
      .launchIn(viewModelScope)
  }

}