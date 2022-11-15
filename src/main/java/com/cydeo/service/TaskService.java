package com.cydeo.service;

import com.cydeo.dto.TaskDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TaskService {
    TaskDTO findById(Long id);
    List<TaskDTO> listAllTasks();
    void save(TaskDTO dto);
    void update(TaskDTO dto);
    void delete(Long id);


}