package com.ioevent.ioeventhumantaskhandlerstarter.service;

import com.ioevent.ioeventhumantaskhandlerstarter.domain.HumanTaskInfos;
import com.ioevent.ioeventhumantaskhandlerstarter.repository.HumanTaskInfosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class HumanTaskInfosServiceImpl implements HumanTaskInfosService{
    private final HumanTaskInfosRepository humanTaskInfosRepository;

    public HumanTaskInfosServiceImpl(HumanTaskInfosRepository humanTaskInfosRepository) {
        this.humanTaskInfosRepository = humanTaskInfosRepository;
    }
    public List<HumanTaskInfos> getAll() {
        return humanTaskInfosRepository.findAll();
    }
    public List<HumanTaskInfos> getByProcessName(String processName){
        return humanTaskInfosRepository.findByProcessName(processName);
    }
    public Optional<HumanTaskInfos> getById(String id){
        return humanTaskInfosRepository.findById(id);
    }
}