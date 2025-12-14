package com.example.todolist.domain.usecase

import com.example.todolist.domain.model.TodoItem
import com.example.todolist.domain.repository.TodoRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetTodosUseCaseTest {

    class FakeRepository : TodoRepository {
        override suspend fun getTodos(): List<TodoItem> {
            return listOf(
                TodoItem(1, "A", "B", false),
                TodoItem(2, "C", "D", true),
                TodoItem(3, "E", "F", false)
            )
        }

        override suspend fun toggleTodo(id: Int) {
        }
    }

    @Test
    fun `GetTodosUseCase returns 3 tasks`() = runBlocking {
        val repo = FakeRepository()
        val useCase = GetTodosUseCase(repo)
        val result = useCase()
        assertEquals(3, result.size)
    }
}
