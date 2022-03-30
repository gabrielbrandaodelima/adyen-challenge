package com.adyen.android.assignment.application

import android.app.Application
import com.adyen.android.assignment.core.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        // start Koin!
        startKoin {
            // Android context
            androidContext(this@Application)
            // modules
            modules(appComponent)
        }
    }
}