package br.com.albuquerque.agendatelefonicanati.modules.contacts.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import br.com.albuquerque.agendatelefonicanati.R
import br.com.albuquerque.agendatelefonicanati.modules.auth.business.AuthBusiness
import br.com.albuquerque.agendatelefonicanati.modules.auth.model.User
import br.com.albuquerque.agendatelefonicanati.modules.contacts.adapter.ContactsAdapter
import br.com.albuquerque.agendatelefonicanati.modules.contacts.business.ContactBusiness
import br.com.albuquerque.agendatelefonicanati.modules.contacts.model.Contact
import kotlinx.android.synthetic.main.activity_contacts.*
import kotlinx.android.synthetic.main.activity_new_contact.*
import android.R.menu
import android.content.DialogInterface
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.view.MenuInflater
import android.view.MenuItem
import android.support.v4.widget.SwipeRefreshLayout
import br.com.albuquerque.agendatelefonicanati.modules.auth.view.MainActivity


class ContactsActivity : AppCompatActivity() {

    private val adapter = ContactsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        configurarContatos()
        requestContatos()
        configurarBotaoNovoContato()
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
                            val intentLogout = Intent(this, MainActivity::class.java)
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

        ContactBusiness.atualizarContatos {

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
            refreshItems()
        })

    }

    fun refreshItems() {
        // Load items
        adapter.refresh()

        // Load complete
        onItemsLoadComplete()
    }

    fun onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        rvListaContatos.adapter.notifyDataSetChanged()

        // Stop refresh animation
        swipeRefreshLayout.setRefreshing(false)
    }


}
