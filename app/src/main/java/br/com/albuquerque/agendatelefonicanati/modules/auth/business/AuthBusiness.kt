package br.com.albuquerque.agendatelefonicanati.modules.auth.business

import br.com.albuquerque.agendatelefonicanati.modules.auth.database.AuthDatabase
import br.com.albuquerque.agendatelefonicanati.modules.auth.model.User
import br.com.albuquerque.agendatelefonicanati.modules.auth.network.AuthNetwork

object AuthBusiness {

    fun registrar(email: String, senha: String, confirmarSenha: String, onSuccess: (msg: String)-> Unit, onError: (msg: String)-> Unit){

        AuthNetwork.criarConta( User(email, senha, confirmarSenha), {
            onSuccess("Usuario criado com sucesso")
        }, {
            onError("Erro ao criar usuario")
        } )

    }

    fun fazerLogin(email: String, senha: String, onSuccess: ()-> Unit, onError: (msg: String)-> Unit){

        AuthNetwork.fazerLogin(email, senha, { user ->
            AuthDatabase.salvarUsuario(user)
            onSuccess()
        }, {
            onError("Erro ao fazer login")
        })

    }

    fun fazerLogout(onSuccess: (msg: String)-> Unit, onError: (msg: String)-> Unit){

        val headers = getHeaders()

        AuthNetwork.fazerLogout(headers,{
            AuthDatabase.clearDatabase()
            onSuccess("Logout")
        },{
            onError("Erro ao efetuar logout")
        })
    }

    fun getUsuarioLogado(): User? {
        return AuthDatabase.getUsuario()
    }

    fun getHeaders(): Map<String, String>{

        val headers = HashMap<String, String>()

        getUsuarioLogado()?.let { user ->

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