package com.example.guestapp.core

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.guest.ui.screens.GuestListScreen
import com.example.guest.ui.screens.Login
import com.example.guest.ui.screens.TelaCadastro

@Composable
fun Routes() {
    val navController = rememberNavController()
    NavHost(
        startDestination = "Login", //no trabalho mudar para a tela de Login
        navController = navController
    ) {
        composable("GuestListScreen") {
            GuestListScreen(navController)
        }
        composable("Login") {
            Login(navController)
        }
        composable("TelaCadastro") {
            TelaCadastro(navController)
        }
    }
}