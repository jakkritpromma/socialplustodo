package com.example.socialplustodo.interfaces

import com.example.socialplustodo.model.Todo
import retrofit2.http.GET

interface ApiInterface {
    @GET("todos")
    suspend fun getTodos(): List<Todo>
}
