package com.online.projectmanager.project.services;

import com.online.projectmanager.project.DTOs.ProjectDTO;
import com.online.projectmanager.project.services.constants.PageObject;

import java.util.HashMap;

public interface ProjectService {

    HashMap createProject(ProjectDTO project);
    HashMap getAllProjects(PageObject project);
    HashMap getProject(Long id);
    HashMap getProjectWithName(ProjectDTO project);
}
