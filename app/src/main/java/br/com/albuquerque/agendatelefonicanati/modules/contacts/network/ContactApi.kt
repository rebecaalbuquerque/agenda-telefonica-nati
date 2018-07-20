package br.com.albuquerque.agendatelefonicanati.modules.contacts.network

import br.com.albuquerque.agendatelefonicanati.modules.contacts.model.Contact
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface ContactApi {

    @GET("contacts")
    fun listarContatos(@HeaderMap headers: Map<String, String>): Observable<List<Contact>>

    @POST("contacts")
    fun criarContato(@HeaderMap headers: Map<String, String>, @Body contato: Contact): Observable<Response<Contact>>

}