package org.example.todo.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.example.todo.exception.InvalidOperationException;
import org.example.todo.model.Todo;
import org.example.todo.service.NotificationService;
import org.example.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {

  private final JdbcTemplate jdbcTemplate;
  private final NotificationService notificationService;

  @Autowired
  public TodoServiceImpl(JdbcTemplate jdbcTemplate, NotificationService notificationService) {
    this.jdbcTemplate = jdbcTemplate;
    this.notificationService = notificationService;
  }

  @Override
  public void create(Todo todo) {
    jdbcTemplate.update("INSERT INTO todos (PARENTID, DESCRIPTION, DEADLINE, ISDONE) VALUES (?, ?, ?, ?)",
        todo.getParentId(), todo.getDescription(), todo.getDeadline(), todo.getDone());
  }

  @Override
  public void markAsDone(Long id) {
    if (hasUndoneItems(id)) {
      throw new InvalidOperationException("Cannot mark as done cause the item has undone children");
    }

    jdbcTemplate.update("UPDATE todos SET ISDONE = true WHERE ID = ?", id);
  }

  @Override
  public List<Todo> getInProgressItems() {
    return jdbcTemplate.query("SELECT * FROM todos WHERE ISDONE = false", new TodoRowMapper());
  }

  @Override
  public boolean sendNotification(Todo todo) {
    return notificationService.sendNotification();
  }

  private Todo getById(Long id) {
    return jdbcTemplate.queryForObject("SELECT * FROM todos WHERE ID = ?", new TodoRowMapper(), id);
  }

  private boolean hasUndoneItems(Long parentId) {
    return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
        "SELECT COUNT(*) > 0 FROM todos WHERE PARENTID = ? AND ISDONE = false", Boolean.class,
        parentId));
  }

  private static class TodoRowMapper implements RowMapper<Todo> {
    @Override
    public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new Todo(
          rs.getLong("id"),
          rs.getLong("parentId"),
          rs.getString("description"),
          rs.getTimestamp("createdAt").toLocalDateTime(),
          rs.getTimestamp("deadline").toLocalDateTime(),
          rs.getBoolean("isDone")
      );
    }
  }
}
