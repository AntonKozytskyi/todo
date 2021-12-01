package org.example.todo.service.impl;

import org.example.todo.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

  @Override
  public boolean sendNotification() {
    return true;
  }
}
