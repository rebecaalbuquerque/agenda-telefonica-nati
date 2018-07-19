package br.com.albuquerque.agendatelefonicanati.modules.auth.network

import br.com.albuquerque.agendatelefonicanati.modules.auth.model.User
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth")
    fun criarConta(usuario: User): Observable<String>

    @POST("auth/sign_in")
    fun fazerLogin(@Body usuario: User): Observable<Response<ApiResponse>>


}