package br.com.albuquerque.agendatelefonicanati.core.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatoBrasileiro() : String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(this)