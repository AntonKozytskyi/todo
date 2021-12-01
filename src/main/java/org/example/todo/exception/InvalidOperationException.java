package org.example.todo.exception;

public class InvalidOperationException extends IllegalStateException {

  public InvalidOperationException() {
    super("Invalid operation caused");
  }

  public InvalidOperationException(String message) {
    super(message);
  }
}
