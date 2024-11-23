package com.example.socialplustodo.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.socialplustodo.interfaces.ApiInterface
import com.example.socialplustodo.interfaces.TodoDaoInterface
import com.example.socialplustodo.model.Todo

class TodoRepository(private val apiInterface: ApiInterface,
                     private val todoDaoInterface: TodoDaoInterface,
                     private val errorMessage: MutableLiveData<String?>) {
    suspend fun getTodosFromNetwork(): List<Todo> {
        var todoEntities : List<Todo> = emptyList()
        try {
            val todoResponses = apiInterface.getTodos()
            todoEntities = todoResponses.map { Todo(it.id, it.userId, it.title, it.completed) }
            todoDaoInterface.insertTodos(todoEntities)

        } catch (e: Exception) {
           Log.e("TodoRepository", "e: $e")
            errorMessage.postValue("Failed to load todos")
        }
        return todoEntities
    }

    fun getAllTodosFromDatabase(): LiveData<List<Todo>> {
        return todoDaoInterface.getAllTodos()
    }
}

