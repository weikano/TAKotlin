package com.kingsunedu.takotlin

import com.jakewharton.threetenabp.AndroidThreeTen
import com.kingsunedu.takotlin.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }else {
            Timber.plant(CrashlyticsTree())
        }
        AndroidThreeTen.init(this)
    }
}