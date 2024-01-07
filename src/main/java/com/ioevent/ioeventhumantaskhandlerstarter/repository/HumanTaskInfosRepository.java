package com.ioevent.ioeventhumantaskhandlerstarter.repository;

import com.ioevent.ioeventhumantaskhandlerstarter.domain.HumanTaskInfos;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HumanTaskInfosRepository extends ElasticsearchRepository<HumanTaskInfos, String> {
    List<HumanTaskInfos> findAll();
}