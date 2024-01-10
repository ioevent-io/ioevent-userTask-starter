package com.ioevent.ioeventhumantaskhandlerstarter.service;

import com.ioevent.ioeventhumantaskhandlerstarter.domain.HumanTaskInfos;


import java.util.List;
import java.util.Optional;

public interface HumanTaskInfosService {
    List<HumanTaskInfos> getAll();

    List<HumanTaskInfos> getByProcessName(String processName);
    Optional<HumanTaskInfos> getById(String id);
}