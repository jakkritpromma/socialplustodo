package com.example.socialplustodo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.socialplustodo.RetrofitInstance
import com.example.socialplustodo.database.TodoDatabase
import com.example.socialplustodo.model.Todo
import com.example.socialplustodo.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TodoRepository
    val allTodos: LiveData<List<Todo>>

    init {
        val todoDao = TodoDatabase.getDatabase(application).todoDao()
        val apiService = RetrofitInstance.apiInterface
        repository = TodoRepository(apiService, todoDao)
        allTodos = repository.getAllTodosFromDatabase()
    }

    fun fetchTodos() {
        viewModelScope.launch {
            repository.fetchTodosFromNetwork()
        }
    }
}


