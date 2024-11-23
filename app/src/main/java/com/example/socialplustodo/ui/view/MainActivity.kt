package com.example.socialplustodo.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.socialplustodo.ui.screen.TodoListScreen
import com.example.socialplustodo.viewmodel.TodoViewModel

class MainActivity : ComponentActivity() {
    private lateinit var todoViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
            todoViewModel.fetchTodos()
            TodoListScreen(viewModel = todoViewModel)
        }
    }
}
