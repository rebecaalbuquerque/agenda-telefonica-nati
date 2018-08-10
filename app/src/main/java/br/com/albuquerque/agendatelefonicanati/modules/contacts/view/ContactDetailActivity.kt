package br.com.albuquerque.agendatelefonicanati.modules.contacts.view

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.TextUtils
import br.com.albuquerque.agendatelefonicanati.R
import br.com.albuquerque.agendatelefonicanati.R.id.ivFotoContato
import br.com.albuquerque.agendatelefonicanati.core.extensions.*
import br.com.albuquerque.agendatelefonicanati.core.view.activity.BaseActivity
import br.com.albuquerque.agendatelefonicanati.modules.contacts.business.ContactBusiness
import br.com.albuquerque.agendatelefonicanati.modules.contacts.model.Contact
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_contact_detail.*
import java.text.SimpleDateFormat


class ContactDetailActivity : BaseActivity() {

    private var idContato: Int? = null
    private lateinit var contato: Contact
    private lateinit var activityName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)

        configurarContatoSelecionado()
        setupActionBar(true, activityName)
        setupDatePicker(txtNascimento, contato.birth)
        configurarBtnEditarContato()
        configurarBtnExcluirContato()

    }

    private fun configurarContatoSelecionado(){
        val idContatoExtra = intent.getIntExtra("idContato", -1)

        if(idContatoExtra != -1){
            contato = ContactBusiness.buscarContato(idContatoExtra)
            idContato = idContatoExtra

            contato.name?.let {
                activityName = it
            }

            txtNomeContato.setText(contato.name)
            txtEmailContato.setText(contato.email)
            Picasso.with(this).load(contato.picture).transform(CropCircleTransformation()).into(ivFotoContato)
            txtTelefoneContato.setText(contato.phone)

            contato.birth?.let{
                txtNascimento.text = it.toDate().formatoBrasileiro()
            }
        }
    }

    private fun configurarBtnExcluirContato() {
        btnExcluirContato.setOnClickListener {

            showAlert(R.string.alert_title_delete_contact, R.string.alert_msg_delete_contact, {
                excluirContato()
            }, null)

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

        contatoEditado.picture = contato.picture

        if(contato.compareTo(contatoEditado) == 1){
            ContactBusiness.editarContato(contatoEditado,{
                setupActionBar(null, contatoEditado.name)
                Snackbar.make(btnEditarContato, it, Snackbar.LENGTH_SHORT).success()

            }, {
                Snackbar.make(btnEditarContato, it, Snackbar.LENGTH_SHORT).error()
            })
        } else {
            Snackbar.make(btnEditarContato, getString(R.string.msg_no_changes), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun excluirContato(){
        idContato?.let {contato ->
            ContactBusiness.excluirContato(contato) {
                finish()
                Snackbar.make(window.decorView, it, Snackbar.LENGTH_SHORT).success()
            }
        }
    }

}
