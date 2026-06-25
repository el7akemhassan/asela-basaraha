package com.aselab.basaraha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.compose.rememberNavController
import com.aselab.basaraha.ui.navigation.AppNavGraph
import com.aselab.basaraha.ui.theme.AselaBasarahaTheme
import com.aselab.basaraha.util.FeedbackManager

class MainActivity : ComponentActivity() {

    private lateinit var feedbackManager: FeedbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as AselaBasarahaApp
        feedbackManager = FeedbackManager(this)

        setContent {
            val darkMode by app.settingsRepository.darkMode.collectAsState(initial = false)

            androidx.compose.runtime.CompositionLocalProvider(
                LocalLayoutDirection provides LayoutDirection.Rtl
            ) {
                AselaBasarahaTheme(darkTheme = darkMode) {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        val navController = rememberNavController()
                        AppNavGraph(
                            navController = navController,
                            app = app,
                            feedbackManager = feedbackManager
                        )
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::feedbackManager.isInitialized) {
            feedbackManager.release()
        }
    }
}
