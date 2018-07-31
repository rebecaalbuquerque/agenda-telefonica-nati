package br.com.albuquerque.agendatelefonicanati.modules.auth.network

import br.com.albuquerque.agendatelefonicanati.modules.auth.database.AuthDatabase
import br.com.albuquerque.agendatelefonicanati.modules.auth.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object AuthNetwork {

    val authAPI: AuthApi by lazy {
        configurarRetrofit().create(AuthApi::class.java)
    }

    private fun configurarRetrofit(): Retrofit{
        return Retrofit.Builder()
                .baseUrl("https://api-agenda-unifor.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    fun criarConta(usuario: User, onSucess: () -> Unit, onError: () -> Unit){

        authAPI.criarConta(usuario)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onSucess()

                },{

                    onError()

                })

    }

    fun fazerLogin(email: String, senha: String, onSucess: (user: User) -> Unit, onError: () -> Unit){

        authAPI.fazerLogin(User(email = email, senha = senha))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

                    response.body()?.let { apiResponse ->

                        apiResponse.data?.let { user ->
                            user.uid = response?.headers()?.get("uid")
                            user.client = response?.headers()?.get("client")
                            user.accessToken = response?.headers()?.get("access-token")

                            onSucess(user)
                        }
                    }

                },{
                    onError()
                })

    }

    fun fazerLogout(headers: Map<String, String>, onSucess: () -> Unit, onError: () -> Unit){
        authAPI.fazerLogout(headers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onSucess()
                }, {
                    onError()
                })

    }

}