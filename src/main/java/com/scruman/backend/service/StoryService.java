package com.scruman.backend.service;

import com.scruman.backend.entity.Status;
import com.scruman.backend.entity.Story;
import com.scruman.backend.repository.StatusRepository;
import com.scruman.backend.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoryService {

    private StoryRepository storyRepository;
    private StatusRepository statusRepository;

    @Autowired
    public StoryService(StoryRepository storyRepository, StatusRepository statusRepository) {
        this.storyRepository = storyRepository;
        this.statusRepository = statusRepository;
    }

    public Story save(Story story) {
        return storyRepository.save(story);
    }

    public List<Status> getAvailableStatuses() {
        return statusRepository.findByOrderByIdAsc();
    }

    public List<Story> findAllByProjectAndStatus(Long projectId, String status) {
        return storyRepository.findAllByProject_IdAndStatus_Name(projectId, status);
    }

    public List<Story> findAllByProjectAndSprintAndStatus(Long projectId, Long sprintId, String status) {
        return storyRepository.findAllByProject_IdAndSprint_IdAndStatus_Name(projectId, sprintId, status);
    }
}
