package com.ioevent.ioeventusertaskhandlerstarter.service;

import com.ioevent.ioeventusertaskhandlerstarter.domain.UserTaskInfos;


import java.util.List;
import java.util.Optional;

public interface UserTaskInfosService {
    List<UserTaskInfos> getAll();
    Optional<UserTaskInfos> getById(String id);

    List<UserTaskInfos> getByProcessNameAndActiveTrue(String processName);
    void deactivateUserTask(String id);

    UserTaskInfos save(UserTaskInfos userTaskInfos);

    void saveAll(List<UserTaskInfos> userTaskInfos);
}