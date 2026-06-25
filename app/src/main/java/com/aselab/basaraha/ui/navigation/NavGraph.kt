package com.aselab.basaraha.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aselab.basaraha.AselaBasarahaApp
import com.aselab.basaraha.ui.screens.AboutScreen
import com.aselab.basaraha.ui.screens.GameScreen
import com.aselab.basaraha.ui.screens.HomeScreen
import com.aselab.basaraha.ui.screens.QuestionsScreen
import com.aselab.basaraha.ui.screens.SettingsScreen
import com.aselab.basaraha.ui.screens.SetupScreen
import com.aselab.basaraha.ui.viewmodel.GameViewModel
import com.aselab.basaraha.ui.viewmodel.GameViewModelFactory
import com.aselab.basaraha.ui.viewmodel.QuestionsViewModel
import com.aselab.basaraha.ui.viewmodel.QuestionsViewModelFactory
import com.aselab.basaraha.ui.viewmodel.SettingsViewModel
import com.aselab.basaraha.ui.viewmodel.SettingsViewModelFactory
import com.aselab.basaraha.util.FeedbackManager

@Composable
fun AppNavGraph(
    navController: NavHostController,
    app: AselaBasarahaApp,
    feedbackManager: FeedbackManager
) {
    val questionsViewModel: QuestionsViewModel = viewModel(
        factory = QuestionsViewModelFactory(app.questionRepository)
    )
    val settingsViewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModelFactory(app.settingsRepository, app.questionRepository)
    )
    val gameViewModel: GameViewModel = viewModel(
        factory = GameViewModelFactory(app.questionRepository)
    )

    val soundEnabled by settingsViewModel.soundEnabled.collectAsState()
    val vibrationEnabled by settingsViewModel.vibrationEnabled.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onStartGame = { navController.navigate(Screen.Setup.route) },
                onAddQuestions = { navController.navigate(Screen.Questions.route) },
                onSettings = { navController.navigate(Screen.Settings.route) },
                onAbout = { navController.navigate(Screen.About.route) }
            )
        }

        composable(Screen.Setup.route) {
            SetupScreen(
                onBack = { navController.popBackStack() },
                onStart = { names ->
                    gameViewModel.setupPlayers(names)
                    gameViewModel.startGame()
                    navController.navigate(Screen.Game.route) {
                        popUpTo(Screen.Home.route)
                    }
                }
            )
        }

        composable(Screen.Game.route) {
            GameScreen(
                viewModel = gameViewModel,
                soundEnabled = soundEnabled,
                vibrationEnabled = vibrationEnabled,
                onFeedback = {
                    if (soundEnabled) feedbackManager.playNextSound()
                    if (vibrationEnabled) feedbackManager.vibrate()
                },
                onBack = {
                    gameViewModel.resetGame()
                    navController.popBackStack()
                },
                onFinish = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Questions.route) {
            QuestionsScreen(
                viewModel = questionsViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                viewModel = settingsViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.About.route) {
            AboutScreen(onBack = { navController.popBackStack() })
        }
    }
}
