package br.com.albuquerque.agendatelefonicanati.modules.auth.view

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputLayout
import android.text.TextUtils
import br.com.albuquerque.agendatelefonicanati.R
import br.com.albuquerque.agendatelefonicanati.core.extensions.*
import br.com.albuquerque.agendatelefonicanati.core.view.activity.BaseActivity
import br.com.albuquerque.agendatelefonicanati.modules.auth.business.AuthBusiness
import br.com.albuquerque.agendatelefonicanati.modules.contacts.view.ContactsActivity
import kotlinx.android.synthetic.main.activity_auth.*
import java.util.*

class AuthActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        if(AuthBusiness.getUsuarioLogado() != null){
            val intentAuth = Intent(this, ContactsActivity::class.java)
            startActivity(intentAuth)
            finish()
        }

        configurarBotaoLogin()
        configurarBotaoCadastrar()

    }

    private fun configurarBotaoCadastrar() {
        btnAuthCadastrar.setOnClickListener {
            val intentDetalheActivity = Intent(this, RegisterAccountActivity::class.java)
            startActivity(intentDetalheActivity)
        }
    }

    private fun configurarBotaoLogin() {

        btnAuthLogin.setOnClickListener{
            attemptAuth()
        }

    }

    private fun attemptAuth(){
        // Reseta errors
        txtAuthEmail.error = null
        txtAuthSenha.error = null

        var enviarRequest = true

        // Valida campos
        for(i: Int in 0..linearFormAuth.childCount){
            val view = linearFormAuth.getChildAt(i)

            if(view is TextInputLayout){
                if(TextUtils.isEmpty(view.editText!!.text.toString().trim())){
                    view.error = getString(R.string.error_input)
                    enviarRequest = false
                }
            }
        }

        if(enviarRequest){
            auth()
        }

    }

    private fun auth(){
        if(!isConnectedToInternet(this))
            Snackbar.make(btnAuthLogin, getString(R.string.error_no_connection), Snackbar.LENGTH_SHORT).alert()

        AuthBusiness.fazerLogin(txtAuthEmail.text.toString(), txtAuthSenha.text.toString(), {
            val intentContatosActivity = Intent(this, ContactsActivity::class.java)
            startActivity(intentContatosActivity)
            finish()

        }, {
            Snackbar.make(btnAuthLogin, it, Snackbar.LENGTH_SHORT).error()
        })
    }
}
