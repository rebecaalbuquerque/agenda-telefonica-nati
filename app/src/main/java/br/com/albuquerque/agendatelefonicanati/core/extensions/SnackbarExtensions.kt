package br.com.albuquerque.agendatelefonicanati.core.extensions

import android.graphics.Color
import android.support.design.widget.Snackbar

fun Snackbar.success(): Snackbar{
    this.view.setBackgroundColor(Color.parseColor("#009933"))
    return this
}

fun Snackbar.error(): Snackbar{
    this.view.setBackgroundColor(Color.parseColor("#e60000"))
    return this
}

fun Snackbar.noChanges(): Snackbar{
    this.view.setBackgroundColor(Color.parseColor("#ffcc00"))
    return this
}