package com.example.todolist

import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.example.todolist.domain.model.TodoItem
import com.example.todolist.presentation.ui.screen.TodoListScreen
import com.example.todolist.presentation.ui.viewmodel.ITodoViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Rule
import org.junit.Test

class TodoCheckboxTest {

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
    fun checkbox_changes_state() {
        val testTodos = listOf(
            TodoItem(1, "Купить продукты", "2 литра, обезжиренное", false)
        )

        val fakeViewModel = FakeTodoViewModel(testTodos)

        composeTestRule.setContent {
            TodoListScreen(
                viewModel = fakeViewModel,
                navController = rememberNavController()
            )
        }

        val checkbox = composeTestRule.onNodeWithTag("todoCheckbox_1")
        checkbox.assertIsOff()
        checkbox.performClick()
        checkbox.assertIsOn()
    }
}