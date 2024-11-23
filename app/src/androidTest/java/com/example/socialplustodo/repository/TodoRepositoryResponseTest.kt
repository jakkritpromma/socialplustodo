package com.example.socialplustodo.repository

import com.example.socialplustodo.interfaces.ApiInterface
import com.example.socialplustodo.interfaces.TodoDaoInterface
import com.example.socialplustodo.model.Todo
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TodoRepositoryResponseTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var todoRepository: TodoRepository
    private val todoDaoInterface: TodoDaoInterface = mock()

    @Before
    fun setUp() {
        try {
            println("Set up MockWebServer.")
            mockWebServer = MockWebServer()
            mockWebServer.start()
            val baseUrl = mockWebServer.url("/")
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val apiInterface = retrofit.create(ApiInterface::class.java)
            todoRepository = TodoRepository(apiInterface, todoDaoInterface)
            println("MockWebServer started at: $baseUrl")
        } catch (e: Exception) {
            println("setUp Error: ${e.message}")
        }
    }

    @After
    fun tearDown() {
        try {
            println("Shut down mockWebServer.")
            mockWebServer.shutdown()
        } catch (e: UninitializedPropertyAccessException) {
            println("mockWebServer was not initialized: ${e.message}")
        }
    }

    @Test
    fun testGetTodosFromApiSuccessfullyAndInsertsIntoDB() = runBlocking {
        val mockTodosResponse = """
            [
                {"id": 1, "userId": 1, "title": "Todo 1", "completed": false},
                {"id": 2, "userId": 1, "title": "Todo 2", "completed": true}
            ]
        """

        mockWebServer.enqueue(MockResponse().setBody(mockTodosResponse).setResponseCode(200))
        val result = todoRepository.fetchTodosFromNetwork()
        assert(result.size == 2)

        verify(todoDaoInterface).insertTodos(
            listOf(
                Todo(1, 1, "Todo 1", false),
                Todo(2, 1, "Todo 2", true)
            )
        )
    }

    @Test
    fun testGetTodosFromApiHandlesAPIFailure() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))
        val result = todoRepository.fetchTodosFromNetwork()
        assert(result.isEmpty())
    }
}

