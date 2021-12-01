package org.example.todo.security;

import java.util.Optional;
import org.example.todo.model.CliUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TodoUserDetailsService implements UserDetailsService {

  private final UserService userService;

  public TodoUserDetailsService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    CliUser user = Optional.ofNullable(userService.getUserByUsername(username))
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    User.UserBuilder builder = User.withUsername(username);
    builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));

    return builder.build();
  }
}
