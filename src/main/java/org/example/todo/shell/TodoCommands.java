package org.example.todo.shell;

import java.util.List;
import org.example.todo.exception.InvalidOperationException;
import org.example.todo.model.Todo;
import org.example.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class TodoCommands extends SecuredCommand {

  private final AuthenticationManager authenticationManager;
  private final TodoService todoService;

  @Autowired
  public TodoCommands(AuthenticationManager authenticationManager, TodoService todoService) {
    this.authenticationManager = authenticationManager;
    this.todoService = todoService;
  }

  @ShellMethod("Sign in as a todo user")
  public String signin(
      @ShellOption({"-u", "--username"}) String username,
      @ShellOption({"-p", "--password"}) String password) {

    Authentication request = new UsernamePasswordAuthenticationToken(
        username, password);

    try {
      Authentication result = authenticationManager.authenticate(request);
      SecurityContextHolder.getContext().setAuthentication(result);

      return "Successfully authenticated";
    } catch (AuthenticationException e) {
      return "Authentication failed";
    }
  }

  /**
   * Example of usage:
   * shell:>create -D 'Test description' -Dl '2021-12-01 20:00'
   *
   * @param description Description text
   * @param deadline Deadline date
   */
  @ShellMethod("Create a new Todo item")
  @ShellMethodAvailability("isUserSignedIn")
  public void create(
      @ShellOption({"-D", "--description"}) String description,
      @ShellOption(value = {"-Dl", "--deadline"}, help = "Use the following format: '2021-12-01 20:00'") String deadline) {

    todoService.create(new Todo(description, deadline));
  }

  /**
   * Example of usage:
   * shell:>create -P 2 -D 'Test description' -Dl '2021-12-01 20:00'
   *
   * @param parentId relation to the parent
   * @param description Description text
   * @param deadline Deadline date
   */
  @ShellMethod("Create a new child Todo item")
  @ShellMethodAvailability("isUserSignedIn")
  public void createChild(
      @ShellOption({"-p", "--parentId"}) Long parentId,
      @ShellOption({"-D", "--description"}) String description,
      @ShellOption(value = {"-Dl", "--deadline"}, help = "Use the following format: '2021-12-01 20:00'") String deadline) {

    todoService.create(new Todo(parentId, description, deadline));
  }

  /**
   * Example of usage:
   * shell:>mark-as-done --id 5
   *
   * @param id Identifier of item to be marked as done
   */
  @ShellMethod("Mark a Todo as done")
  @ShellMethodAvailability("isUserSignedIn")
  public String markAsDone(@ShellOption({"--id"}) Long id) {
    try {
      todoService.markAsDone(id);
      return "Marked as done";
    } catch (InvalidOperationException e) {
      return e.getMessage();
    }
  }

  /**
   * Example of usage:
   * shell:>get-in-progress-items
   *
   * @return list of items in progress
   */
  @ShellMethod("Get the list of todos that are in progress")
  @ShellMethodAvailability("isUserSignedIn")
  public List<Todo> getInProgressItems() {
    return todoService.getInProgressItems();
  }
}
