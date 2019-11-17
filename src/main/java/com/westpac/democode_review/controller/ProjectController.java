package com.westpac.democode_review.controller;

import com.westpac.democode_review.exception.ProjectNotFoundException;
import com.westpac.democode_review.model.Project;
import com.westpac.democode_review.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
    @GetMapping("/projects")
    public ResponseEntity<HashMap<String, String>> getAllProjects (@RequestParam(name="page", required = true) int page, @RequestParam(name="limit", required= true) int limit) throws ProjectNotFoundException {
        Pageable pageable= PageRequest.of(page, limit, Sort.by("id").ascending());
        logger.info("Get API '/projects' at timestamp " + new Date());
        List<Project> projects;
        HashMap  <String, String> result = new HashMap<>();
        try {
            projects = projectService.getAllProjects(pageable);
        }  catch (Exception exception) {
            throw new ProjectNotFoundException();
        }
        result.put("message", "Projects Found");
        result.put("result", projects.toString());
        result.put("error", "false");
        result.put("timestamp", new Date().toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<HashMap<String, String>> getProjectById(@PathVariable int projectId) throws ProjectNotFoundException{
        logger.info("Get API '/projects' at timestamp " + new Date());
        Optional<Project> project;
        HashMap  <String, String> result = new HashMap<>();
        project = projectService.getProjectById(projectId);
        if(!project.isPresent()) {
            throw new ProjectNotFoundException(projectId);
        }
        result.put("message", "Projects Found");
        result.put("result", project.get().toString());
        result.put("error", "false");
        result.put("timestamp", new Date().toString());
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    @PostMapping("/projects")
    public ResponseEntity<HashMap<String, String>> addProject(@RequestBody Project project) {
        logger.info("Post API '/projects' at timestamp " + new Date() + " with data " + project.toString());
        project.setIsStarted(true);
        project.setProposedAt(new Date());
        HashMap  <String, String> result = new HashMap<>();
        result.put("result", projectService.addOrUpdateProject(project).toString());
        result.put("error", "false");
        result.put("timestamp", new Date().toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/projects/{projectId}/deploy")
    public ResponseEntity<HashMap<String, String>> updateLastDeployed(@PathVariable int projectId) throws ProjectNotFoundException{
        logger.info("Patch API '/projects/"+projectId+"/deploy' at timestamp " + new Date());
        Optional<Project> project;
        HashMap  <String, String> result = new HashMap<>();
        project = projectService.getProjectById(projectId);

        if(!project.isPresent()) {
            throw new ProjectNotFoundException(projectId);
        }

        project.get().setLastDeployed(new Date());
        projectService.addOrUpdateProject(project.get());
        result.put("result", project.toString());
        result.put("error", "false");
        result.put("timestamp", new Date().toString());
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
    @PutMapping("/projects/{projectId}")
    public ResponseEntity<HashMap<String, String>> updateProject(@PathVariable int projectId, @RequestBody Project updatedProject) throws ProjectNotFoundException{
        logger.info("PUT API '/projects" + projectId + " at timestamp " + new Date() + " with data " + updatedProject.toString());
        Optional<Project> project;
        HashMap  <String, String> result = new HashMap<>();
        project = projectService.getProjectById(projectId);

        if(!project.isPresent()) {
            throw new ProjectNotFoundException(projectId);
        }

        project.get().setProposedAt(updatedProject.getProposedAt());
        project.get().setIsStarted(updatedProject.getIsStarted());
        project.get().setLastDeployed(updatedProject.getLastDeployed());
        project.get().setBudget(updatedProject.getBudget());
        project.get().setClient(updatedProject.getClient());
        project.get().setName(updatedProject.getName());

        result.put("message", "Projects Found");
        result.put("result", project.get().toString());
        result.put("error", "false");
        result.put("timestamp", new Date().toString());
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<HashMap<String, String>> deleteProject(@PathVariable int projectId) throws ProjectNotFoundException{
        logger.info("Delete API '/projects" + projectId + " at timestamp " + new Date());

        Optional<Project> project;
        HashMap  <String, String> result = new HashMap<>();
        project = projectService.getProjectById(projectId);

        if(!project.isPresent()) {
            throw new ProjectNotFoundException(projectId);
        }

        projectService.deleteProject(projectId);
        result.put("error", "false");
        result.put("message", "Project Deleted");
        result.put("timestamp", new Date().toString());
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
}
