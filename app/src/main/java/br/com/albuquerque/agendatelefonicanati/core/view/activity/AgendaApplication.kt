package br.com.albuquerque.agendatelefonicanati.core.view.activity

import android.app.Application
import io.realm.Realm

class AgendaApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

    }

}