package com.scruman.backend.service;

import com.scruman.backend.entity.Project;
import com.scruman.backend.entity.Sprint;
import com.scruman.backend.entity.User;
import com.scruman.backend.entity.UserProject;
import com.scruman.backend.exception.ProjectNotFoundException;
import com.scruman.backend.repository.ProjectRepository;
import com.scruman.backend.repository.UserProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ProjectService {

    private ProjectRepository projectRepository;
    private UserProjectRepository userProjectRepository;

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

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public List<UserProject> saveUserProjects(Collection<UserProject> userProjects) {
        return userProjectRepository.saveAll(userProjects);
    }
}
