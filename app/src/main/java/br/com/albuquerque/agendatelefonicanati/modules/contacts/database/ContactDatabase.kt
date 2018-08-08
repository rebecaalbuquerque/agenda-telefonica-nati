package br.com.albuquerque.agendatelefonicanati.modules.contacts.database

import br.com.albuquerque.agendatelefonicanati.modules.contacts.model.Contact
import io.realm.Realm

object ContactDatabase {

    fun salvarContatos(contatos: List<Contact>) {

        Realm.getDefaultInstance().use { realm ->

            realm.beginTransaction()
            realm.delete(Contact::class.java)
            realm.copyToRealm(contatos)
            realm.commitTransaction()
        }

    }

    fun buscarContatos(): List<Contact> {
        return Realm.getDefaultInstance().where(Contact::class.java).findAll()
    }

    fun buscarContato(contatoId: Int): Contact{
        return (Realm.getDefaultInstance().where(Contact::class.java).findAll().first{it.id == contatoId})
    }

    fun criarNovoContato(contato: Contact){
        Realm.getDefaultInstance().use { realm ->

            realm.beginTransaction()
            realm.copyToRealm(contato)
            realm.commitTransaction()

        }
    }

    fun editarContato(contatoAtualizado: Contact){
        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(contatoAtualizado)
            realm.commitTransaction()
        }
    }

    fun excluirContato(id: Int){
        Realm.getDefaultInstance().use { realm ->

            realm.beginTransaction()
            realm.where(Contact::class.java).equalTo(Contact::id.name, id).findFirst()?.deleteFromRealm()
            realm.commitTransaction()
        }
    }

}