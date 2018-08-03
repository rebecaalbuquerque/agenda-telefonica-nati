package br.com.albuquerque.agendatelefonicanati.modules.contacts.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Contact(): RealmObject(), Comparable<Contact> {

    @PrimaryKey var id: Int? = null
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

    override fun compareTo(other: Contact): Int = when{
        id != other.id || name != other.name || email != other.email || phone != other.phone || picture != other.picture || birth != other.birth -> 1
        else -> 0
    }

}