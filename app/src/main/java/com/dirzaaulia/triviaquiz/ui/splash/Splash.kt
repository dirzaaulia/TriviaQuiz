package com.dirzaaulia.triviaquiz.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import com.dirzaaulia.triviaquiz.R
import com.dirzaaulia.triviaquiz.ui.home.HomeViewModel

@Composable
fun Splash(
  viewModel: HomeViewModel,
  navigateToHome: () -> Unit
) {
  val loading = viewModel.loading.value
  val scale = remember { Animatable(0f) }

  //Animation Effect
  LaunchedEffect(key1 = loading) {
    scale.animateTo(
      targetValue = 0.7f,
      animationSpec = spring(
        dampingRatio = Spring.DampingRatioHighBouncy,
        stiffness = Spring.StiffnessMedium
      )
    )
    if (!loading) {
      navigateToHome()
    }
  }

  Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier
      .background(MaterialTheme.colors.primary)
      .fillMaxSize()
  ) {
    Image(
      painter = painterResource(id = R.drawable.trivia_transparent),
      contentDescription = null,
      modifier = Modifier.scale(scale.value)
    )
  }
}