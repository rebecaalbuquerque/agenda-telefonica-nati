package br.com.albuquerque.agendatelefonicanati.modules.contacts.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import br.com.albuquerque.agendatelefonicanati.R
import br.com.albuquerque.agendatelefonicanati.modules.contacts.business.ContactBusiness
import br.com.albuquerque.agendatelefonicanati.modules.contacts.model.Contact
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_new_contact.*
import java.text.SimpleDateFormat
import java.util.*

class NewContactActivity : AppCompatActivity() {

    private var isToEdit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)

        configurarEditarContato()
        configurarBtnAdicionarContato()
        configurarDatePickerNascimento()
    }

    private fun configurarEditarContato() {
        val idContatoEditar = intent.getIntExtra("idContatoEditar", -1)
        val contato = ContactBusiness.buscarContato(idContatoEditar)
        var dataNascimento: String? = null

        if(idContatoEditar!= -1){
            btnCriarContato.text = "Editar"
            isToEdit = true
        }

        contato.birth?.let {
            dataNascimento = SimpleDateFormat("dd/MM/yyyy").format(Date(it * 1000))
        }

        contactName.setText(contato.name)
        contactEmail.setText(contato.email)
        contactTelefone.setText(contato.phone)
        contactFoto.setText(contato.picture)
        contactDataNasc.setText(dataNascimento)

    }

    private fun configurarDatePickerNascimento() {

        val txtDataNascimento: TextView = findViewById(R.id.contactDataNasc)
        val calendar = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(myFormat)
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

        if(!isToEdit){
            btnCriarContato.setOnClickListener {
                val timestampNasc = SimpleDateFormat("dd/MM/yyyy").parse(contactDataNasc.text.toString()).time/1000
                val novoContato = Contact(name=contactName.text.toString(), email = contactEmail.text.toString(), phone = contactTelefone.text.toString(), picture = contactFoto.text.toString(), birth = timestampNasc)

                ContactBusiness.criarNovoContato(novoContato, {
                    Snackbar.make(btnCriarContato, "Contato criado com sucesso!", Snackbar.LENGTH_SHORT).show()
                }, {
                    Snackbar.make(btnCriarContato, "Erro ao criar contato!", Snackbar.LENGTH_SHORT).show()
                })
            }
        } else {
            btnCriarContato.setOnClickListener {
                val timestampNasc = SimpleDateFormat("dd/MM/yyyy").parse(contactDataNasc.text.toString()).time/1000
                val contatoEditado = Contact(name=contactName.text.toString(), email = contactEmail.text.toString(), phone = contactTelefone.text.toString(), picture = contactFoto.text.toString(), birth = timestampNasc)
                contatoEditado.id = intent.getIntExtra("idContatoEditar", -1)

                ContactBusiness.editarContato(contatoEditado,{
                    Log.d("TAG", it)
                }, {
                    Log.d("TAG", it)
                })

            }
        }



    }


}


