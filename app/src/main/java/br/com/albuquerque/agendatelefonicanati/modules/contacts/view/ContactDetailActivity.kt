package br.com.albuquerque.agendatelefonicanati.modules.contacts.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import br.com.albuquerque.agendatelefonicanati.R
import br.com.albuquerque.agendatelefonicanati.modules.contacts.business.ContactBusiness
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_contact_detail.*
import android.content.DialogInterface
import android.content.Intent
import android.util.Log


class ContactDetailActivity : AppCompatActivity() {

    private var idContato: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)

        configurarContatoSelecionado()
        configurarBtnEditarContato()
        configurarBtnExcluirContato()

    }

    private fun configurarBtnExcluirContato() {
        btnExcluirContato.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            builder
                    .setMessage("Você deseja realmente excluir esse contato?")
                    .setTitle("Excluir contato")
                    .setPositiveButton("Sim", DialogInterface.OnClickListener { dialog, id ->
                        idContato?.let {contato ->
                            ContactBusiness.excluirContato(contato, {
                                Log.d("TAG", it)
                            },{
                                Log.d("TAG", it)
                            })
                        }
                    })
                    .setNegativeButton("Não", DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()
                    })
                    .create()
                    .show()

        }

    }

    private fun configurarBtnEditarContato() {
        btnEditarContato.setOnClickListener {
            val intentEditar = Intent(it.context, NewContactActivity::class.java)
            val extraBundle = Bundle()

            idContato?.let { idContato ->
                extraBundle.putInt("idContatoEditar", idContato)
            }

            intentEditar.putExtras(extraBundle)
            this.startActivity(intentEditar)
        }
    }

    private fun configurarContatoSelecionado(){
        val idContatoExtra = intent.getIntExtra("idContato", -1)
        val contato = ContactBusiness.buscarContato(idContatoExtra)
        idContato = idContatoExtra

        lblNomeContato.text = contato.name
        lblEmailContato.text = contato.email
        Picasso.with(this).load(contato.picture).into(ivFotoContato)
        lblTelefoneContato.text = contato.phone
    }
}
