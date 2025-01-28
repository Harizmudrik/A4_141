package com.example.uaspam_141.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uaspam_141.ui.view.jenis.DestinasiDetailJenis
import com.example.uaspam_141.ui.view.jenis.DestinasiHomeJenis
import com.example.uaspam_141.ui.view.jenis.DestinasiJenisEntry
import com.example.uaspam_141.ui.view.jenis.DestinasiUpdateJenis
import com.example.uaspam_141.ui.view.jenis.DetailJenisView
import com.example.uaspam_141.ui.view.jenis.EntryJenisScreen
import com.example.uaspam_141.ui.view.jenis.HomeJenisView
import com.example.uaspam_141.ui.view.jenis.UpdateJenisView
import com.example.uaspam_141.ui.view.manajer.DestinasiDetailManajer
import com.example.uaspam_141.ui.view.manajer.DestinasiHomeManajer
import com.example.uaspam_141.ui.view.manajer.DestinasiManajerEntry
import com.example.uaspam_141.ui.view.manajer.DestinasiUpdateManajer
import com.example.uaspam_141.ui.view.manajer.DetailManajerView
import com.example.uaspam_141.ui.view.manajer.EntryManajerScreen
import com.example.uaspam_141.ui.view.manajer.HomeManajerView
import com.example.uaspam_141.ui.view.manajer.UpdateManajerView
import com.example.uaspam_141.ui.view.pemilik.DestinasiDetailPemilik
import com.example.uaspam_141.ui.view.pemilik.DestinasiHomePemilik
import com.example.uaspam_141.ui.view.pemilik.DestinasiPemilikEntry
import com.example.uaspam_141.ui.view.pemilik.DestinasiUpdatePemilik
import com.example.uaspam_141.ui.view.pemilik.DetailPemilikView
import com.example.uaspam_141.ui.view.pemilik.EntryPemilikScreen
import com.example.uaspam_141.ui.view.pemilik.HomePemilikView
import com.example.uaspam_141.ui.view.pemilik.UpdatePemilikView
import com.example.uaspam_141.ui.view.properti.DestinasiDetailProperti
import com.example.uaspam_141.ui.view.properti.DestinasiHomeProperti
import com.example.uaspam_141.ui.view.properti.DestinasiPropertiEntry
import com.example.uaspam_141.ui.view.properti.DestinasiUpdateProperti
import com.example.uaspam_141.ui.view.properti.DetailPropertiView
import com.example.uaspam_141.ui.view.properti.EntryPropertiScreen
import com.example.uaspam_141.ui.view.properti.HomePropertiView
import com.example.uaspam_141.ui.view.properti.UpdatePropertiView
import com.example.uaspam_141.ui.view.splash.DestinasiSplash
import com.example.uaspam_141.ui.view.splash.SplashView

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiSplash.route,
        modifier = Modifier
    ) {

        // Home Splash
        composable(DestinasiSplash.route) {
            SplashView(
                onPropertiClick = {
                    navController.navigate(DestinasiHomeProperti.route)
                },
                onPemilikClick = {
                    navController.navigate(DestinasiHomePemilik.route)
                },
                onManajerClick = {
                    navController.navigate(DestinasiHomeManajer.route)
                },
                onJenisClick = {
                    navController.navigate(DestinasiHomeJenis.route)
                }
            )
        }

        //Pemilik
        composable(DestinasiHomePemilik.route) {
            HomePemilikView(
                navigateToItemEntry = { navController.navigate(DestinasiPemilikEntry.route) },
                onDetailClick = { id_pemilik ->
                    navController.navigate("${DestinasiDetailPemilik.route}/$id_pemilik")
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        // Insert Pemilik Screen
        composable(DestinasiPemilikEntry.route) {
            EntryPemilikScreen(navigateBack = {
                navController.navigate(DestinasiHomePemilik.route) {
                    popUpTo(DestinasiHomePemilik.route) {
                        inclusive = true
                    }
                }
            })
        }

        // Detail Pemilik Screen
        composable(
            route = "${DestinasiDetailPemilik.route}/{id_pemilik}",
            arguments = listOf(navArgument("id_pemilik") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_pemilik = backStackEntry.arguments?.getString("id_pemilik") ?: ""
            DetailPemilikView(
                id_pemilik = id_pemilik,
                navigateBack = {
                    navController.navigate(DestinasiHomePemilik.route) {
                        popUpTo(DestinasiHomePemilik.route) {
                            inclusive = true
                        }
                    }
                },
                onClick = {
                    navController.navigate("${DestinasiUpdatePemilik.route}/$id_pemilik")
                }
            )
        }

        // Update Pengembalian Screen
        composable(
            route = "${DestinasiUpdatePemilik.route}/{id_pemilik}",
            arguments = listOf(navArgument("id_pemilik") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_pemilik = backStackEntry.arguments?.getString("id_pemilik") ?: ""
            UpdatePemilikView(
                id_pemilik = id_pemilik,
                navigateBack = {
                    navController.navigate(DestinasiHomePemilik.route) {
                        popUpTo(DestinasiHomePemilik.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Manajer

        composable(DestinasiHomeManajer.route) {
            HomeManajerView(
                navigateToItemEntry = { navController.navigate(DestinasiManajerEntry.route) },
                onDetailClick = { id_manajer ->
                    navController.navigate("${DestinasiDetailManajer.route}/$id_manajer")
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        // Insert Manajer Screen
        composable(DestinasiManajerEntry.route) {
            EntryManajerScreen(navigateBack = {
                navController.navigate(DestinasiHomeManajer.route) {
                    popUpTo(DestinasiHomeManajer.route) {
                        inclusive = true
                    }
                }
            })
        }

        // Detail Manajer Screen
        composable(
            route = "${DestinasiDetailManajer.route}/{id_manajer}",
            arguments = listOf(navArgument("id_manajer") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_manajer = backStackEntry.arguments?.getString("id_manajer") ?: ""
            DetailManajerView(
                id_manajer = id_manajer,
                navigateBack = {
                    navController.navigate(DestinasiHomeManajer.route) {
                        popUpTo(DestinasiHomeManajer.route) {
                            inclusive = true
                        }
                    }
                },
                onClick = {
                    navController.navigate("${DestinasiUpdateManajer.route}/$id_manajer")
                }
            )
        }

        // Update Pengembalian Screen
        composable(
            route = "${DestinasiUpdateManajer.route}/{id_manajer}",
            arguments = listOf(navArgument("id_manajer") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_manajer = backStackEntry.arguments?.getString("id_manajer") ?: ""
            UpdateManajerView(
                id_manajer = id_manajer,
                navigateBack = {
                    navController.navigate(DestinasiHomeManajer.route) {
                        popUpTo(DestinasiHomeManajer.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Jenis
        composable(DestinasiHomeJenis.route) {
            HomeJenisView(
                navigateToItemEntry = { navController.navigate(DestinasiJenisEntry.route) },
                onDetailClick = { id_jenis ->
                    navController.navigate("${DestinasiDetailJenis.route}/$id_jenis")
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        // Insert Jenis Screen
        composable(DestinasiJenisEntry.route) {
            EntryJenisScreen(navigateBack = {
                navController.navigate(DestinasiHomeJenis.route) {
                    popUpTo(DestinasiHomeJenis.route) {
                        inclusive = true
                    }
                }
            })
        }

        // Detail Jenis Screen
        composable(
            route = "${DestinasiDetailJenis.route}/{id_jenis}",
            arguments = listOf(navArgument("id_jenis") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_jenis = backStackEntry.arguments?.getString("id_jenis") ?: ""
            DetailJenisView(
                id_jenis = id_jenis,
                navigateBack = {
                    navController.navigate(DestinasiHomeJenis.route) {
                        popUpTo(DestinasiHomeJenis.route) {
                            inclusive = true
                        }
                    }
                },
                onClick = {
                    navController.navigate("${DestinasiUpdateJenis.route}/$id_jenis")
                }
            )
        }

        // Update Pengembalian Screen
        composable(
            route = "${DestinasiUpdateJenis.route}/{id_jenis}",
            arguments = listOf(navArgument("id_jenis") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_jenis = backStackEntry.arguments?.getString("id_jenis") ?: ""
            UpdateJenisView(
                id_jenis = id_jenis,
                navigateBack = {
                    navController.navigate(DestinasiHomeJenis.route) {
                        popUpTo(DestinasiHomeJenis.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Properti

        composable(DestinasiHomeProperti.route) {
            HomePropertiView(
                navigateToItemEntry = { navController.navigate(DestinasiPropertiEntry.route) },
                onDetailClick = { id_properti ->
                    navController.navigate("${DestinasiDetailProperti.route}/$id_properti")
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        // Insert Properti Screen
        composable(DestinasiPropertiEntry.route) {
            EntryPropertiScreen(navigateBack = {
                navController.navigate(DestinasiHomeProperti.route) {
                    popUpTo(DestinasiHomeProperti.route) {
                        inclusive = true
                    }
                }
            })
        }

        // Detail Properti Screen
        composable(
            route = "${DestinasiDetailProperti.route}/{id_properti}",
            arguments = listOf(navArgument("id_properti") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_properti = backStackEntry.arguments?.getString("id_properti") ?: ""
            DetailPropertiView(
                id_properti = id_properti,
                navigateBack = {
                    navController.navigate(DestinasiHomeProperti.route) {
                        popUpTo(DestinasiHomeProperti.route) {
                            inclusive = true
                        }
                    }
                },
                onClick = {
                    navController.navigate("${DestinasiUpdateProperti.route}/$id_properti")
                }
            )
        }

// Update Properti Screen
        composable(
            route = "${DestinasiUpdateProperti.route}/{id_properti}",
            arguments = listOf(navArgument("id_properti") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_properti = backStackEntry.arguments?.getString("id_properti") ?: ""
            UpdatePropertiView(
                id_properti = id_properti,
                navigateBack = {
                    navController.navigate(DestinasiHomeProperti.route) {
                        popUpTo(DestinasiHomeProperti.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

