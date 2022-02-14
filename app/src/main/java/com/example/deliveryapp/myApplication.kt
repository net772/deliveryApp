package com.example.deliveryapp

import android.app.Application
import android.content.Context
import com.example.deliveryapp.di.appModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class myApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this

        startKoin {
            modules(appModule)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        appContext = null
    }

    companion object {
        var appContext: Context? = null
            private set // 내부에서만 set할 수 있도록 설정

    }
}