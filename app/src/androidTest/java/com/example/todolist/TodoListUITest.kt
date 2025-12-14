package com.example.todolist

import androidx.navigation.compose.rememberNavController
import com.example.todolist.domain.model.TodoItem
import com.example.todolist.presentation.ui.screen.TodoListScreen
import com.example.todolist.presentation.ui.viewmodel.ITodoViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TodoListUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    class FakeTodoViewModel(
        private val todosList: List<TodoItem>
    ) : ITodoViewModel {
        private val _todos = MutableStateFlow(todosList)
        override val todos: StateFlow<List<TodoItem>> = _todos

        override fun toggleTodo(id: Int) {
            _todos.value = _todos.value.map {
                if (it.id == id) it.copy(isCompleted = !it.isCompleted) else it
            }
        }
    }

    @Test
    fun all_three_tasks_are_displayed() {
        val testTodos = listOf(
            TodoItem(1, "Купить продукты", "2 литра, обезжиренное", false),
            TodoItem(2, "Позвонить маме", "Спросить про выходные", true),
            TodoItem(3, "Сделать ДЗ по Android", "Compose", false)
        )

        val fakeViewModel = FakeTodoViewModel(testTodos)

        composeTestRule.setContent {
            TodoListScreen(
                viewModel = fakeViewModel,
                navController = rememberNavController()
            )
        }

        composeTestRule.onNodeWithText("Купить продукты").assertExists()
        composeTestRule.onNodeWithText("Позвонить маме").assertExists()
        composeTestRule.onNodeWithText("Сделать ДЗ по Android").assertExists()
    }
}