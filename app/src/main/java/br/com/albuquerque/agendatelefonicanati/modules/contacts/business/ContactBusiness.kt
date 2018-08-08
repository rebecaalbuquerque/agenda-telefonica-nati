package br.com.albuquerque.agendatelefonicanati.modules.contacts.business

import br.com.albuquerque.agendatelefonicanati.modules.auth.business.AuthBusiness
import br.com.albuquerque.agendatelefonicanati.modules.contacts.database.ContactDatabase
import br.com.albuquerque.agendatelefonicanati.modules.contacts.model.Contact
import br.com.albuquerque.agendatelefonicanati.modules.contacts.network.ContactNetwork

object ContactBusiness {

    fun atualizarContatos(onSuccess: () -> Unit) {

        val headers = AuthBusiness.getHeaders()

        ContactNetwork.requestContatos(headers) { contatos ->

            ContactDatabase.salvarContatos(contatos)
            onSuccess()
        }

    }

    fun buscarContatos(): List<Contact>{
        return ContactDatabase.buscarContatos()
    }

    fun buscarContato(contatoId: Int): Contact{
        return ContactDatabase.buscarContato(contatoId)
    }

    fun criarNovoContato(contato: Contact, onSuccess: () -> Unit, onError: () -> Unit){

        val headers = AuthBusiness.getHeaders()

        ContactNetwork.requestNovoContato(headers, contato, { contatoResponse ->
            ContactDatabase.criarNovoContato(contatoResponse)
            onSuccess()
        }, {
            onError()
        })

    }

    fun excluirContato(idContato: Int, onSuccess: (msg: String) -> Unit){
        val headers = AuthBusiness.getHeaders()

        ContactNetwork.requestExcluirContato(headers, idContato,{
            ContactDatabase.excluirContato(idContato)
            onSuccess("Contato excluido com sucesso.")
        })
    }

    fun editarContato(contatoAtualizado: Contact, onSuccess: (msg: String) -> Unit, onError: (msg: String) -> Unit){
        val headers = AuthBusiness.getHeaders()

        ContactNetwork.requestEditarContato(headers, contatoAtualizado,{
            ContactDatabase.editarContato(it)
        }, {
            onError("Erro ao editar contato.")
        })

    }

}