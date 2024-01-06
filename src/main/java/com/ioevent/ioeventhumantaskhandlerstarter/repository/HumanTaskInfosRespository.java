package com.ioevent.ioeventhumantaskhandlerstarter.repository;

import com.ioevent.ioeventhumantaskhandlerstarter.domain.HumanTaskInfos;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HumanTaskInfosRespository extends ElasticsearchRepository<HumanTaskInfos, String> {
}
