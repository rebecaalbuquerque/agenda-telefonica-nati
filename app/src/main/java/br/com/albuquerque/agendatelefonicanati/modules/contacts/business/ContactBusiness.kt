package br.com.albuquerque.agendatelefonicanati.modules.contacts.business

import android.util.Log
import br.com.albuquerque.agendatelefonicanati.modules.auth.business.AuthBusiness
import br.com.albuquerque.agendatelefonicanati.modules.contacts.model.Contact
import br.com.albuquerque.agendatelefonicanati.modules.contacts.network.ContactNetwork

object ContactBusiness {

    fun listarContatos(onSuccess: () -> Unit){
        val headers = AuthBusiness.getHeaders()

        return ContactNetwork.listarContatos(headers) {

            Log.d("TAG", "Quantidade de contatos: ${it.size}")

            onSuccess()
        }
    }

}