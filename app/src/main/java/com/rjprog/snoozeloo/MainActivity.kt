package com.rjprog.snoozeloo

import android.animation.ObjectAnimator.*
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.rjprog.snoozeloo.core.Navigation.NavigationControl
import com.rjprog.snoozeloo.ui.theme.SnoozelooTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.androidx.compose.KoinAndroidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        if (LoadingManager.loading.value) {
            installSplashScreen().apply {
                setOnExitAnimationListener { screen ->
                    val zoomX = ofFloat(
                        screen.iconView,
                        View.SCALE_X,
                        0.4f,
                        0.0f
                    )
                    zoomX.interpolator = OvershootInterpolator()
                    zoomX.duration = 1000L
                    zoomX.doOnEnd { screen.remove() }

                    val zoomY = ofFloat(
                        screen.iconView,
                        View.SCALE_Y,
                        0.4f,
                        0.0f
                    )
                    zoomY.interpolator = OvershootInterpolator()
                    zoomY.duration = 1000L
                    zoomY.doOnEnd { screen.remove() }

                    zoomX.start()
                    zoomY.start()
                }
            }
            LoadingManager.clearLoading()
        }
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            SnoozelooTheme {
                KoinAndroidContext {
                    NavigationControl()
                }
            }
        }
    }
}

object LoadingManager {
    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()

    fun clearLoading() {
        _loading.value = false
    }
}