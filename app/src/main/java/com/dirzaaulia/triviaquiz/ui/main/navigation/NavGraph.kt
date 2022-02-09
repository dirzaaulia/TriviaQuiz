package com.dirzaaulia.triviaquiz.ui.main.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.dirzaaulia.triviaquiz.ui.home.Home
import com.dirzaaulia.triviaquiz.ui.home.HomeViewModel
import com.dirzaaulia.triviaquiz.ui.splash.Splash
import com.dirzaaulia.triviaquiz.ui.trivia.Trivia
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@Composable
fun NavGraph(navController: NavHostController) {
  val viewModel: HomeViewModel = hiltViewModel()
  val actions = remember(navController) { NavActions(navController) }

  ProvideWindowInsets {
    AnimatedNavHost(
      navController = navController,
      startDestination = NavScreen.Splash.route
    ) {
      composable(NavScreen.Splash.route) {
        Splash(
          viewModel =  viewModel,
          navigateToHome = actions.navigateToHome
        )
      }

      composable(NavScreen.Home.route) {
        Home(
          viewModel =  viewModel,
          navigateToTrivia = actions.navigateToTrivia
        )
      }
      composable(
        route = NavScreen.Trivia.routeWithArgument,
        arguments = listOf(
          navArgument(NavScreen.Trivia.argument0) {
            type = NavType.IntType
          },
          navArgument(NavScreen.Trivia.argument1) {
            type = NavType.IntType
          },
          navArgument(NavScreen.Trivia.argument2) {
            type = NavType.StringType
          },
          navArgument(NavScreen.Trivia.argument3) {
            type = NavType.StringType
          }
        ),
        enterTransition = {
          expandIn(animationSpec = tween(700))
        },
        exitTransition = {
          shrinkOut(animationSpec = tween(700))
        }
      ) { backStackEntry ->
        backStackEntry.arguments.let { bundle ->
          bundle?.let { argument ->
            Trivia(
              numberOfQuestions = argument.getInt(NavScreen.Trivia.argument0),
              category = argument.getInt(NavScreen.Trivia.argument1),
              difficulty = argument.getString(NavScreen.Trivia.argument2),
              type = argument.getString(NavScreen.Trivia.argument3)
            )
          }
        }
      }
    }
  }
}