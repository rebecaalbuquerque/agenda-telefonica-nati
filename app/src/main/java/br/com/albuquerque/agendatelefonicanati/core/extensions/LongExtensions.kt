package br.com.albuquerque.agendatelefonicanati.core.extensions

import java.util.*

fun Long.toDate() : Date = Date(this*1000)