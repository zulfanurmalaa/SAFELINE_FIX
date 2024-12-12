package com.capstone.fall_guard

import android.app.Application
import com.capstone.fall_guard.di.repositoryModules
import com.capstone.fall_guard.di.viewModelModules
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FallGuard : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@FallGuard)
            modules(
                repositoryModules,
                viewModelModules
            )
        }
    }
}