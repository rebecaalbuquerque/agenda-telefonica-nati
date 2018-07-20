package br.com.albuquerque.agendatelefonicanati.modules.contacts.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import br.com.albuquerque.agendatelefonicanati.R
import br.com.albuquerque.agendatelefonicanati.modules.contacts.business.ContactBusiness
import br.com.albuquerque.agendatelefonicanati.modules.contacts.model.Contact
import kotlinx.android.synthetic.main.activity_new_contact.*

class NewContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)

        configurarBtnAdicionarContato()
    }

    private fun configurarBtnAdicionarContato() {

        btnCriarContato.setOnClickListener {
            val novoContato = Contact(name=contactName.text.toString(), email = contactEmail.text.toString(), phone = contactTelefone.text.toString(), picture = contactFoto.text.toString())

            ContactBusiness.criarNovoContato(novoContato, {

                Snackbar.make(btnCriarContato, "Contato criado com sucesso!", Snackbar.LENGTH_SHORT).show()

            }, {
                Snackbar.make(btnCriarContato, "Erro ao criar contato!", Snackbar.LENGTH_SHORT).show()
            })
        }



    }


}
