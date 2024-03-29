package com.sanmiaderibigbe.customfirebaseregistrationapp

import android.app.Application
import com.sanmiaderibigbe.customfirebaseregistrationapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application : Application(){
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin{
            androidLogger()
            androidContext(this@Application)
            modules(appModule)
        }
    }
}