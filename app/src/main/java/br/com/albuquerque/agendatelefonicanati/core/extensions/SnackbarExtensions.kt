package br.com.albuquerque.agendatelefonicanati.core.extensions

import android.graphics.Color
import android.support.design.widget.Snackbar

fun Snackbar.success(){
    this.view.setBackgroundColor(Color.parseColor("#009933"))
    return this.show()
}

fun Snackbar.error(){
    this.view.setBackgroundColor(Color.parseColor("#e60000"))
    return this.show()
}