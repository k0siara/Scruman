package com.scruman.backend.service;

import com.scruman.backend.entity.Sprint;
import com.scruman.backend.repository.SprintRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SprintService {

    private SprintRepository sprintRepository;

    public List<Sprint> findAllByProjectId(Long projectId) {
        return sprintRepository.findAllByProject_IdOrderByIdAsc(projectId);
    }

    public Sprint save(Sprint sprint) {
        return sprintRepository.save(sprint);
    }
}
