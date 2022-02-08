package com.example.deliveryapp

import android.app.Application
import android.content.Context

class myApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this

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