package com.example.socialplustodo.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.socialplustodo.model.Todo
import com.example.socialplustodo.viewmodel.TodoViewModel

@Composable fun TodoListScreen(viewModel: TodoViewModel) { //MockTodoViewModel
    val todos by viewModel.allTodos.observeAsState(emptyList())

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(todos) { todo ->
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Text(text = todo.title)
                    Text(text = "Completed: ${todo.completed}")
                }
            }
        }
    }
}

class MockTodoViewModel : ViewModel() {
    val allTodos: LiveData<List<Todo>> = MutableLiveData(listOf(Todo(id = 1, userId = 1, title = "Buy groceries", completed = false), Todo(id = 2, userId = 1, title = "Clean the house", completed = true), Todo(id = 3, userId = 1, title = "Finish homework", completed = false)))
}

@Preview(showBackground = true) @Composable fun PreviewTodoListScreen() {
    val mockViewModel = MockTodoViewModel()
    //TodoListScreen(viewModel = mockViewModel)
}