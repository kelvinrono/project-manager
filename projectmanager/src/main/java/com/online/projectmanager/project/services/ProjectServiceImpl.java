package com.online.projectmanager.project.services;

import com.online.projectmanager.project.DTOs.ProjectDTO;
import com.online.projectmanager.project.models.Project;
import com.online.projectmanager.project.models.Status;
import com.online.projectmanager.project.repositories.ProjectRepository;
import com.online.projectmanager.project.services.constants.PageObject;
import com.online.projectmanager.users.models.User;
import com.online.projectmanager.users.repositories.UserRepository;
import com.online.projectmanager.users.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {
    private  final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    @Override
    public HashMap createProject(ProjectDTO project) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            User user = userService.getUser(project.getUserId()).orElseThrow(()-> new IllegalArgumentException("User with the given id does not exist"));
            Project newProject = Project.builder()
                    .name(project.getName())
                    .user(user)
                    .description(project.getDescription())
                    .startDate(project.getStartDate())
                    .expectedEndDate(project.getExpectedEndDate())
                    .status(Status.NOT_STARTED)
                    .build();
            projectRepository.save(newProject);
            response.put("message", "project saved successfully");
            response.put("status", true);
            return response;
        }catch (Exception e){
           return getError(e);
        }

    }

    @Override
    public HashMap getAllProjects( PageObject pageObject) {
        HashMap <String, Object> response = new HashMap<>();
        try{

        }catch (Exception e){
            return getError(e);
        }
        PageRequest pageRequest = PageRequest.of(pageObject.getPageNumber(), pageObject.getPageSize(), Sort.by("id").descending());
        Page<Project> data = projectRepository.findAll(pageRequest);

        response.put("message", "data retrieved successfully");
        response.put("status", true);
        response.put("data", data);

        return response;
    }

    @Override
    public HashMap getProject(Long id) {
        HashMap<String, Object> response = new HashMap();

        try{
            Optional<Project> project = projectRepository.findById(id);

            if(project.isEmpty()){
                response.put("message", "Project with the given id does not exist");
                response.put("status", false);
                response.put("statusCode", 404);
                return response;
            }else {
                response.put("message", "Project retrieved successfully");
                response.put("status", true);
                response.put("data", project);
                return response;
            }
        }
        catch (Exception e){
            return getError(e);
        }

    }

    @Override
    public HashMap getProjectWithName(ProjectDTO project) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Project existingProjectName = projectRepository.findByName(project.getName());
            if(existingProjectName!=null){
                response.put("message", "project with the same name already exist");
                response.put("status", false);
            }

        }
        catch (Exception e){
           return getError(e);
        }
        return response;
    }

    HashMap getError(Exception e){
        HashMap<String, Object> response = new HashMap<>();

        e.printStackTrace();
        log.error("Oops! An error occured"+e.getMessage());
        response.put("message", "Oops! Something went wrong");
        response.put("status", false);
        response.put("statusCode", 500);
        return response;
    }

}
