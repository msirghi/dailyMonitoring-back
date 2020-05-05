package com.example.dailymonitoring.services;

import com.example.dailymonitoring.models.ProjectData;
import com.example.dailymonitoring.models.ProjectTaskData;
import com.example.dailymonitoring.models.TaskData;
import com.example.dailymonitoring.models.UserTaskData;
import java.util.List;
import javassist.NotFoundException;

public interface ProjectTaskService {

  TaskData projectTaskCreate(TaskData taskData, Long userId, Long projectId);

  TaskData getProjectTaskById(Long userId, Long projectId, Long taskId);

  List<TaskData> getAllTasksByProjectId(Long userId, Long projectId) throws NotFoundException;

  TaskData deleteProjectTask(Long userId, Long projectId, Long taskId);

  TaskData updateProjectTask(TaskData projectData, Long userId, Long projectId, Long taskId);

  int markProjectTaskAsDone(Long userId, Long projectId, Long taskId);

  List<UserTaskData> getAllInProgressProjectTasks(Long userId, Long projectId);

  List<TaskData> getLastDoneTasks(Long userId, Long projectId);

}
