package br.com.albuquerque.agendatelefonicanati.modules.contacts.database

import br.com.albuquerque.agendatelefonicanati.modules.contacts.model.Contact
import io.realm.Realm

object ContactDatabase {

    fun salvarContatos(contatos: List<Contact>, onSuccess: () -> Unit) {

        Realm.getDefaultInstance().use { realm ->

            realm.beginTransaction()
            realm.delete(Contact::class.java)
            realm.copyToRealm(contatos)
            realm.commitTransaction()

            onSuccess()

        }

    }

    fun buscarContatos(): List<Contact> {
        return Realm.getDefaultInstance().where(Contact::class.java).findAll()
    }

}