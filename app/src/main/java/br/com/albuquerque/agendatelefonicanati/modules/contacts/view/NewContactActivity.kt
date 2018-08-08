package br.com.albuquerque.agendatelefonicanati.modules.contacts.view

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputLayout
import android.text.TextUtils
import br.com.albuquerque.agendatelefonicanati.R
import br.com.albuquerque.agendatelefonicanati.core.extensions.error
import br.com.albuquerque.agendatelefonicanati.core.extensions.success
import br.com.albuquerque.agendatelefonicanati.core.view.activity.BaseActivity
import br.com.albuquerque.agendatelefonicanati.modules.contacts.business.ContactBusiness
import br.com.albuquerque.agendatelefonicanati.modules.contacts.model.Contact
import kotlinx.android.synthetic.main.activity_new_contact.*
import java.text.SimpleDateFormat


class NewContactActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)

        setupActionBar(true, getString(R.string.actionbar_new_contact_title))
        setupDatePicker(contactDataNasc, null)
        configurarBtnAdicionarContato()
    }

    private fun configurarBtnAdicionarContato() {

        btnCriarContato.setOnClickListener {
            attemptCriar()
        }

    }

    private fun attemptCriar(){
        // Reseta os errors
        contactFoto.error = null
        contactName.error = null
        contactEmail.error = null
        contactTelefone.error = null
        contactDataNasc.error = null

        var enviarRequest = true

        // Valida campos
        for(i: Int in 0..constraintNewContact.childCount){
            val view = constraintNewContact.getChildAt(i)

            if(view is TextInputLayout){

                if(TextUtils.isEmpty(view.editText!!.text.toString().trim())){
                    view.error = getString(R.string.error_input)
                    enviarRequest = false
                }

            }

        }

        if(contactDataNasc.text == "Data de Nascimento"){
            Snackbar.make(btnCriarContato, "Selecione a data de nascimento.", Snackbar.LENGTH_SHORT).show()
            enviarRequest = false
        }

        if(enviarRequest){
            criarContato()
        }

    }

    private fun criarContato(){
        val timestampNasc = SimpleDateFormat(getString(R.string.format_date_br)).parse(contactDataNasc.text.toString()).time/1000
        val novoContato = Contact(name=contactName.text.toString(), email = contactEmail.text.toString(), phone = contactTelefone.text.toString(), picture = contactFoto.text.toString(), birth = timestampNasc)

        ContactBusiness.criarNovoContato(novoContato, {
            finish()
            Snackbar.make(btnCriarContato, getString(R.string.success_new_contact), Snackbar.LENGTH_SHORT).success()
        }, {
            Snackbar.make(btnCriarContato, getString(R.string.error_new_contact), Snackbar.LENGTH_SHORT).error()
        })
    }
}


