package com.scruman.backend.service;

import com.scruman.backend.entity.Sprint;
import com.scruman.backend.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SprintService {

    private SprintRepository sprintRepository;

    @Autowired
    public SprintService(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    public List<Sprint> findAllByProjectId(Long projectId) {
        return sprintRepository.findAllByProject_IdOrderByIdAsc(projectId);
    }
}
