package com.respirex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.respirex.data.Firebase
import com.respirex.screen.FormScreen
import com.respirex.screen.HistoryScreen
import com.respirex.screen.HomeScreen
import com.respirex.screen.ImageScreen
import com.respirex.screen.LoginScreen
import com.respirex.screen.PasswordResetScreen
import com.respirex.screen.ProfileScreen
import com.respirex.screen.RegistrationScreen
import com.respirex.screen.ReportScreen
import com.respirex.screen.SymptomsScreen
import com.respirex.ui.theme.RespirexTheme
import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Register

@Serializable
object Home

@Serializable
object Profile

@Serializable
object Form

@Serializable
object Image

@Serializable
object History

@Serializable
data class ReportIndex(val index: Int)

@Serializable
object Symptoms

@Serializable
object PasswordReset

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val start = if (Firebase.isCurrentUserNull()) {
            Login
        } else {
            Home
        }

        setContent {
            RespirexTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(MaterialTheme.colorScheme.surface)

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = start) {
                    composable<Login> {
                        LoginScreen(register = { navController.navigate(Register) },
                            home = {
                                navController.navigate(Home) {
                                    popUpTo(Login) {
                                        inclusive = true
                                    }
                                }
                            },
                            passwordReset = { navController.navigate(PasswordReset) }
                        )
                    }

                    composable<Register> {
                        RegistrationScreen(home = {
                            navController.navigate(Home) {
                                popUpTo(Login) {
                                    inclusive = true
                                }
                            }
                        })
                    }

                    composable<Home> {

                        HomeScreen(
                            history = { navController.navigate(History) },
                            profile = { navController.navigate(Profile) },
                            logout = {
                                navController.navigate(Login) {
                                    popUpTo(Home) {
                                        inclusive = true
                                    }
                                }
                            },
                            form = { navController.navigate(Form) },
                            symptoms = { navController.navigate(Symptoms) }
                        )
                    }

                    composable<Form> {
                        FormScreen(image = {
                            navController.navigate(Image)
                        })
                    }

                    composable<Image> {
                        ImageScreen(report = { index ->
                            navController.navigate(ReportIndex(index)){
                                popUpTo(Home) {
                                    inclusive = false
                                }
                            }
                        })
                    }

                    composable<History> {
                        HistoryScreen(report = { index ->
                            navController.navigate(ReportIndex(index))
                        })
                    }

                    composable<ReportIndex> { backStackEntry ->
                        val reportIndex : ReportIndex = backStackEntry.toRoute()
                        ReportScreen(reportIndex.index)
                    }

                    composable<Symptoms>{
                        SymptomsScreen()
                    }

                    composable<Profile>{
                        ProfileScreen()
                    }

                    composable<PasswordReset>{
                        PasswordResetScreen()
                    }

                }
            }
        }
    }

}



