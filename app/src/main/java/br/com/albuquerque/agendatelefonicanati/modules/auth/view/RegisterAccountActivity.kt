package br.com.albuquerque.agendatelefonicanati.modules.auth.view

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputLayout
import android.text.TextUtils
import br.com.albuquerque.agendatelefonicanati.R
import br.com.albuquerque.agendatelefonicanati.core.extensions.error
import br.com.albuquerque.agendatelefonicanati.core.extensions.success
import br.com.albuquerque.agendatelefonicanati.core.view.activity.BaseActivity
import br.com.albuquerque.agendatelefonicanati.modules.auth.business.AuthBusiness
import kotlinx.android.synthetic.main.activity_register_account.*

class RegisterAccountActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_account)

        setupActionBar(true, getString(R.string.actionbar_register_user_title))
        configurarBotaoCadastrar()
    }

    private fun configurarBotaoCadastrar() {

        btnCadastrarConta.setOnClickListener {
            attemptRegister()
        }

    }

    private fun attemptRegister(){
        // Reseta os errors
        txtEmail.error = null
        txtSenha.error = null
        txtConfirmarSenha.error = null

        var enviarRequest = true

        // Valida campos
        for(i: Int in 0..llRegister.childCount){
            val view = llRegister.getChildAt(i)

            if(view is TextInputLayout){
                if(TextUtils.isEmpty(view.editText!!.text.toString().trim())){
                    view.error = getString(R.string.error_input)
                    enviarRequest = false
                }
            }
        }

        if(enviarRequest){
            registerUser()
        }

    }

    private fun registerUser(){
        AuthBusiness.registrar(txtEmail.text.toString(), txtSenha.text.toString(), txtConfirmarSenha.text.toString(),{
            Snackbar.make(btnCadastrarConta, it, Snackbar.LENGTH_SHORT).success()

        }, {
            Snackbar.make(btnCadastrarConta, it, Snackbar.LENGTH_SHORT).error()
        })
    }
}
