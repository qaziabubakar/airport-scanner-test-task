package com.vorctis.devgate.task2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vorctis.devgate.task2.fragments.SplashScreen
import com.vorctis.devgate.task2.fragments.MainListScreen
import com.vorctis.devgate.task2.ui.theme.ComposePracticeTheme
import com.vorctis.devgate.task2.utils.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val myViewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePracticeTheme {
                myNavigationComponent()
            }
        }
    }
}

@Composable
fun myNavigationComponent() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.Splash.route
    ) {
        composable(Routes.Splash.route) {
            val home = SplashScreen()
            home.content(navController)
        }
        composable(Routes.MainListScreen.route) {
            val next = MainListScreen()
            next.content(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposePracticeTheme {
        myNavigationComponent()
    }
}