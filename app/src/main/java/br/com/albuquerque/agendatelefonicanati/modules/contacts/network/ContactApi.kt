package br.com.albuquerque.agendatelefonicanati.modules.contacts.network

import br.com.albuquerque.agendatelefonicanati.modules.contacts.model.Contact
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap

interface ContactApi {

    @GET("contacts")
    fun listarContatos(@HeaderMap headers: Map<String, String>): Observable<List<Contact>>

}