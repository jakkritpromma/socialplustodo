package com.example.socialplustodo.interfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.socialplustodo.model.Todo

@Dao
interface TodoDaoInterface {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodos(todos: List<Todo>)

    @Query("SELECT * FROM todos")
    fun getAllTodos(): LiveData<List<Todo>>
}
