package org.example.todo.security;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.todo.model.CliUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public UserService(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public CliUser getUserByUsername(String username) {
    return jdbcTemplate.queryForObject("SELECT * FROM users WHERE username = ?", new UserRowMapper(), username);
  }

  private static class UserRowMapper implements RowMapper<CliUser> {

    @Override
    public CliUser mapRow(ResultSet resultSet, int i) throws SQLException {
      return new CliUser(resultSet.getString("username"), resultSet.getString("password"));
    }
  }
}
