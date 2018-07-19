package br.com.albuquerque.agendatelefonicanati.modules.auth.business

import br.com.albuquerque.agendatelefonicanati.modules.auth.database.AuthDatabase
import br.com.albuquerque.agendatelefonicanati.modules.auth.model.User
import br.com.albuquerque.agendatelefonicanati.modules.auth.network.AuthNetwork

object AuthBusiness {

    fun registrar(email: String, senha: String, confirmarSenha: String, onSuccess: (msg: String)-> Unit, onError: (msg: String)-> Unit){

        AuthNetwork.criarConta( User(email, senha, confirmarSenha), {

            onSuccess("Usuario criado com sucesso!")

        }, {
            onError("Erro ao criar usuario!")
        } )

    }

    fun fazerLogin(email: String, senha: String, onSuccess: ()-> Unit, onError: (msg: String)-> Unit){

        AuthNetwork.fazerLogin(email, senha, {
            onSuccess()
        }, {

            onError("Erro ao fazer login.")

        })

    }

    fun buscarUsuarioLogado(): User? {
        return AuthDatabase.buscarUsuarioLogado()
    }

    fun getHeaders(): Map<String, String>{

        val headers = HashMap<String, String>()

        buscarUsuarioLogado()?.let { user ->

            user.uid?.let{ uid ->
                headers.put("uid", uid)
            }

            user.client?.let{ client ->
                headers.put("client", client)
            }

            user.accessToken?.let{ accessToken ->
                headers.put("access-token", accessToken)
            }
        }

        return headers

    }

}