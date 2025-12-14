package com.example.todolist.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todolist.data.local.TodoJsonDataSource
import com.example.todolist.data.repository.TodoRepositoryImpl
import com.example.todolist.domain.usecase.GetTodosUseCase
import com.example.todolist.domain.usecase.ToggleTodoUseCase
import com.example.todolist.presentation.ui.screen.TodoDetailScreen
import com.example.todolist.presentation.ui.screen.TodoListScreen
import com.example.todolist.presentation.ui.viewmodel.TodoViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val viewModel = TodoViewModel(
        getTodosUseCase = GetTodosUseCase(
            repository = TodoRepositoryImpl(
                dataSource = TodoJsonDataSource(context)
            )
        ),
        toggleTodoUseCase = ToggleTodoUseCase(
            repository = TodoRepositoryImpl(
                dataSource = TodoJsonDataSource(context)
            )
        )
    )

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        composable("list") {
            TodoListScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(
            "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            val todo = viewModel.todos.value.find { it.id == id }

            if (todo != null) {
                TodoDetailScreen(
                    todo = todo,
                    navController = navController
                )
            }
        }
    }
}