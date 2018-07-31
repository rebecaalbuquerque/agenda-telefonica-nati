package br.com.albuquerque.agendatelefonicanati.modules.auth.network

import br.com.albuquerque.agendatelefonicanati.modules.auth.model.User
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface AuthApi {

    @POST("auth")
    fun criarConta(@Body usuario: User): Observable<ApiResponse>

    @POST("auth/sign_in")
    fun fazerLogin(@Body usuario: User): Observable<Response<ApiResponse>>

    @DELETE("auth/sign_out")
    fun fazerLogout(@HeaderMap headers: Map<String, String>): Observable<Response<Any>>
}