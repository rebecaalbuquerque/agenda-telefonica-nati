package br.com.albuquerque.agendatelefonicanati.modules.contacts.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.albuquerque.agendatelefonicanati.R
import br.com.albuquerque.agendatelefonicanati.modules.contacts.business.ContactBusiness
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_contact_detail.*

class ContactDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)

        configurarContatoSelecionado()
        configurarBtnEditarContato()

    }

    private fun configurarBtnEditarContato() {
        btnEditarContato.setOnClickListener {

        }
    }

    private fun configurarContatoSelecionado(){
        val idContatoExtra = intent.getIntExtra("idContato", -1)
        val contato = ContactBusiness.buscarContato(idContatoExtra)

        lblNomeContato.text = contato.name
        lblEmailContato.text = contato.email
        Picasso.with(this).load(contato.picture).into(ivFotoContato)
        lblTelefoneContato.text = contato.phone
    }
}
