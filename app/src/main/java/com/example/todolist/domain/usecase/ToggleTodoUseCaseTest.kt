package com.example.todolist.domain.usecase

import com.example.todolist.domain.model.TodoItem
import com.example.todolist.domain.repository.TodoRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ToggleTodoUseCaseTest {

    class ToggleTodoTestRepository : TodoRepository {
        val items = mutableListOf(
            TodoItem(1, "A", "B", false)
        )

        override suspend fun getTodos(): List<TodoItem> = items

        override suspend fun toggleTodo(id: Int) {
            val i = items.indexOfFirst { it.id == id }
            items[i] = items[i].copy(isCompleted = !items[i].isCompleted)
        }
    }

    @Test
    fun `toggleTodo changes isCompleted`() = runBlocking {
        val repo = ToggleTodoTestRepository()
        val useCase = ToggleTodoUseCase(repo)

        useCase(1)

        assertEquals(true, repo.items[0].isCompleted)
    }
}