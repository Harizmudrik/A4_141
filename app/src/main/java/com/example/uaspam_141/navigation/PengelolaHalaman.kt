package com.example.uaspam_141.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = Modifier
    ) {
        // Home buat si Properti Screen
        composable("home") {
            HomePropertiView(
                navigateToPropertiEntry = { navController.navigate("entry") },
                onDetailClick = { idProperti ->
                    navController.navigate("detail/$idProperti")
                },
                navigateToJenis = { navController.navigate("jenis") },
                navigateToPemilik = { navController.navigate("pemilik") },
                navigateToManajer = { navController.navigate("manajer") }
            )
        }


        composable("entry") {
            EntryPropertiView(navigateBack = {
                navController.navigate("home") {
                    popUpTo("home") {
                        inclusive = true
                    }
                }
            })
        }

        composable(
            route = "detail/{idProperti}",
            arguments = listOf(navArgument("idProperti") { type = NavType.StringType })
        ) { backStackEntry ->
            val idProperti = backStackEntry.arguments?.getString("idProperti") ?: ""
            DetailPropertiView(
                idProperti = idProperti,
                navigateBack = {
                    navController.navigate("home") {
                        popUpTo("home") {
                            inclusive = true
                        }
                    }
                },
                onEditClick = {
                    navController.navigate("update/$idProperti")
                }
            )
        }


        composable(
            route = "update/{idProperti}",
            arguments = listOf(navArgument("idProperti") { type = NavType.StringType })
        ) { backStackEntry ->
            val idProperti = backStackEntry.arguments?.getString("idProperti") ?: ""
            UpdatePropertiView(
                idProperti = idProperti,
                navigateBack = {
                    navController.navigate("home") {
                        popUpTo("home") {
                            inclusive = true
                        }
                    }
                }
            )
        }


        composable("jenis") {
            JenisView(navigateBack = {
                navController.navigate("home") {
                    popUpTo("home") {
                        inclusive = true
                    }
                }
            })
        }


        composable("pemilik") {
            PemilikView(navigateBack = {
                navController.navigate("home") {
                    popUpTo("home") {
                        inclusive = true
                    }
                }
            })
        }


        composable("manajer") {
            ManajerView(navigateBack = {
                navController.navigate("home") {
                    popUpTo("home") {
                        inclusive = true
                    }
                }
            })
        }
    }
}

