package com.rishi.swipe.assignment.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * [SwipeApplication] is the main application class for the Swipe Android app.
 * It is annotated with [@HiltAndroidApp], indicating that Hilt should generate the necessary
 * Dagger components for dependency injection.
 *
 * @see Application
 */
@HiltAndroidApp
class SwipeApplication : Application() {
    // Any custom configuration or initialization can be done here.
}