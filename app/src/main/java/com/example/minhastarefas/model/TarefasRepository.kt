package com.example.minhastarefas.model

import android.content.SharedPreferences
import com.example.minhastarefas.Constants
import com.google.gson.GsonBuilder

class TarefasRepository(val sharedPrefs: SharedPreferences) {

    val gson = GsonBuilder().create()
    suspend fun salvarTarefas(listaTarefas: List<Tarefa>) : List<Tarefa> {
        val jsonString = gson.toJson(listaTarefas)

        sharedPrefs.edit().putString(Constants.TAREFAS, jsonString).apply()
        return recuperarTarefas()
    }

    suspend fun recuperarTarefas() : List<Tarefa> {
        val jsonString = sharedPrefs.getString(Constants.TAREFAS, "[]")

        val lista = gson.fromJson(
            jsonString,
            Array<Tarefa>::class.java
        )

        return lista.toList()
    }
}