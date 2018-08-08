package br.com.albuquerque.agendatelefonicanati.modules.contacts.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import br.com.albuquerque.agendatelefonicanati.R
import br.com.albuquerque.agendatelefonicanati.core.view.activity.BaseActivity
import br.com.albuquerque.agendatelefonicanati.modules.contacts.adapter.ContactsAdapter
import br.com.albuquerque.agendatelefonicanati.modules.contacts.business.ContactBusiness
import kotlinx.android.synthetic.main.activity_contacts.*


class ContactsActivity : BaseActivity() {

    private val adapter = ContactsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        setupActionBar(null, getString(R.string.actionbar_contacts_title))

        configurarRecyclerViewContatos()
        requestContatos()
        configurarBotaoNovoContato()
    }

    override fun onStart() {
        atualizarContatos()
        super.onStart()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.contacts_menu, menu)
        return true
    }

    private fun configurarBotaoNovoContato() {
        btnNovoContato.setOnClickListener {
            startActivity( Intent(this, NewContactActivity::class.java) )
        }
    }

    private fun requestContatos() {

        swipeRefreshLayout.isRefreshing = true

        ContactBusiness.atualizarContatos {
            swipeRefreshLayout.isRefreshing = false
            adapter.refresh { hasContent ->

                if(hasContent) {
                    lblSemContatos.visibility = View.GONE
                    rvListaContatos.visibility = View.VISIBLE
                }else{
                    lblSemContatos.visibility = View.VISIBLE
                    rvListaContatos.visibility = View.GONE
                }

            }

        }

    }

    private fun configurarRecyclerViewContatos() {
        rvListaContatos.adapter = adapter

        swipeRefreshLayout.setOnRefreshListener{
            atualizarContatos()
        }

    }

    private fun atualizarContatos() {
        adapter.refresh()
        onItemsLoadComplete()
    }

    private fun onItemsLoadComplete() {
        rvListaContatos.adapter.notifyDataSetChanged()
        swipeRefreshLayout.isRefreshing = false
    }


}
