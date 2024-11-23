package com.example.socialplustodo.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.socialplustodo.database.TodoDatabase
import com.example.socialplustodo.interfaces.ApiInterface
import com.example.socialplustodo.interfaces.TodoDaoInterface
import com.example.socialplustodo.model.Todo
import com.example.socialplustodo.retrofit.RetrofitInstance
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TodoRepositoryDatabaseTest {
    private lateinit var todoDatabase: TodoDatabase
    private lateinit var todoDaoInterface: TodoDaoInterface
    private lateinit var todoRepository: TodoRepository
    private lateinit var apiInterface: ApiInterface

    @get:Rule val instantExecutorRule = InstantTaskExecutorRule() //LiveData is allowed to work synchronously during the test.

    @Before fun setup() {
        todoDatabase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), TodoDatabase::class.java)
            .allowMainThreadQueries().build()
        apiInterface = RetrofitInstance.apiInterface
        todoDaoInterface = todoDatabase.todoDao()
        todoRepository = TodoRepository(RetrofitInstance.apiInterface, todoDaoInterface)
    }

    @After fun tearDown() {
        todoDatabase.close()
    }

    @Test fun testGetAllTodosFromDatabase() = runBlocking { // Insert some test data into the database
        val todo1 = Todo(1, 1, "Test Todo 1", false)
        val todo2 = Todo(2, 1, "Test Todo 2", true)
        todoDaoInterface.insertTodos(listOf(todo1, todo2))

        val liveData: LiveData<List<Todo>> = todoRepository.getAllTodosFromDatabase()
        val observer = Observer<List<Todo>> { todos ->
            assertEquals(2, todos.size)
        }
        liveData.observeForever(observer)
    }
}
