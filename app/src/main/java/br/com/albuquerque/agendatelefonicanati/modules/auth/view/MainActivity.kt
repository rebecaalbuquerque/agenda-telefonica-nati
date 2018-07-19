package br.com.albuquerque.agendatelefonicanati.modules.auth.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import br.com.albuquerque.agendatelefonicanati.R
import br.com.albuquerque.agendatelefonicanati.modules.auth.business.AuthBusiness
import br.com.albuquerque.agendatelefonicanati.modules.contacts.view.ContactsActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.init(this)

        configurarBotaoLogin()

    }

    private fun configurarBotaoLogin() {

        btnAuthLogin.setOnClickListener{

            AuthBusiness.fazerLogin(txtAuthEmail.text.toString(), txtAuthSenha.text.toString(), {
                irParaContatos()
            }, {
                Snackbar.make(btnAuthLogin, it, Snackbar.LENGTH_SHORT).show()
            })

        }

    }

    private fun irParaContatos(){
        val intentDetalheActivity = Intent(this, ContactsActivity::class.java)
        startActivity(intentDetalheActivity)
    }
}
