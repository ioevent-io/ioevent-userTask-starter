package com.ioevent.ioeventhumantaskhandlerstarter.service;

import com.ioevent.ioeventhumantaskhandlerstarter.domain.HumanTaskInfos;
import com.ioevent.ioeventhumantaskhandlerstarter.repository.HumanTaskInfosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.QueryBuilders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HumanTaskInfosServiceImpl implements HumanTaskInfosService{
    private final HumanTaskInfosRepository humanTaskInfosRepository;

    public HumanTaskInfosServiceImpl(HumanTaskInfosRepository humanTaskInfosRepository) {
        this.humanTaskInfosRepository = humanTaskInfosRepository;
    }
    public List<HumanTaskInfos> getAll() {
        return (List<HumanTaskInfos>) humanTaskInfosRepository.findAll();
    }
    /*public Page<HumanTaskInfos> getByProcessName(String processName, Pageable pageable) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(QueryBuilders.termQueryAsQuery("processName.keyword", processName))
                .withPageable(pageable)
                .build();


        return humanTaskInfosRepository.findByProcessName(processName, pageable);
    }*/
    public List<HumanTaskInfos> getByProcessName(String processName){
        return humanTaskInfosRepository.findByProcessName(processName);
    }
}