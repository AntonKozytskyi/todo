package org.example.todo.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Todo {

  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

  private Long id;
  private Long parentId;

  private String description;
  private LocalDateTime createdAt;
  private LocalDateTime deadline;
  private Boolean isDone;

  public Todo( String description, String deadline) {
    this(null, description, deadline);
  }

  public Todo(Long parentId, String description, String deadline) {
    this.parentId = parentId;
    this.description = description;
    this.createdAt = LocalDateTime.now();
    this.deadline = LocalDateTime.parse(deadline, DATE_TIME_FORMATTER); // for example: "1986-04-08 12:30"
    this.isDone = false;
  }

  public Todo(Long id, Long parentId, String description, LocalDateTime createdAt,
      LocalDateTime deadline, Boolean isDone) {
    this.id = id;
    this.parentId = parentId;
    this.description = description;
    this.createdAt = createdAt;
    this.deadline = deadline;
    this.isDone = isDone;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getDeadline() {
    return deadline;
  }

  public void setDeadline(LocalDateTime deadline) {
    this.deadline = deadline;
  }

  public Boolean getDone() {
    return isDone;
  }

  public void setDone(Boolean done) {
    isDone = done;
  }

  @Override
  public String toString() {
    return "Todo{" +
        "id=" + id +
        ", parentId=" + parentId +
        ", description='" + description + '\'' +
        ", createdAt=" + createdAt +
        ", deadline=" + deadline +
        ", isDone=" + isDone +
        '}';
  }
}
