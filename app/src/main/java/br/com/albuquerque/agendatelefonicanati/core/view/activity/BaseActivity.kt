package br.com.albuquerque.agendatelefonicanati.core.view.activity

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import br.com.albuquerque.agendatelefonicanati.R

abstract class BaseActivity: AppCompatActivity() {

    private val progessDialog by lazy{

    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    protected fun showPopup(context: Context, title: String, msg: String, positiveAction: () -> Unit, negativeAction: (() -> Unit)?){
        AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(getString(R.string.popup_yes), DialogInterface.OnClickListener { dialog, id ->
                    positiveAction()
                })
                .setNegativeButton(getString(R.string.popup_no), DialogInterface.OnClickListener { dialog, id ->
                    negativeAction?.let {
                        it()
                    }
                    dialog.dismiss()
                })
                .create()
                .show()
    }



}