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


import java.util.List;
import java.util.Optional;

public interface UserTaskInfosService {
    List<UserTaskInfos> getAll();
    Optional<UserTaskInfos> getById(String id);

    List<UserTaskInfos> getByProcessNameAndActiveTrue(String processName);
    void deactivateUserTask(String id);

    UserTaskInfos save(UserTaskInfos userTaskInfos);

    void saveAll(List<UserTaskInfos> userTaskInfos);

    void purgeAll();

}