package com.dirzaaulia.triviaquiz.repository

import androidx.annotation.WorkerThread
import com.dirzaaulia.triviaquiz.network.Service
import com.dirzaaulia.triviaquiz.utils.NotFoundException
import com.dirzaaulia.triviaquiz.utils.ResponseResult
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
  private val service: Service
) {

  @WorkerThread
  fun getCategory() = flow {
    try {
      service.getCategory().body()?.let {
        emit(ResponseResult.Success(it))
      } ?: run {
        throw NotFoundException()
      }
    } catch (e: Exception) {
      emit(ResponseResult.Error(e))
    }
  }

  @WorkerThread
  fun getQuestions(
    numberOfQuestions: Int,
    category: Int,
    difficulty: String,
    type: String
  ) = flow {
    try {
      service.getQuestions(
        numberOfQuestions,
        category,
        difficulty,
        type
      ).body()?.let {
        emit(ResponseResult.Success(it))
      } ?: run {
        throw NotFoundException()
      }
    } catch (e: Exception) {
      emit(ResponseResult.Error(e))
    }
  }
}