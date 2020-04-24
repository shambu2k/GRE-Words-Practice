package com.example.grewordspractice.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.grewordspractice.BaseApplication;
import com.example.grewordspractice.SharedPrefHelper;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.Provides;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuildersModule.class,
                AppModule.class,
                ViewModelFactoryModule.class,
                SharedPrefModule.class,
        }
)
public interface AppComponent extends AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
