package com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.nav

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object DetailAnimeGhibli : Screen("home/{animeGhibliId}") {
        fun createRoute(animeGhibliId: Int) = "home/$animeGhibliId"
    }
}