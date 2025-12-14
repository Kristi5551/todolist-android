package com.example.todolist.data.repository

import com.example.todolist.data.local.TodoJsonDataSource
import com.example.todolist.domain.model.TodoItem
import com.example.todolist.domain.repository.TodoRepository

class TodoRepositoryImpl(private val dataSource: TodoJsonDataSource) : TodoRepository {
    private var todosCache = mutableListOf<TodoItem>()

    override suspend fun getTodos(): List<TodoItem> {
        if (todosCache.isEmpty()) {
            todosCache = dataSource.getTodos().map { dto ->
                TodoItem(dto.id, dto.title, dto.description, dto.isCompleted)
            }.toMutableList()
        }
        return todosCache
    }

    override suspend fun toggleTodo(id: Int) {
        val index = todosCache.indexOfFirst { it.id == id }
        if (index != -1) {
            val old = todosCache[index]
            todosCache[index] = old.copy(isCompleted = !old.isCompleted)
        }
    }
}