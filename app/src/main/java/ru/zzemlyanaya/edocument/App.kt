package ru.zzemlyanaya.edocument

import android.app.Application
import com.kryptoprefs.preferences.KryptoBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.zzemlyanaya.edocument.data.local.Prefs
import ru.zzemlyanaya.edocument.data.local.PrefsConst
import ru.zzemlyanaya.edocument.data.remote.IServerService
import ru.zzemlyanaya.edocument.data.remote.SynchronousCallAdapterFactory


class App : Application(){


    override fun onCreate() {
        super.onCreate()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://us-central1-passport-client-rukami.cloudfunctions.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(SynchronousCallAdapterFactory.create())
            .build()

        service = retrofit.create(IServerService::class.java)

        preferences = Prefs(
            KryptoBuilder.hybrid(
                this,
                PrefsConst.PREFS_NAME
            )
        )
    }

    companion object {
        private lateinit var service: IServerService
        val api: IServerService
            get() = service

        private lateinit var preferences : Prefs
        val prefs: Prefs
            get() = preferences

        const val readerID = "6kOh7RdbywgiWNJIdJ8TKFvx9LkHNphm"
    }
}