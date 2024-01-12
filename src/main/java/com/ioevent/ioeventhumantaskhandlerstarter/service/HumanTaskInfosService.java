package com.ioevent.ioeventhumantaskhandlerstarter.service;

import com.ioevent.ioeventhumantaskhandlerstarter.domain.HumanTaskInfos;


import java.util.List;
import java.util.Optional;

public interface HumanTaskInfosService {
    List<HumanTaskInfos> getAll();
    Optional<HumanTaskInfos> getById(String id);

    List<HumanTaskInfos> getByProcessNameAndActiveTrue(String processName);
    void deactivateHumanTask(String id);
}