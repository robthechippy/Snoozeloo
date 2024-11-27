package com.rjprog.snoozeloo

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.rjprog.snoozeloo.core.di.dbModule
import com.rjprog.snoozeloo.core.di.repoModule
import com.rjprog.snoozeloo.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(listOf(dbModule, viewModelModule, repoModule))
        }

        val channelId = "alarm_id"
        val channelName = "Snoozeloo alarm"
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            setBypassDnd(true)
        }
        notificationManager.createNotificationChannel(channel)
    }

}