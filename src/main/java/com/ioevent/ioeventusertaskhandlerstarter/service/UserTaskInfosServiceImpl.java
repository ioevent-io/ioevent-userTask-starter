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
    public void deactivateHumanTask(final String id){
        Optional<UserTaskInfos> humanTaskInfos = userTaskInfosRepository.findByIdAndActiveTrue(id);
        if(humanTaskInfos.isPresent()){
            humanTaskInfos.get().setActive(false);
            userTaskInfosRepository.save(humanTaskInfos.get());
        }
    }
    @Override
    public UserTaskInfos save(final UserTaskInfos userTaskInfos){
        if (userTaskInfos.getIsImplicitStart()){
            List<UserTaskInfos> userTaskInfosList = userTaskInfosRepository.findByProcessNameAndStepNameAndActiveTrue(userTaskInfos.getProcessName(), userTaskInfos.getStepName());
            if(userTaskInfosList.isEmpty()){
                return userTaskInfosRepository.save(userTaskInfos);
            }
        }
        return userTaskInfosRepository.save(userTaskInfos);
    }

    @Override
    public void saveAll(final List<UserTaskInfos> userTaskInfos){
        userTaskInfos.forEach(humanTaskInfo -> {
            if (humanTaskInfo.getIsImplicitStart()){
                List<UserTaskInfos> userTaskInfosList = userTaskInfosRepository.findByProcessNameAndStepNameAndActiveTrue(humanTaskInfo.getProcessName(), humanTaskInfo.getStepName());
                if(userTaskInfosList.isEmpty()){
                    userTaskInfosRepository.save(humanTaskInfo);

                }
                return;
            }
            userTaskInfosRepository.save(humanTaskInfo);
        });
    }
}