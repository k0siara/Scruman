package com.scruman.backend.repository;

import com.scruman.backend.entity.Project;
import com.scruman.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllByUserProjectsUserId(Long userId);
}
