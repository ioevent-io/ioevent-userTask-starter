package com.ioevent.ioeventhumantaskhandlerstarter.service;

import com.ioevent.ioeventhumantaskhandlerstarter.domain.HumanTaskInfos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HumanTaskInfosService {
    List<HumanTaskInfos> getAll();
    //Page<HumanTaskInfos> getByProcessName(String processName, Pageable pageable);
    List<HumanTaskInfos> getByProcessName(String processName);
}