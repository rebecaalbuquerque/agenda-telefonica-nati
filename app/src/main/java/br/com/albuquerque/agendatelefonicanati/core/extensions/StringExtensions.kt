package br.com.albuquerque.agendatelefonicanati.core.extensions

import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(): Date = SimpleDateFormat("dd/MM/yyyy").parse(this)