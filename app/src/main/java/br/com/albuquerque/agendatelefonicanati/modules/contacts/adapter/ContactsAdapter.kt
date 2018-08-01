package br.com.albuquerque.agendatelefonicanati.modules.contacts.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.albuquerque.agendatelefonicanati.R
import br.com.albuquerque.agendatelefonicanati.modules.contacts.business.ContactBusiness
import br.com.albuquerque.agendatelefonicanati.modules.contacts.model.Contact
import br.com.albuquerque.agendatelefonicanati.modules.contacts.viewholder.ContactViewHolder

class ContactsAdapter: RecyclerView.Adapter<ContactViewHolder>() {

    lateinit var contacts: List<Contact>

    init {
        refresh()
    }

    fun refresh(refreshFinished: ((hasContent: Boolean) -> Unit)? = null){

        contacts = ContactBusiness.buscarContatos().sortedWith(compareBy(Contact::name))
        notifyDataSetChanged()

        refreshFinished?.let { lambda ->
            lambda(contacts.isNotEmpty())
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }
}