package br.com.albuquerque.agendatelefonicanati.modules.contacts.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import br.com.albuquerque.agendatelefonicanati.R
import br.com.albuquerque.agendatelefonicanati.modules.contacts.business.ContactBusiness
import br.com.albuquerque.agendatelefonicanati.modules.contacts.model.Contact
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_new_contact.*
import java.text.SimpleDateFormat
import java.util.*

class NewContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.actionbar_new_contact_title)

        configurarBtnAdicionarContato()
        configurarDatePickerNascimento()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun configurarDatePickerNascimento() {

        val txtDataNascimento: TextView = findViewById(R.id.contactDataNasc)
        val calendar = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val sdf = SimpleDateFormat(getString(R.string.format_date_br))
            txtDataNascimento.text = sdf.format(calendar.time)

        }

        txtDataNascimento.setOnClickListener {
            DatePickerDialog(this, dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
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
        if(TextUtils.isEmpty(contactFoto.text.toString().trim())){
            contactFoto.error = getString(R.string.error_input)
            enviarRequest = false
        }

        if(TextUtils.isEmpty(contactName.text.toString().trim())){
            contactName.error = getString(R.string.error_input)
            enviarRequest = false
        }

        if(TextUtils.isEmpty(contactEmail.text.toString().trim())){
            contactEmail.error = getString(R.string.error_input)
            enviarRequest = false
        }

        if(TextUtils.isEmpty(contactTelefone.text.toString().trim())){
            contactTelefone.error = getString(R.string.error_input)
            enviarRequest = false
        }

        if(TextUtils.isEmpty(contactDataNasc.text.toString().trim())){
            contactDataNasc.error = getString(R.string.error_input)
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
            Snackbar.make(btnCriarContato, getString(R.string.success_new_contact), Snackbar.LENGTH_SHORT).show()
        }, {
            Snackbar.make(btnCriarContato, getString(R.string.error_new_contact), Snackbar.LENGTH_SHORT).show()
        })
    }
}


