/*
 * Copyright © 2024 CodeOnce Software (https://www.codeonce.fr/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
