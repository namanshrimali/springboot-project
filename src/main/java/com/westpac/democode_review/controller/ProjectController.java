package com.westpac.democode_review.controller;

import com.westpac.democode_review.exception.ProjectNotFoundException;
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
    public ResponseEntity<HashMap<String, String>> getAllProjects () throws ProjectNotFoundException {
        List<Project> projects = new ArrayList<>();
        projects = projectService.getAllProjects();
        HashMap  <String, String> result = new HashMap<>();
        if (projects == null) {
            throw new ProjectNotFoundException();
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
    public ResponseEntity<HashMap<String, String>> updateLastDeployed(@PathVariable int projectId) throws ProjectNotFoundException{
        Project project = projectService.getProjectById(projectId);
        HashMap  <String, String> result = new HashMap<>();

        if(project == null) {
            throw new ProjectNotFoundException(projectId);
        }
        project.setLastDeployed(new Date());
        project = projectService.addOrUpdateProject(project);
        result.put("result", project.toString());
        result.put("error", "false");
        result.put("timestamp", new Date().toString());
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
    @PutMapping("/projects/{projectId}")
    public ResponseEntity<HashMap<String, String>> updateProject(@PathVariable int projectId, @RequestBody Project updatedProject) throws ProjectNotFoundException{
        Project project = projectService.getProjectById(projectId);
        HashMap  <String, String> result = new HashMap<>();

        if(project == null) {
            throw new ProjectNotFoundException(projectId);
        }
        project.setProposedAt(updatedProject.getProposedAt());
        project.setIsStarted(updatedProject.getIsStarted());
        project.setLastDeployed(updatedProject.getLastDeployed());
        project.setBudget(updatedProject.getBudget());
        project.setClient(updatedProject.getClient());
        project.setName(updatedProject.getName());
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<HashMap<String, String>> deleteProject(@PathVariable int projectId) throws ProjectNotFoundException{
        Project project = projectService.getProjectById(projectId);
        HashMap  <String, String> result = new HashMap<>();

        if(project == null) {
            throw new ProjectNotFoundException(projectId);
        }
        projectService.deleteProject(projectId);
        result.put("error", "false");
        result.put("message", "Project Deleted");
        result.put("timestamp", new Date().toString());
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
}
