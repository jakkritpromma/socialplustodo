package com.example.socialplustodo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.socialplustodo.retrofit.RetrofitInstance
import com.example.socialplustodo.database.TodoDatabase
import com.example.socialplustodo.model.Todo
import com.example.socialplustodo.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TodoRepository
    val allTodos: LiveData<List<Todo>>
    val errorMessage: MutableLiveData<String?> = MutableLiveData(null)

    init {
        val todoDao = TodoDatabase.getDatabase(application).todoDao()
        val apiService = RetrofitInstance.apiInterface
        repository = TodoRepository(apiService, todoDao, errorMessage)
        allTodos = repository.getAllTodosFromDatabase()
    }

    fun getTodos() {
        viewModelScope.launch {
            repository.getTodosFromNetwork()
        }
    }
}


