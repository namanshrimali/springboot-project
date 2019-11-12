package com.westpac.democode_review.controller;

import com.westpac.democode_review.model.Project;
import com.westpac.democode_review.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class ProjectController {
    @Autowired
    private
    ProjectService projectService;

    @GetMapping("/projects")
    public ResponseEntity<HashMap<String, String>> getAllProjects () {
        List<Project> projects = new ArrayList<>();
        projects = projectService.getAllProjects();
        HashMap  <String, String> result = new HashMap<>();
        if (projects == null) {
            result.put("message", "No Projects Found");
            result.put("error","true");
            result.put("timestamp", new Date().toString());
            return new ResponseEntity<HashMap<String, String>>(result, HttpStatus.NOT_FOUND);
        }
        result.put("message", "Projects Found");
        result.put("result", projects.toString());
        result.put("error", "false");
        result.put("timestamp", new Date().toString());
        return new ResponseEntity<HashMap<String, String>>(result, HttpStatus.OK);
    }

    @PostMapping("/projects")
    public ResponseEntity<HashMap<String, String>> addProject(@RequestBody Project project) {
        project.setIsStarted(true);
        project.setProposedAt(new Date());
        HashMap  <String, String> result = new HashMap<>();
        result.put("result", projectService.addOrUpdateProject(project).toString());
        result.put("error", "false");
        result.put("timestamp", new Date().toString());
        return new ResponseEntity<HashMap<String, String>>(result, HttpStatus.OK);
    }

    @PatchMapping("/projects/{projectId}/deploy")
    public ResponseEntity<HashMap<String, String>> updateLastDeployed(@PathVariable String projectId) {
        Project project = projectService.getProjectById(projectId);
        HashMap  <String, String> result = new HashMap<>();

        if(project == null) {
            result.put("error", "true");
            result.put("message", "No Projects Found");
            result.put("timestamp", new Date().toString());
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);

        }
        project.setLastDeployed(new Date());
        project = projectService.addOrUpdateProject(project);
        result.put("result", project.toString());
        result.put("error", "false");
        result.put("timestamp", new Date().toString());
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
    @PutMapping("/projects/{projectId}")
    public ResponseEntity<HashMap<String, String>> updateProject(@PathVariable String projectId, @RequestBody Project updatedProject) {
        Project project = projectService.getProjectById(projectId);
        HashMap  <String, String> result = new HashMap<>();

        if(project == null) {
            result.put("error", "true");
            result.put("message", "No Projects Found");
            result.put("timestamp", new Date().toString());
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        project.setProposedAt(updatedProject.getProposedAt());
        project.setIsStarted(updatedProject.getIsStarted());
        project.setLastDeployed(updatedProject.getLastDeployed());
        project.setBudget(updatedProject.getBudget());
        project.setClient(updatedProject.getClient());
        project.setProjectName(updatedProject.getProjectName());
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<HashMap<String, String>> deleteProject(@PathVariable String projectId) {
        Project project = projectService.getProjectById(projectId);
        HashMap  <String, String> result = new HashMap<>();

        if(project == null) {
            result.put("error", "true");
            result.put("message", "No Projects Found");
            result.put("timestamp", new Date().toString());
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        projectService.deleteProject(projectId);
        result.put("error", "false");
        result.put("message", "Project Deleted");
        result.put("timestamp", new Date().toString());
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
}
