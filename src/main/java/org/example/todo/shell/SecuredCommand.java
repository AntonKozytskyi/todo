package org.example.todo.shell;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.Availability;

public abstract class SecuredCommand {

  public Availability isUserSignedIn() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication instanceof UsernamePasswordAuthenticationToken) {
      return Availability.available();
    }

    return Availability.unavailable("you should be signed in to perform this action");
  }
}
