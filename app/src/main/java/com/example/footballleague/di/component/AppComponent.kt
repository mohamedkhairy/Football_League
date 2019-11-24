package com.example.footballleague.di.component

import android.app.Application
import com.example.footballleague.BaseApplication
import com.example.footballleague.di.ActivityBuilderModule
import com.example.footballleague.di.AppModule
import com.example.footballleague.di.ViewModelFactoryModule
import com.example.footballleague.di.modules.EndPointModule
import com.example.footballleague.di.modules.HomeFragmentModule
import com.example.footballleague.di.modules.HomeViewModelModule
import com.example.footballleague.di.modules.TeamInfoViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
                     ActivityBuilderModule::class,
                     AppModule::class,
                     ViewModelFactoryModule::class,
                     EndPointModule::class,
                     HomeViewModelModule::class,
                     TeamInfoViewModelModule::class
])

interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}