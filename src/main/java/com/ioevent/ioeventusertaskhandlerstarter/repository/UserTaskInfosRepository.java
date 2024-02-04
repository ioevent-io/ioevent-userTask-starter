package com.ioevent.ioeventusertaskhandlerstarter.repository;

import com.ioevent.ioeventusertaskhandlerstarter.domain.UserTaskInfos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTaskInfosRepository extends ElasticsearchRepository<UserTaskInfos, String> {
    List<UserTaskInfos> findAllByActiveTrue();
    @Query("{\"term\": {\"processName.keyword\": {\"value\": \"?0\"}}}")
    Page<UserTaskInfos> findByProcessName(String processName, Pageable pageable);

    @Query("{\"term\": {\"processName.keyword\": {\"value\": \"?0\"}}}")
    List<UserTaskInfos> findByProcessName(String processName);

    List<UserTaskInfos> findByProcessNameAndActiveTrue(String processName);

    Optional<UserTaskInfos> findByIdAndActiveTrue(String id);

    List<UserTaskInfos> findByProcessNameAndStepNameAndActiveTrue(String processName, String stepName);
}
