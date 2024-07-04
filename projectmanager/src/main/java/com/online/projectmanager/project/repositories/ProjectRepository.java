package com.online.projectmanager.project.repositories;

import com.online.projectmanager.project.models.Project;
import com.online.projectmanager.users.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM projects")
    List<Project> findAllProjects();

    @Query(nativeQuery = true, value = "SELECT * FROM projects p WHERE p.id = :id")
    Project findById(@Param("id") Integer id);

    Project findByName(String name);
}
