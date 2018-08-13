package br.com.albuquerque.agendatelefonicanati.core.view.activity

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.TextView
import br.com.albuquerque.agendatelefonicanati.R
import br.com.albuquerque.agendatelefonicanati.core.extensions.error
import br.com.albuquerque.agendatelefonicanati.core.extensions.formatoBrasileiro
import br.com.albuquerque.agendatelefonicanati.core.extensions.toDate
import br.com.albuquerque.agendatelefonicanati.modules.auth.business.AuthBusiness
import br.com.albuquerque.agendatelefonicanati.modules.auth.view.AuthActivity
import java.util.*

abstract class BaseActivity: AppCompatActivity() {

    private val progessDialog by lazy{

    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }

            R.id.btnLogout -> {
                showAlert(R.string.alert_title_logout, R.string.alert_msg_logout, {
                    logout()
                }, null)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    protected fun showAlert(@StringRes title: Int, @StringRes msg: Int, positiveAction: (() -> Unit)?, negativeAction: (() -> Unit)?){
        AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(R.string.alert_yes){ dialog, _ ->
                    positiveAction?.invoke()
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.alert_no){ dialog, _ ->
                    negativeAction?.invoke()
                    dialog.dismiss()
                }
                .create()
                .show()
    }

    protected fun setupActionBar(displayHomeAsUpEnabled: Boolean?, activityName: String?){
        supportActionBar?.let { actionBar->
            displayHomeAsUpEnabled?.let{
                actionBar.setDisplayHomeAsUpEnabled(it)
            }

            activityName?.let {
                actionBar.title = it
            }
        }
    }

    protected fun setupDatePicker(textView: TextView, defaultValue: Long?){
        val calendar = Calendar.getInstance()

        defaultValue?.let {
            calendar.timeInMillis = it.toDate().time
        }

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->

            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            textView.text = calendar.time.formatoBrasileiro()
        }

        textView.setOnClickListener {
            DatePickerDialog(this, dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    protected fun isConnectedToInternet(context: Context): Boolean {
        val networkTypes = intArrayOf(ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI)

        try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            for (networkType in networkTypes) {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.type == networkType)
                    return true
            }
        } catch (e: Exception) {
            return false
        }

        return false
    }

    private fun logout(){
        AuthBusiness.fazerLogout({
            val intentLogout = Intent(this, AuthActivity::class.java)
            startActivity(intentLogout)
            finish()
        },{
            Snackbar.make(window.decorView, it, Snackbar.LENGTH_SHORT).error()
        })
    }
}