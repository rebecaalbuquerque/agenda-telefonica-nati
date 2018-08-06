package br.com.albuquerque.agendatelefonicanati.modules.contacts.view

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import br.com.albuquerque.agendatelefonicanati.R
import br.com.albuquerque.agendatelefonicanati.modules.contacts.business.ContactBusiness
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_contact_detail.*
import android.content.DialogInterface
import android.graphics.Color
import android.support.design.widget.Snackbar
import android.view.MenuItem
import android.text.TextUtils
import android.widget.TextView
import br.com.albuquerque.agendatelefonicanati.core.extensions.*
import br.com.albuquerque.agendatelefonicanati.modules.contacts.model.Contact
import java.text.SimpleDateFormat
import java.util.*


class ContactDetailActivity : AppCompatActivity() {

    private var idContato: Int? = null
    private var contato: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        configurarContatoSelecionado()
        configurarDatePickerNascimento()
        configurarBtnEditarContato()
        configurarBtnExcluirContato()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configurarContatoSelecionado(){
        val idContatoExtra = intent.getIntExtra("idContato", -1)
        contato = ContactBusiness.buscarContato(idContatoExtra)
        idContato = idContatoExtra

        contato?.let {
            supportActionBar!!.title = it.name

            txtNomeContato.setText(it.name)
            txtEmailContato.setText(it.email)
            Picasso.with(this).load(it.picture).into(ivFotoContato)
            txtTelefoneContato.setText(it.phone)

            it.birth?.let{
                txtNascimento.text = SimpleDateFormat(getString(R.string.format_date_br)).format(Date( it * 1000))
            }
        }




    }

    private fun configurarBtnExcluirContato() {
        btnExcluirContato.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            builder
                    .setMessage(getString(R.string.popup_msg_delete_contact))
                    .setTitle(getString(R.string.popup_title_delete_contact))
                    .setPositiveButton(getString(R.string.popup_yes), DialogInterface.OnClickListener { dialog, id ->
                        idContato?.let {contato ->
                            ContactBusiness.excluirContato(contato) {
                                finish()
                                Snackbar.make(window.decorView, it, Snackbar.LENGTH_SHORT).success().show()
                            }
                        }
                    })
                    .setNegativeButton(getString(R.string.popup_no), DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()
                    })
                    .create()
                    .show()

        }

    }

    private fun configurarBtnEditarContato() {
        btnEditarContato.setOnClickListener {

            if(btnEditarContato.text == getString(R.string.btnEditarContato)){
                btnEditarContato.text = getString(R.string.btnSalvarContato)
                txtNascimento.setTextColor(Color.BLACK)
                txtNascimento.alpha = 1.0F
                habilitarCampos()

            } else {
                btnEditarContato.text = getString(R.string.btnEditarContato)
                attemptEditar()
                desabilitarCampos()
                txtNascimento.setTextColor(Color.parseColor("#808080"))
                txtNascimento.alpha = 0.5F
            }

        }

    }

    private fun configurarDatePickerNascimento() {

        val txtDataNascimento: TextView = findViewById(R.id.txtNascimento)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = Date(contato!!.birth!!).time * 1000

        val myFormat = getString(R.string.format_date_br)
        val sdf = SimpleDateFormat(myFormat)

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            txtDataNascimento.text = sdf.format(calendar.time)
        }

        txtDataNascimento.setOnClickListener {
            DatePickerDialog(this, dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun habilitarCampos(){
        txtNomeContato.isEnabled = true
        txtEmailContato.isEnabled = true
        txtTelefoneContato.isEnabled = true
        txtNascimento.isEnabled = true
    }

    private fun desabilitarCampos(){
        txtNomeContato.isEnabled = false
        txtEmailContato.isEnabled = false
        txtTelefoneContato.isEnabled = false
        txtNascimento.isEnabled = false
    }

    private fun attemptEditar(){
        // Reseta os errors
        txtNomeContato.error = null
        txtEmailContato.error = null
        txtTelefoneContato.error = null
        txtNascimento.error = null

        var enviarRequest = true

        // Valida campos
        if(TextUtils.isEmpty(txtNomeContato.text.toString().trim())){
            txtNomeContato.error = getString(R.string.error_input)
            enviarRequest = false
        }

        if(TextUtils.isEmpty(txtEmailContato.text.toString().trim())){
            txtEmailContato.error = getString(R.string.error_input)
            enviarRequest = false
        }

        if(TextUtils.isEmpty(txtTelefoneContato.text.toString().trim())){
            txtTelefoneContato.error = getString(R.string.error_input)
            enviarRequest = false
        }

        if(txtNascimento.text == "Data de Nascimento"){
            Snackbar.make(btnEditarContato, "Selecione a data de nascimento.", Snackbar.LENGTH_SHORT).show()
            enviarRequest = false
        }

        if(enviarRequest){
            editarContato()
        }
    }

    private fun editarContato(){
        val contatoEditado = Contact(name=txtNomeContato.text.toString(), email = txtEmailContato.text.toString(), phone = txtTelefoneContato.text.toString())
        contatoEditado.id = idContato
        contatoEditado.birth = SimpleDateFormat(getString(R.string.format_date_br)).parse(txtNascimento.text.toString()).time/1000

        contato?.let {
            contatoEditado.picture = it.picture
        }

        if(contato!!.compareTo(contatoEditado) == 1){
            ContactBusiness.editarContato(contatoEditado,{
                Snackbar.make(btnEditarContato, it, Snackbar.LENGTH_SHORT).success().show()

            }, {
                Snackbar.make(btnEditarContato, it, Snackbar.LENGTH_SHORT).error().show()
            })
        } else {
            Snackbar.make(btnEditarContato, getString(R.string.msg_no_changes), Snackbar.LENGTH_SHORT).show()
        }

    }

}
