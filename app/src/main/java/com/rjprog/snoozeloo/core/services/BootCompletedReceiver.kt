package com.rjprog.snoozeloo.core.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.rjprog.snoozeloo.core.domain.AlarmRepositoryInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BootCompletedReceiver: BroadcastReceiver(), KoinComponent {

    private val repository: AlarmRepositoryInterface by inject()

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            goAsync().apply {
                CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
                    try {
                        val alarmList = repository.getAllAlarms()
                            .first()
                            .filter { it.enabled }
                        println("Working")
                        println(alarmList)
                        alarmList.forEach{ alarm ->
                            println(alarm.title)
                        }
                    } catch (e: Exception){
                        println(e)
                    } finally {
                        println("Done")
                        finish()
                        cancel()
                    }
                }
            }
        }
    }
}