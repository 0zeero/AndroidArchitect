package com.example.androidarchitecture

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for the Android Architecture project.
 * 
 * The @HiltAndroidApp annotation triggers Hilt's code generation,
 * including a base class for the application that uses dependency injection.
 */
@HiltAndroidApp
class MainApplication : Application()
