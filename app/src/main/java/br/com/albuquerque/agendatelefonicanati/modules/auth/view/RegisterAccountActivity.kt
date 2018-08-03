package br.com.albuquerque.agendatelefonicanati.modules.auth.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.MenuItem
import br.com.albuquerque.agendatelefonicanati.R
import br.com.albuquerque.agendatelefonicanati.modules.auth.business.AuthBusiness
import kotlinx.android.synthetic.main.activity_register_account.*

class RegisterAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_account)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Registrar conta"

        configurarBotaoCadastrar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
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
        if(TextUtils.isEmpty(txtEmail.text.toString().trim())){
            txtEmail.error = getString(R.string.error_input)
            enviarRequest = false
        }

        if(TextUtils.isEmpty(txtSenha.text.toString().trim())){
            txtSenha.error = getString(R.string.error_input)
            enviarRequest = false
        }

        if(txtSenha.text.length < 8){
            txtSenha.error = getString(R.string.error_input_length)
            enviarRequest = false
        }

        if(TextUtils.isEmpty(txtConfirmarSenha.text.toString().trim())){
            txtConfirmarSenha.error = getString(R.string.error_input)
            enviarRequest = false
        }

        if(enviarRequest){
            registerUser()
        }

    }

    private fun registerUser(){
        AuthBusiness.registrar(txtEmail.text.toString(), txtSenha.text.toString(), txtConfirmarSenha.text.toString(),{
            Snackbar.make(btnCadastrarConta, it, Snackbar.LENGTH_SHORT).show()

        }, {
            Snackbar.make(btnCadastrarConta, it, Snackbar.LENGTH_SHORT).show()
        })
    }
}
