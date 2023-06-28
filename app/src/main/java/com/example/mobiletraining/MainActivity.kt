package com.example.mobiletraining

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mobiletraining.api.TokenProvider
import com.example.mobiletraining.login.Login
import com.example.mobiletraining.ui.theme.MobileTrainingTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var tokenProvider: TokenProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileTrainingTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "Login",
                    ) {
                        composable(route = "Login") {
                            Login(
                                tokenProvider,
                                loginHandler = {
                                    navController.navigate("Home")
                                },
                            )
                        }
                        composable(
                            route = "ProductDetails/{id}",
                            arguments = listOf(navArgument("id") {
                                type = NavType.StringType
                            })
                        ) { navBackStackEntry ->
                            val id = navBackStackEntry.arguments?.getString("id")
                            if (id != null) {
                                ProductDetails(id)
                            }
                        }
                        composable(route = "Home") {
                            HomeScreen(goToProductDetails = {id -> navController.navigate("ProductDetails/$id")})
                        }
                    }
                }
            }
        }
    }
}