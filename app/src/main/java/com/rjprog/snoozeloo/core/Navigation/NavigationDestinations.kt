package com.rjprog.snoozeloo.core.Navigation

import kotlinx.serialization.Serializable

//sealed class NavigationDestinations(val route: String) {
//    object MainScreenDest : NavigationDestinations(route = "mainScreenDest")
//    object EditScreenDest : NavigationDestinations(route = "editScreenDest/{alarmId}") {
//        const val alarmIdArg = "alarmId"
//        fun createRoute(alarmId: String) = "editScreenDest/$alarmId"
//    }
//}

@Serializable
data object MainScreenDest

@Serializable
data class EditScreenDest(val alarmId: Long)