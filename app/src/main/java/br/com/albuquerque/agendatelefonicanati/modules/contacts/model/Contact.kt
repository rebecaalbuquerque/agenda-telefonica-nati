package br.com.albuquerque.agendatelefonicanati.modules.contacts.model

import io.realm.RealmObject

open class Contact(): RealmObject() {

    var name: String? = null
    var email: String? = null
    var phone: String? = null
    var picture: String? = null
    var birth: Long? = null

    constructor(name: String?, email: String?, phone: String?): this(){
        this.name = name
        this.email = email
        this.phone = phone
    }

    constructor(name: String?, email: String?, phone: String?, picture: String?): this(){
        this.name = name
        this.email = email
        this.phone = phone
        this.picture = picture
    }

    constructor(name: String?, email: String?, phone: String?, picture: String?, birth: Long?): this(){
        this.name = name
        this.email = email
        this.phone = phone
        this.picture = picture
        this.birth = birth
    }

}