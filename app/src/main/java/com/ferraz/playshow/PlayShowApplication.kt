package com.ferraz.playshow

import android.app.Application
import com.ferraz.playshow.di.PlayShowModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.ksp.generated.module

class PlayShowApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PlayShowApplication)
            modules(PlayShowModule().module)
        }
    }
}
