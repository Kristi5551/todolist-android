package com.example.todolist.presentation.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.todolist.presentation.ui.component.TodoItemView
import com.example.todolist.presentation.ui.viewmodel.ITodoViewModel

@Composable
fun TodoListScreen(
    viewModel: ITodoViewModel,
    navController: NavController
) {
    val todos by viewModel.todos.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Список задач") }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(todos) { todo ->
                TodoItemView(
                    todo = todo,
                    onClick = {
                        navController.navigate("detail/${todo.id}")
                    },
                    onCheckedChange = {
                        viewModel.toggleTodo(todo.id)
                    }
                )
            }
        }
    }
}