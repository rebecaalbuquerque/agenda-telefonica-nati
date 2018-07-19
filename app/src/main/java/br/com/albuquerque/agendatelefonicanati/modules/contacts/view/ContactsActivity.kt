package br.com.albuquerque.agendatelefonicanati.modules.contacts.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.albuquerque.agendatelefonicanati.R
import br.com.albuquerque.agendatelefonicanati.modules.auth.business.AuthBusiness
import br.com.albuquerque.agendatelefonicanati.modules.contacts.business.ContactBusiness
import kotlinx.android.synthetic.main.activity_contacts.*

class ContactsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)



        configurarBtnTeste()
    }

    private fun configurarBtnTeste(){

    }
}
