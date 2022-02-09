package com.dirzaaulia.triviaquiz.ui.main.navigation

import androidx.navigation.NavHostController

class NavActions(navController: NavHostController) {

  val navigateToHome: () -> Unit = {
    NavScreen.Home.apply {
      navController.navigate(this.route)
    }
  }

  val navigateToTrivia: (Int, Int, String, String) -> Unit =
    { numberOfQuestions: Int, category: Int, difficulty: String, type: String ->
      NavScreen.Trivia.apply {
        var route = routeWithArgument.replace("{$argument0}", numberOfQuestions.toString())
        route = route.replace("{$argument1}", category.toString())
        route = route.replace("{$argument2}", difficulty)
        route = route.replace("{$argument3}", type)

        navController.navigate(route)
      }
  }
}