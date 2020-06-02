package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.models.entities.BaseEntity;
import com.example.dailymonitoring.models.entities.ProjectTaskEntity;
import com.example.dailymonitoring.models.entities.UserProjectEntity;
import com.example.dailymonitoring.models.enums.TaskStatusType;
import com.example.dailymonitoring.models.statistics.ActivityStatisticsData;
import com.example.dailymonitoring.models.statistics.ProjectTaskActivityData;
import com.example.dailymonitoring.models.statistics.TaskStatisticsData;
import com.example.dailymonitoring.respositories.ProjectTaskRepository;
import com.example.dailymonitoring.respositories.TaskRepository;
import com.example.dailymonitoring.respositories.UserProjectRepository;
import com.example.dailymonitoring.services.UserStatisticsService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.DateFormatSymbols;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author Sirghi Mihail
 */
@Service
public class UserStatisticsServiceImpl implements UserStatisticsService {

  private final UserProjectRepository userProjectRepository;

  private final TaskRepository taskRepository;

  private final ProjectTaskRepository projectTaskRepository;

  public UserStatisticsServiceImpl(UserProjectRepository userProjectRepository,
                                   TaskRepository taskRepository,
                                   ProjectTaskRepository projectTaskRepository) {
    this.userProjectRepository = userProjectRepository;
    this.taskRepository = taskRepository;
    this.projectTaskRepository = projectTaskRepository;
  }

  @Override
  public ActivityStatisticsData getUserStatistics(Long userId) {
    List<ProjectTaskEntity> projectTasks = projectTaskRepository.findAllByTaskCreatorId(userId)
        .orElse(new ArrayList<>());
    List<UserProjectEntity> userProjects = userProjectRepository.getProjectsByUser(userId)
        .orElse(new ArrayList<>());
    List<Long> projectIds = this.getUserProjectsIds(userProjects);
    List<ProjectTaskActivityData> projectTaskActivityList = new ArrayList<>();

    int allProjectTasksCount = projectTasks.size();
    int allProjectsCount = userProjects.size();
    long allDoneProjectTaskCount = this.getAllDoneProjectTaskCount(projectTasks);
    long allUndoneTasks = allProjectTasksCount - allDoneProjectTaskCount;

    Map<YearMonth, Integer> tasksPerMonths = this.getTasksPerMonths(projectTasks);
    List<String> months = new ArrayList<>();
    List<Integer> monthsTasks = new ArrayList<>();
    Locale engLocale = new Locale("eng", "US");
    Iterator<Map.Entry<YearMonth, Integer>> it = tasksPerMonths.entrySet().iterator();

    while (it.hasNext()) {
      Map.Entry<YearMonth, Integer> pair = it.next();

      months.add(new DateFormatSymbols(engLocale).getShortMonths()[this.getMonthNumber(pair.getKey()) - 1]);
      monthsTasks.add(pair.getValue());
      it.remove();
    }

    projectTaskRepository.getProjectLastActivity(projectIds, PageRequest.of(0, 5,
        Sort.Direction.DESC, "updatedAt")).orElse(new ArrayList<>())
        .stream()
        .sorted(Comparator.comparing(BaseEntity::getUpdatedAt))
        .limit(5)
        .collect(Collectors.toList())
        .forEach(projectTaskEntity ->
            projectTaskActivityList.add(
                ProjectTaskActivityData
                    .builder()
                    .projectId(projectTaskEntity.getProject().getId())
                    .task(projectTaskEntity.getName())
                    .userId(projectTaskEntity.getTaskCreator().getId())
                    .dateTime(projectTaskEntity.getUpdatedAt().toString())
                    .projectName(projectTaskEntity.getProject().getName())
                    .userName(projectTaskEntity.getTaskCreator().getFullName())
                    .build()
            ));

    return ActivityStatisticsData
        .builder()
        .projectTaskActivity(projectTaskActivityList)
        .chartMonths(months)
        .chartValues(monthsTasks)
        .taskStatistics(
            TaskStatisticsData
                .builder()
                .totalProjects((long) allProjectsCount)
                .totalDoneTasks(allDoneProjectTaskCount)
                .totalUndoneTasks(allUndoneTasks)
                .totalTasks((long) allProjectTasksCount)
                .build()
        )
        .build();
  }

  private long getAllDoneProjectTaskCount(List<ProjectTaskEntity> projectTasks) {
    return projectTasks
        .stream()
        .filter(projectTaskEntity -> projectTaskEntity.getStatus().equals(TaskStatusType.DONE))
        .count();
  }

  private List<Long> getUserProjectsIds(List<UserProjectEntity> userProjects) {
    return userProjects
        .stream()
        .map(userProjectEntity -> userProjectEntity.getProject().getId())
        .collect(Collectors.toList());
  }

  private Integer getMonthNumber(YearMonth value) {
    return Integer.parseInt(
        value
            .toString()
            .replace("2020", "")
            .replace("-", ""));
  }

  private Map<YearMonth, Integer> getTasksPerMonths(List<ProjectTaskEntity> projectTasks) {
    return projectTasks
        .stream()
        .collect(Collectors.groupingBy(m -> YearMonth.from(
            new Date(m.getUpdatedAt()
                .getTime())
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()),
            TreeMap::new,
            Collectors.summingInt(m -> Math.toIntExact(1))
        ));
  }
}
