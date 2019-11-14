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
        HashMap  <String, String> result = new HashMap<>();
        try {
            projects = projectService.getAllProjects();
        }  catch (Exception exception) {
            throw new ProjectNotFoundException();
        }
        result.put("message", "Projects Found");
        result.put("result", projects.toString());
        result.put("error", "false");
        result.put("timestamp", new Date().toString());
        return new ResponseEntity<HashMap<String, String>>(result, HttpStatus.OK);
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<HashMap<String, String>> getProjectById(@PathVariable int projectId) throws ProjectNotFoundException{
        Project project;
        HashMap  <String, String> result = new HashMap<>();
        try {
            project = projectService.getProjectById(projectId);
        } catch (Exception exception) {
            throw new ProjectNotFoundException(projectId);
        }

        result.put("message", "Projects Found");
        result.put("result", project.toString());
        result.put("error", "false");
        result.put("timestamp", new Date().toString());
        return new ResponseEntity<>(result, HttpStatus.FOUND);
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
        Project project;
        HashMap  <String, String> result = new HashMap<>();
        try {
            project = projectService.getProjectById(projectId);

        } catch (Exception exception) {
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
        Project project;
        HashMap  <String, String> result = new HashMap<>();
        try {
            project = projectService.getProjectById(projectId);
        } catch (Exception exception) {
            throw new ProjectNotFoundException(projectId);
        }

        project.setProposedAt(updatedProject.getProposedAt());
        project.setIsStarted(updatedProject.getIsStarted());
        project.setLastDeployed(updatedProject.getLastDeployed());
        project.setBudget(updatedProject.getBudget());
        project.setClient(updatedProject.getClient());
        project.setName(updatedProject.getName());

        result.put("message", "Projects Found");
        result.put("result", project.toString());
        result.put("error", "false");
        result.put("timestamp", new Date().toString());
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<HashMap<String, String>> deleteProject(@PathVariable int projectId) throws ProjectNotFoundException{

        HashMap  <String, String> result = new HashMap<>();

       try {
           Project project = projectService.getProjectById(projectId);

       } catch (Exception exception) {
           throw new ProjectNotFoundException(projectId);
           // log here
       }
        projectService.deleteProject(projectId);
        result.put("error", "false");
        result.put("message", "Project Deleted");
        result.put("timestamp", new Date().toString());
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
}
