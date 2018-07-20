package br.com.albuquerque.agendatelefonicanati.modules.auth.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import br.com.albuquerque.agendatelefonicanati.R
import br.com.albuquerque.agendatelefonicanati.modules.auth.business.AuthBusiness
import kotlinx.android.synthetic.main.activity_register_account.*

class RegisterAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_account)

        configurarBotaoCadastrar()

    }

    private fun configurarBtnTeste() {
        AlertDialog.Builder(this)
                .setMessage("Usuário criado com sucesso!")
                .setTitle("Mensagem")
                .create()
                .show()

    }

    private fun configurarBotaoCadastrar() {

        btnCadastrarConta.setOnClickListener {

            AuthBusiness.registrar(txtEmail.text.toString(), txtSenha.text.toString(), txtConfirmarSenha.text.toString(),{

                Snackbar.make(btnCadastrarConta, it, Snackbar.LENGTH_SHORT).show()

            }, {
                Snackbar.make(btnCadastrarConta, it, Snackbar.LENGTH_SHORT).show()
            })

        }

    }
}
