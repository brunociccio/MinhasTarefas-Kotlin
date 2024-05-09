package com.example.minhastarefas.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minhastarefas.Constants
import com.example.minhastarefas.model.Tarefa
import com.example.minhastarefas.model.TarefasRepository
import kotlinx.coroutines.launch

class TarefasViewModel(val sharedPrefs: SharedPreferences) : ViewModel() {

    private val repository = TarefasRepository(sharedPrefs)
    private val _listaTarefas: MutableLiveData<List<Tarefa>> = MutableLiveData()
    val listaTarefas: LiveData<List<Tarefa>> get() = _listaTarefas

    init {
        recuperarTarefa()
    }

    fun salvarDados(descricao: String) {
        val lista = arrayListOf<Tarefa>()
        val tarefa = Tarefa(
            descricao,
            completa = false
        )
        _listaTarefas.value?.let {
            lista.addAll(it)
        }

        lista.add(tarefa)

        viewModelScope.launch {
            _listaTarefas.postValue(
                repository.salvarTarefas(lista)
            )
        }
    }

    private fun recuperarTarefa(){
        viewModelScope.launch {
            _listaTarefas.postValue(
                repository.recuperarTarefas()
            )
        }
    }

    fun completarTarefa(tarefa: Tarefa) {
        val lista = arrayListOf<Tarefa>()
        _listaTarefas.value?.let {
            lista.addAll(it)
        }
        val index = lista.indexOf(tarefa)
        lista[index].completa = !lista[index].completa

        viewModelScope.launch {
            _listaTarefas.postValue(
                repository.salvarTarefas(lista)
            )
        }
    }
}