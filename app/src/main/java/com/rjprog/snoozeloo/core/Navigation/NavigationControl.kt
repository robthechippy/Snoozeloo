package com.rjprog.snoozeloo.core.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.rjprog.snoozeloo.edit_alarm.presentation.EditAlarmScreen
import com.rjprog.snoozeloo.your_alarms.presentation.MainScreen
import kotlin.reflect.typeOf

@Composable
fun NavigationControl() {
    val navController: NavHostController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MainScreenDest
    ) {
        composable<MainScreenDest> {
            MainScreen(
                onFabClick = { alarmId ->
                    navController.navigate(EditScreenDest(alarmId = alarmId))
                },
                onEditAlarm = { alarmId ->
                    navController.navigate(EditScreenDest(alarmId = alarmId))
                }
            )
        }
        composable<EditScreenDest>(
            typeMap = mapOf(
                typeOf<Long>() to NavType.LongType
            )
        ) {
            EditAlarmScreen(
                alarmSaved = {
                    navController.popBackStack()
                },
            )
        }
    }
}