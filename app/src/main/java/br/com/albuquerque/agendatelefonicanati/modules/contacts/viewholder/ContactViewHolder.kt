package br.com.albuquerque.agendatelefonicanati.modules.contacts.viewholder

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import br.com.albuquerque.agendatelefonicanati.modules.contacts.model.Contact
import br.com.albuquerque.agendatelefonicanati.modules.contacts.view.ContactDetailActivity
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.viewholder_contact.view.*

class ContactViewHolder(var view: View): RecyclerView.ViewHolder(view) {

    fun bind(contato: Contact){

        with(view){
            vhNome.text = contato.name
            vhEmail.text = contato.email
            Picasso.with(this.context).load(contato.picture).transform(CropCircleTransformation()).into(vhFoto)
            vhTelefone.text = contato.phone
        }

        view.setOnClickListener {
            intentContactDetail(it, contato)
        }

    }

    private fun intentContactDetail(it: View, contato: Contact) {
        val intentDetalhes = Intent(it.context, ContactDetailActivity::class.java)
        val extraBundle = Bundle()

        contato.id?.let { contatoId ->
            extraBundle.putInt("idContato", contatoId)
        }

        intentDetalhes.putExtras(extraBundle)
        it.context.startActivity(intentDetalhes)
    }

}