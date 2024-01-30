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

    public HumanTaskInfosServiceImpl(final HumanTaskInfosRepository humanTaskInfosRepository) {
        this.humanTaskInfosRepository = humanTaskInfosRepository;
    }
    @Override
    public List<HumanTaskInfos> getAll() {
        return humanTaskInfosRepository.findAllByActiveTrue();
    }
    @Override
    public Optional<HumanTaskInfos> getById(final String id){
        return humanTaskInfosRepository.findByIdAndActiveTrue(id);
    }
    @Override
    public List<HumanTaskInfos> getByProcessNameAndActiveTrue(final String processName){
        return humanTaskInfosRepository.findByProcessNameAndActiveTrue(processName);
    }
    @Override
    public void deactivateHumanTask(final String id){
        Optional<HumanTaskInfos> humanTaskInfos = humanTaskInfosRepository.findByIdAndActiveTrue(id);
        if(humanTaskInfos.isPresent()){
            humanTaskInfos.get().setActive(false);
            humanTaskInfosRepository.save(humanTaskInfos.get());
        }
    }
    @Override
    public HumanTaskInfos save(final HumanTaskInfos humanTaskInfos){
        if (humanTaskInfos.getIsImplicitStart()){
            List<HumanTaskInfos> humanTaskInfosList = humanTaskInfosRepository.findByProcessNameAndStepNameAndActiveTrue(humanTaskInfos.getProcessName(), humanTaskInfos.getStepName());
            if(humanTaskInfosList.isEmpty()){
                return humanTaskInfosRepository.save(humanTaskInfos);
            }
        }
        return humanTaskInfosRepository.save(humanTaskInfos);
    }

    @Override
    public void saveAll(final List<HumanTaskInfos> humanTaskInfos){
        humanTaskInfos.forEach(humanTaskInfo -> {
            if (humanTaskInfo.getIsImplicitStart()){
                List<HumanTaskInfos> humanTaskInfosList = humanTaskInfosRepository.findByProcessNameAndStepNameAndActiveTrue(humanTaskInfo.getProcessName(), humanTaskInfo.getStepName());
                if(humanTaskInfosList.isEmpty()){
                    humanTaskInfosRepository.save(humanTaskInfo);

                }
                return;
            }
            humanTaskInfosRepository.save(humanTaskInfo);
        });
    }
}