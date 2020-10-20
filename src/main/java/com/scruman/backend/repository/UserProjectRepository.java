package com.scruman.backend.repository;

import com.scruman.backend.entity.UserProject;
import com.scruman.backend.entity.UserProjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, UserProjectId> {
}