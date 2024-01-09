package com.ioevent.ioeventhumantaskhandlerstarter.repository;

import com.ioevent.ioeventhumantaskhandlerstarter.domain.HumanTaskInfos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HumanTaskInfosRepository extends ElasticsearchRepository<HumanTaskInfos, String> {
    List<HumanTaskInfos> findAll();
    @Query("{\"term\": {\"processName.keyword\": {\"value\": \"?0\"}}}")
    Page<HumanTaskInfos> findByProcessName(String processName, Pageable pageable);

    @Query("{\"term\": {\"processName.keyword\": {\"value\": \"?0\"}}}")
    List<HumanTaskInfos> findByProcessName(String processName);
}
