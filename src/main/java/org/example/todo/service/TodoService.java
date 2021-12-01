package org.example.todo.service;

import java.util.List;
import org.example.todo.model.Todo;

public interface TodoService {
  void create(Todo todo);
  void markAsDone(Long id);
  List<Todo> getInProgressItems();
  boolean sendNotification(Todo todo);
}
