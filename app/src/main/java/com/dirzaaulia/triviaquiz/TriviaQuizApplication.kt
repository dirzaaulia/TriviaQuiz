package com.dirzaaulia.triviaquiz

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class TriviaQuizApplication: Application(), ImageLoaderFactory {

  override fun onCreate() {
    super.onCreate()
    Timber.plant(Timber.DebugTree())
  }

  override fun newImageLoader(): ImageLoader {
    return ImageLoader.Builder(this).build()
  }

}