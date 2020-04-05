package com.example.dailymonitoring.controllers;


import com.example.dailymonitoring.controllers.api.TaskApi;
import com.example.dailymonitoring.models.Error;
import com.example.dailymonitoring.models.TaskData;
import com.example.dailymonitoring.models.entities.UserEntity;
import com.example.dailymonitoring.services.impl.TaskServiceImpl;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController implements TaskApi {

  private TaskServiceImpl taskServiceImpl;

  public TaskController(TaskServiceImpl taskServiceImpl) {
    this.taskServiceImpl = taskServiceImpl;
  }


  @Override
  public ResponseEntity<?> taskCreate(@Valid TaskData taskData , @Valid UserEntity userId) {
    TaskData result = this.taskServiceImpl.createTask(taskData , userId);
    return result.getId() != null
        ? ResponseEntity.status(HttpStatus.CREATED).body(result)
        : ResponseEntity.badRequest()
              .body(Error
                  .builder()
                  .code(400)
                  .message("Failed to created task.")
                  .description("No such user.")
                  .build());
  }
}
