package br.com.matreis.rendimentodesoja

import android.app.Application
import br.com.matreis.rendimentodesoja.data.db.AppDatabase

class App : Application() {

    val database by lazy { AppDatabase.getInstance(this) }

}