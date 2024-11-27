package com.rjprog.snoozeloo.core.di

import androidx.room.Room
import com.rjprog.snoozeloo.alarm_screen.presentation.AlarmScreenViewmodel
import com.rjprog.snoozeloo.core.data.local.AlarmRepositoryLocal
import com.rjprog.snoozeloo.core.data.local.AppDatabase
import com.rjprog.snoozeloo.core.domain.AlarmRepositoryInterface
import com.rjprog.snoozeloo.core.domain.AlarmSchedulerInterface
import com.rjprog.snoozeloo.core.services.AlarmSchedulerImpl
import com.rjprog.snoozeloo.edit_alarm.presentation.EditScreenViewmodel
import com.rjprog.snoozeloo.your_alarms.presentation.MainScreenViewmodel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val dbModule: Module = module {
    single {
        Room.databaseBuilder(
            get(),
            klass = AppDatabase::class.java,
            name = "snoozeloo.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().alarmDao() }

}

val viewModelModule = module {
    viewModel { MainScreenViewmodel(get(), get()) }
    viewModel { EditScreenViewmodel(get(), get(), get()) }
    viewModel { AlarmScreenViewmodel(get(), get(), get(), androidContext()) }
}

val repoModule = module {
    single<AlarmRepositoryInterface> { AlarmRepositoryLocal(get()) }
    single<AlarmSchedulerInterface> { AlarmSchedulerImpl(androidContext()) }

}