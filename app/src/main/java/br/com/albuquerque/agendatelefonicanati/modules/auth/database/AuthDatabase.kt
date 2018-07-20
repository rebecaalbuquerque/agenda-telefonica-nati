package br.com.albuquerque.agendatelefonicanati.modules.auth.database

import br.com.albuquerque.agendatelefonicanati.modules.auth.model.User
import io.realm.Realm

object AuthDatabase {

    fun salvarUsuarioLogado(usuario: User, onSuccess: () -> Unit){

        Realm.getDefaultInstance().use { realm ->

            realm.beginTransaction()
            realm.copyToRealm(usuario)
            realm.commitTransaction()
            onSuccess()

        }

    }

    fun buscarUsuarioLogado(): User? {
        return Realm.getDefaultInstance().where(User::class.java).findFirst()
    }

    fun clearDataBase(){

        Realm.getDefaultInstance().use { realm ->

            realm.beginTransaction()
            realm.deleteAll()
            realm.commitTransaction()

        }

    }

}