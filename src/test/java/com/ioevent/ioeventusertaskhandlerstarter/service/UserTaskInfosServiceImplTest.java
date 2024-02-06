package com.ioevent.ioeventusertaskhandlerstarter.service;

import com.ioevent.ioeventusertaskhandlerstarter.domain.UserTaskInfos;
import com.ioevent.ioeventusertaskhandlerstarter.repository.UserTaskInfosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class UserTaskInfosServiceImplTest {
    @Mock
    private UserTaskInfosRepository userTaskInfosRepository;
    @InjectMocks
    private UserTaskInfosServiceImpl userTaskInfosServiceImpl;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getAll() {
        List<UserTaskInfos> userTaskInfosList = new ArrayList<>();
        userTaskInfosList.add(new UserTaskInfos("1", "appName", "processName", "1111", "Task", new ArrayList<>(), "outputEvent", null, "stepName", "apiKey", 1L, 1L, false, false, "payload", new byte[0], true));
        userTaskInfosList.add(new UserTaskInfos("2", "appName", "processName", "2222", "Task", new ArrayList<>(), "outputEvent", null, "stepName", "apiKey", 1L, 1L, false, false, "payload", new byte[0], true));
        userTaskInfosList.add(new UserTaskInfos("3", "appName", "processName", "3333", "Task", new ArrayList<>(), "outputEvent", null, "stepName", "apiKey", 1L, 1L, false, false, "payload", new byte[0], true));
        when(userTaskInfosRepository.findAllByActiveTrue()).thenReturn(userTaskInfosList);
        List<UserTaskInfos> result = userTaskInfosServiceImpl.getAll();
        assertEquals(result, userTaskInfosList);
    }

    @Test
    void getById() {
        UserTaskInfos userTaskInfos = new UserTaskInfos("1", "appName", "processName", "1111", "Task", new ArrayList<>(), "outputEvent", null, "stepName", "apiKey", 1L, 1L, false, false, "payload", new byte[0], true);
        when(userTaskInfosRepository.findByIdAndActiveTrue("1")).thenReturn(Optional.of(userTaskInfos));
        Optional<UserTaskInfos> result = userTaskInfosServiceImpl.getById("1");
        assertEquals(result, Optional.of(userTaskInfos));
        assertTrue(result.isPresent());
    }

    @Test
    void getByProcessNameAndActiveTrue() {
        List<UserTaskInfos> userTaskInfosList = new ArrayList<>();
        userTaskInfosList.add(new UserTaskInfos("1", "appName", "processName", "1111", "Task", new ArrayList<>(), "outputEvent", null, "stepName", "apiKey", 1L, 1L, false, false, "payload", new byte[0], true));
        userTaskInfosList.add(new UserTaskInfos("2", "appName", "processName", "2222", "Task", new ArrayList<>(), "outputEvent", null, "stepName", "apiKey", 1L, 1L, false, false, "payload", new byte[0], true));
        userTaskInfosList.add(new UserTaskInfos("3", "appName", "processName", "3333", "Task", new ArrayList<>(), "outputEvent", null, "stepName", "apiKey", 1L, 1L, false, false, "payload", new byte[0], true));
        when(userTaskInfosRepository.findByProcessNameAndActiveTrue("processName")).thenReturn(userTaskInfosList);
        List<UserTaskInfos> result = userTaskInfosServiceImpl.getByProcessNameAndActiveTrue("processName");
        assertEquals(result, userTaskInfosList);
    }

    @Test
    void deactivateUserTask() {
        UserTaskInfos userTaskInfos = new UserTaskInfos("1", "appName", "processName", "1111", "Task", new ArrayList<>(), "outputEvent", null, "stepName", "apiKey", 1L, 1L, false, false, "payload", new byte[0], true);
        when(userTaskInfosRepository.findByIdAndActiveTrue("1")).thenReturn(Optional.of(userTaskInfos));
        userTaskInfosServiceImpl.deactivateUserTask("1");
        assertEquals(false,userTaskInfos.getActive());
    }

    @Test
    void saveWhenUserTaskIsNotImplicitStart() {
        UserTaskInfos userTaskInfos = new UserTaskInfos("1", "appName", "processName", "1111", "Task", new ArrayList<>(), "outputEvent", null, "stepName", "apiKey", 1L, 1L, false, false, "payload", new byte[0], true);
        when(userTaskInfosRepository.save(userTaskInfos)).thenReturn(userTaskInfos);
        UserTaskInfos result = userTaskInfosServiceImpl.save(userTaskInfos);
        assertEquals(result, userTaskInfos);
    }

    @Test
    void saveWhenUserTaskIsImplicitStartAndFindByProcessNameAndStepNameAndActiveTrueIsEmpty() {
        UserTaskInfos userTaskInfos = new UserTaskInfos("1", "appName", "processName", "1111", "Task", new ArrayList<>(), "outputEvent", null, "stepName", "apiKey", 1L, 1L, true, false, "payload", new byte[0], true);
        when(userTaskInfosRepository.findByProcessNameAndStepNameAndActiveTrue("processName", "stepName")).thenReturn(new ArrayList<>());
        when(userTaskInfosRepository.save(userTaskInfos)).thenReturn(userTaskInfos);
        UserTaskInfos result = userTaskInfosServiceImpl.save(userTaskInfos);
        assertEquals(result, userTaskInfos);
    }

    @Test
    void saveAllWhenUserTaskIsNotImplicitStart() {
        List<UserTaskInfos> userTaskInfosList = new ArrayList<>();
        userTaskInfosList.add(new UserTaskInfos("1", "appName", "processName", "1111", "Task", new ArrayList<>(), "outputEvent", null, "stepName", "apiKey", 1L, 1L, false, false, "payload", new byte[0], true));
        userTaskInfosList.add(new UserTaskInfos("2", "appName", "processName", "2222", "Task", new ArrayList<>(), "outputEvent", null, "stepName", "apiKey", 1L, 1L, false, false, "payload", new byte[0], true));
        when(userTaskInfosRepository.save(userTaskInfosList.get(0))).thenReturn(userTaskInfosList.get(0));
        when(userTaskInfosRepository.save(userTaskInfosList.get(1))).thenReturn(userTaskInfosList.get(1));
        userTaskInfosServiceImpl.saveAll(userTaskInfosList);
        verify(userTaskInfosRepository,times(2)).save(any(UserTaskInfos.class));
    }

    @Test
    void saveAllWhenUserTaskIsImplicitStartAndFindByProcessNameAndStepNameAndActiveTrueIsEmpty() {
        List<UserTaskInfos> userTaskInfosList = new ArrayList<>();
        userTaskInfosList.add(new UserTaskInfos("1", "appName", "processName", "1111", "Task", new ArrayList<>(), "outputEvent", null, "stepName", "apiKey", 1L, 1L, true, false, "payload", new byte[0], true));
        userTaskInfosList.add(new UserTaskInfos("2", "appName", "processName", "2222", "Task", new ArrayList<>(), "outputEvent", null, "stepName", "apiKey", 1L, 1L, true, false, "payload", new byte[0], true));
        when(userTaskInfosRepository.findByProcessNameAndStepNameAndActiveTrue("processName", "stepName")).thenReturn(new ArrayList<>());
        when(userTaskInfosRepository.save(userTaskInfosList.get(0))).thenReturn(userTaskInfosList.get(0));
        when(userTaskInfosRepository.save(userTaskInfosList.get(1))).thenReturn(userTaskInfosList.get(1));
        userTaskInfosServiceImpl.saveAll(userTaskInfosList);
        verify(userTaskInfosRepository,times(2)).save(any(UserTaskInfos.class));
        verify(userTaskInfosRepository,times(2)).findByProcessNameAndStepNameAndActiveTrue("processName", "stepName");
    }

}
