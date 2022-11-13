package com.ihfazh.warnain

import android.app.Application
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraX
import androidx.camera.core.CameraXConfig
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ihfazh.warnain.categories.CategoriesViewModel
import com.ihfazh.warnain.category_detail.CategoryDetailViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

import org.koin.ksp.generated.*


class WarnainApplication: Application(), CameraXConfig.Provider{

    override fun onCreate() {
        super.onCreate()


        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@WarnainApplication)
            modules(WarnainKoinApplication().module)
        }
    }

    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }
}