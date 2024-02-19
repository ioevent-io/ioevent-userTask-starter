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

package com.ioevent.ioeventusertaskhandlerstarter.service;

import com.ioevent.ioeventusertaskhandlerstarter.domain.UserTaskInfos;
import com.ioevent.ioeventusertaskhandlerstarter.repository.UserTaskInfosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserTaskInfosServiceImpl implements UserTaskInfosService {
    private final UserTaskInfosRepository userTaskInfosRepository;

    public UserTaskInfosServiceImpl(final UserTaskInfosRepository userTaskInfosRepository) {
        this.userTaskInfosRepository = userTaskInfosRepository;
    }
    @Override
    public List<UserTaskInfos> getAll() {
        return userTaskInfosRepository.findAllByActiveTrue();
    }
    @Override
    public Optional<UserTaskInfos> getById(final String id){
        return userTaskInfosRepository.findByIdAndActiveTrue(id);
    }
    @Override
    public List<UserTaskInfos> getByProcessNameAndActiveTrue(final String processName){
        return userTaskInfosRepository.findByProcessNameAndActiveTrue(processName);
    }
    @Override
    public void deactivateUserTask(final String id){
        Optional<UserTaskInfos> userTaskInfos = userTaskInfosRepository.findByIdAndActiveTrue(id);
        if(userTaskInfos.isPresent()){
            userTaskInfos.get().setActive(false);
            userTaskInfosRepository.save(userTaskInfos.get());
        }
    }
    @Override
    public UserTaskInfos save(final UserTaskInfos userTaskInfos){
        return userTaskInfosRepository.save(userTaskInfos);
    }

    @Override
    public void saveAll(final List<UserTaskInfos> userTaskInfos){
        userTaskInfos.forEach(userTaskInfosRepository::save);
    }

    @Override
    public void purgeAll(){
        List<UserTaskInfos> userTaskInfos = userTaskInfosRepository.findAllByActiveTrue();
        userTaskInfos.forEach(userTaskInfo -> this.deactivateUserTask(userTaskInfo.getId()));
    }
}