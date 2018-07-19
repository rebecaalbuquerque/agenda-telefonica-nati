package br.com.albuquerque.agendatelefonicanati.modules.auth.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class User(): RealmObject() {

    var name: String? = null
    var nickname: String? = null
    var email: String? = null
    var password: String? = null
    var image: String? = null
    var uid: String? = null
    var accessToken: String? = null
    var client: String? = null

    @SerializedName("password_confirmation")
    var confirmacaoSenha: String? = null

    constructor(email: String?, senha: String?): this(){
        this.email = email
        this.password = senha
    }

    constructor(email: String?, senha: String?, confirmacaoSenha: String?): this(){
        this.email = email
        this.password = senha
        this.confirmacaoSenha = confirmacaoSenha
    }

}