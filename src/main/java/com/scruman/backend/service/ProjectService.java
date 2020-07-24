package com.scruman.backend.service;

import com.scruman.backend.entity.Project;
import com.scruman.backend.entity.Sprint;
import com.scruman.backend.entity.User;
import com.scruman.backend.entity.UserProject;
import com.scruman.backend.exception.ProjectNotFoundException;
import com.scruman.backend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectService {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Sprint> getSprints(Long projectId) {
        return new ArrayList<>(findById(projectId).getSprints());
    }

    public Project findById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));
    }

    public List<User> getProjectMembers(Long projectId) {
        return findById(projectId).getUserProjects().stream()
                .map(UserProject::getUser)
                .collect(Collectors.toList());
    }

    public List<Project> getUserProjects(Long userId) {
        return projectRepository.findAllByUserProjectsUserId(userId);
    }
}
