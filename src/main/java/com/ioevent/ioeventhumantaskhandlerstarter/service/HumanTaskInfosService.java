package com.ioevent.ioeventhumantaskhandlerstarter.service;

import com.ioevent.ioeventhumantaskhandlerstarter.domain.HumanTaskInfos;


import java.util.List;

public interface HumanTaskInfosService {
    List<HumanTaskInfos> getAll();

    List<HumanTaskInfos> getByProcessName(String processName);
}