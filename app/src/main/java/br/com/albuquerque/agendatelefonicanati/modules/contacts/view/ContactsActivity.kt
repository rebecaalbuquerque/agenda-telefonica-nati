package br.com.albuquerque.agendatelefonicanati.modules.contacts.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import br.com.albuquerque.agendatelefonicanati.R
import br.com.albuquerque.agendatelefonicanati.modules.auth.business.AuthBusiness
import br.com.albuquerque.agendatelefonicanati.modules.contacts.adapter.ContactsAdapter
import br.com.albuquerque.agendatelefonicanati.modules.contacts.business.ContactBusiness
import kotlinx.android.synthetic.main.activity_contacts.*
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.view.MenuItem
import android.support.v4.widget.SwipeRefreshLayout
import br.com.albuquerque.agendatelefonicanati.modules.auth.view.AuthActivity


class ContactsActivity : AppCompatActivity() {

    private val adapter = ContactsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        supportActionBar!!.title = "Meus contatos"

        configurarContatos()
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.getItemId() == R.id.btnLogout) {
            val builder = AlertDialog.Builder(this)

            builder
                    .setMessage("Você deseja realmente sair?")
                    .setTitle("Logout")
                    .setPositiveButton("Sim", DialogInterface.OnClickListener { dialog, id ->
                        AuthBusiness.fazerLogout({
                            val intentLogout = Intent(this, AuthActivity::class.java)
                            startActivity(intentLogout)
                            finish()
                        },{
                            Log.d("TAG", it)
                        })
                        dialog.dismiss()
                    })
                    .setNegativeButton("Não", DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()
                    })
                    .create()
                    .show()
        }

        return super.onOptionsItemSelected(item)
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

    private fun configurarContatos() {
        rvListaContatos.adapter = adapter

        swipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            atualizarContatos()
        })

    }

    private fun atualizarContatos() {
        adapter.refresh()
        onItemsLoadComplete()
    }

    private fun onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        rvListaContatos.adapter.notifyDataSetChanged()

        // Stop refresh animation
        swipeRefreshLayout.setRefreshing(false)
    }


}
