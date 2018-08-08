package br.com.albuquerque.agendatelefonicanati.modules.auth.database

import br.com.albuquerque.agendatelefonicanati.modules.auth.model.User
import io.realm.Realm

object AuthDatabase {

    fun salvarUsuario(usuario: User){

        Realm.getDefaultInstance().use { realm ->

            realm.beginTransaction()
            realm.copyToRealm(usuario)
            realm.commitTransaction()

        }

    }

    fun getUsuario(): User? {
        return Realm.getDefaultInstance().where(User::class.java).findFirst()
    }

    fun clearDatabase(){

        Realm.getDefaultInstance().use { realm ->

            realm.beginTransaction()
            realm.deleteAll()
            realm.commitTransaction()

        }

    }


}