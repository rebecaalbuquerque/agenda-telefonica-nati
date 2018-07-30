package br.com.albuquerque.agendatelefonicanati.modules.contacts.network

import android.util.Log
import br.com.albuquerque.agendatelefonicanati.modules.contacts.model.Contact
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ContactNetwork {

    val contactApi: ContactApi by lazy {
        configurarRetrofit().create(ContactApi::class.java)
    }

    private fun configurarRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://api-agenda-unifor.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    fun requestContatos(headers: Map<String, String>, onSuccess: (contatos: List<Contact>) -> Unit){

        contactApi.listarContatos(headers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    onSuccess(it)
                },{
                    Log.d("", "")
                })

    }

    fun requestContato(headers: Map<String, String>, id: Int, onSuccess: (contato: Contact) -> Unit){
        contactApi.buscarContato(headers, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onSuccess(it)
                })
    }

    fun requestNovoContato(headers: Map<String, String>, contato: Contact, onSuccess: () -> Unit, onError: () -> Unit){
        contactApi.criarContato(headers, contato)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    onSuccess()

                }, {
                    onError()
                })
    }

}