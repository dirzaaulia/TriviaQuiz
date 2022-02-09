package com.dirzaaulia.triviaquiz.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import com.dirzaaulia.triviaquiz.ui.main.navigation.NavGraph
import com.dirzaaulia.triviaquiz.ui.theme.TriviaQuizTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private lateinit var navController: NavHostController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // This app draws behind the system bars, so we want to handle fitting system windows
    WindowCompat.setDecorFitsSystemWindows(window, false)

    setContent {
      navController = rememberAnimatedNavController()
      TriviaQuizTheme {
        NavGraph(navController = navController)
      }
    }
  }
}