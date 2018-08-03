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
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.MenuItem
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.TextView
import br.com.albuquerque.agendatelefonicanati.modules.contacts.model.Contact
import java.lang.Comparable
import java.text.SimpleDateFormat
import java.util.*


class ContactDetailActivity : AppCompatActivity() {

    private var idContato: Int? = null
    private var contatoSelecionado: Contact? = null

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
        val id = item.getItemId()
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configurarContatoSelecionado(){
        val idContatoExtra = intent.getIntExtra("idContato", -1)
        contatoSelecionado = ContactBusiness.buscarContato(idContatoExtra)
        idContato = idContatoExtra

        contatoSelecionado?.let {
            supportActionBar!!.title = it.name

            txtNomeContato.setText(it.name)
            txtEmailContato.setText(it.email)
            Picasso.with(this).load(it.picture).into(ivFotoContato)
            txtTelefoneContato.setText(it.phone)

            it.birth?.let{
                txtNascimento.setText(SimpleDateFormat("dd/MM/yyyy").format(Date( it * 1000)))
            }
        }




    }

    private fun configurarBtnExcluirContato() {
        btnExcluirContato.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            builder
                    .setMessage("Você deseja realmente excluir esse contato?")
                    .setTitle("Excluir contato")
                    .setPositiveButton("Sim", DialogInterface.OnClickListener { dialog, id ->
                        idContato?.let {contato ->
                            ContactBusiness.excluirContato(contato) {
                                Snackbar.make(btnExcluirContato, it, Snackbar.LENGTH_SHORT).show()
                                finish()
                            }
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

            if(btnEditarContato.text.equals("Editar")){
                btnEditarContato.text = "Salvar"
                habilitarCampos()

            } else {
                btnEditarContato.text = "Editar"
                attemptEditar()
                desabilitarCampos()
            }

        }

    }

    private fun configurarDatePickerNascimento() {

        val txtDataNascimento: TextView = findViewById(R.id.txtNascimento)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = Date(contatoSelecionado!!.birth!!).time * 1000

        val myFormat = "dd/MM/yyyy"
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

        if(TextUtils.isEmpty(txtNascimento.text.toString().trim())){
            txtNascimento.error = getString(R.string.error_input)
            enviarRequest = false
        }

        if(enviarRequest){
            editarContato()
        }
    }

    private fun editarContato(){
        val contatoEditado = Contact(name=txtNomeContato.text.toString(), email = txtEmailContato.text.toString(), phone = txtTelefoneContato.text.toString())
        contatoEditado.id = idContato
        contatoEditado.birth = SimpleDateFormat("dd/MM/yyyy").parse(txtNascimento.text.toString()).time/1000

        contatoSelecionado?.let {
            contatoEditado.picture = it.picture
        }

        Log.d("TAG", "${contatoSelecionado}")



        if(contatoSelecionado!!.compareTo(contatoEditado) == 1){
            ContactBusiness.editarContato(contatoEditado,{
                Snackbar.make(btnEditarContato, it, Snackbar.LENGTH_SHORT).show()
            }, {
                Snackbar.make(btnEditarContato, it, Snackbar.LENGTH_SHORT).show()
            })
        } else {
            Snackbar.make(btnEditarContato, "Nenhuma alteração foi feita.", Snackbar.LENGTH_SHORT).show()
        }

    }

}
