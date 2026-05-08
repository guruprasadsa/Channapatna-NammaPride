package com.example.channapatna_namma_pride.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.channapatna_namma_pride.ui.screens.*
import com.example.channapatna_namma_pride.ui.theme.*
import com.example.channapatna_namma_pride.viewmodel.VerificationViewModel

/**
 * Route definitions. Bottom-bar items have icons; detail-only screens do not.
 */
sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    object Splash : Screen("splash", "Splash")
    object Home : Screen("home", "Home", Icons.Filled.Home)
    object Verify : Screen("verify", "Verify", Icons.Filled.Search)
    object Map : Screen("map", "Maker", Icons.Filled.LocationOn)
    object Catalog : Screen("catalog", "Catalog", Icons.AutoMirrored.Filled.List)
    object Settings : Screen("settings", "More", Icons.Filled.Settings)
    object ArtisanProfile : Screen("artisan/{artisanId}", "Artisan") {
        fun createRoute(artisanId: String) = "artisan/$artisanId"
    }
    object HowItsMade : Screen("how_its_made", "How It's Made")
}

private val bottomBarScreens = listOf(
    Screen.Home,
    Screen.Verify,
    Screen.Map,
    Screen.Catalog,
    Screen.Settings
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            // Only show bottom bar on main tab screens
            val showBottomBar = bottomBarScreens.any { it.route == currentRoute }

            if (showBottomBar) {
                NavigationBar(
                    containerColor = Color.White,
                    tonalElevation = 8.dp
                ) {
                    bottomBarScreens.forEach { screen ->
                        val selected = navBackStackEntry?.destination?.hierarchy
                            ?.any { it.route == screen.route } == true

                        NavigationBarItem(
                            icon = {
                                Icon(
                                    screen.icon!!,
                                    contentDescription = screen.title
                                )
                            },
                            label = {
                                Text(
                                    screen.title,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            selected = selected,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = LacquerRed,
                                selectedTextColor = LacquerRed,
                                unselectedIconColor = TextSecondary,
                                unselectedTextColor = TextSecondary,
                                indicatorColor = LacquerRed.copy(alpha = 0.1f)
                            ),
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // ─── Splash ───
            composable(Screen.Splash.route) {
                SplashScreen(onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                })
            }

            // ─── Home (no ViewModel dependency) ───
            composable(Screen.Home.route) {
                HomeScreen(
                    onNavigateToVerify = {
                        navController.navigate(Screen.Verify.route) {
                            launchSingleTop = true
                        }
                    },
                    onNavigateToMap = {
                        navController.navigate(Screen.Map.route) {
                            launchSingleTop = true
                        }
                    },
                    onNavigateToHowItsMade = {
                        navController.navigate(Screen.HowItsMade.route)
                    },
                    onNavigateToCatalog = {
                        navController.navigate(Screen.Catalog.route) {
                            launchSingleTop = true
                        }
                    }
                )
            }

            // ─── Verify tab (ViewModel scoped to this screen) ───
            composable(Screen.Verify.route) {
                val verifyViewModel: VerificationViewModel = viewModel()
                ResultScreen(
                    viewModel = verifyViewModel,
                    onNavigateBack = {
                        verifyViewModel.reset()
                        navController.popBackStack()
                    },
                    onNavigateToArtisan = { artisanId ->
                        navController.navigate(Screen.ArtisanProfile.createRoute(artisanId))
                    }
                )
            }

            // ─── Artisan Profile ───
            composable(Screen.ArtisanProfile.route) { backStackEntry ->
                val artisanId = backStackEntry.arguments?.getString("artisanId") ?: "default"
                val artisanViewModel: com.example.channapatna_namma_pride.viewmodel.ArtisanViewModel = viewModel()
                ArtisanProfileScreen(
                    artisanId = artisanId,
                    viewModel = artisanViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // ─── How It's Made ───
            composable(Screen.HowItsMade.route) {
                HowItsMadeScreen(onNavigateBack = {
                    navController.popBackStack()
                })
            }

            // ─── Catalog (ViewModel created internally) ───
            composable(Screen.Catalog.route) {
                ToyCatalogScreen()
            }

            // ─── Meet the Maker (ViewModel created internally) ───
            composable(Screen.Map.route) {
                MeetTheMakerScreen()
            }

            // ─── Settings ───
            composable(Screen.Settings.route) {
                SettingsScreen()
            }
        }
    }
}

