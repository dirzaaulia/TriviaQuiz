package com.dirzaaulia.triviaquiz.ui.main.navigation

sealed class NavScreen(val route: String) {

  object Splash: NavScreen("Splash")

  object Home: NavScreen("Home")

  object Trivia: NavScreen("Trivia") {
    const val routeWithArgument: String =
      "Trivia/{numberOfQuestions}/{category}/{difficulty}/{type}"
    const val argument0: String = "numberOfQuestions"
    const val argument1: String = "category"
    const val argument2: String = "difficulty"
    const val argument3: String = "type"
  }
}