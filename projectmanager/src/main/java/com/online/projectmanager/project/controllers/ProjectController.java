package com.online.projectmanager.project.controllers;

import com.online.projectmanager.project.repositories.ProjectRepository;
import com.online.projectmanager.project.services.ProjectService;
import com.online.projectmanager.project.services.constants.PageObject;
import com.online.projectmanager.users.repositories.UserRepository;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/projects")
public class ProjectController {
    private final UserRepository userRepository;
    private final ProjectService projectService;

    @GetMapping("/")
    public ResponseEntity<HashMap<String, Object>> getAllProjects(@RequestBody PageObject pageObject){
        return ResponseEntity.ok(projectService.getAllProjects(pageObject));
    }

    @GetMapping("/project")
    public ResponseEntity<HashMap<String, Object>> getProject(@PathParam("id") long id){
        HashMap<String, Object> response  = projectService.getProject(id);

        if(projectService.getProject(id).get("status").equals(false)){
            return new ResponseEntity<>(response, (HttpStatusCode) response.get("statusCode"));
        }
        return new ResponseEntity(projectService.getProject(id), HttpStatus.OK);
    }



}
