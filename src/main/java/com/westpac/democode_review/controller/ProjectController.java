package com.westpac.democode_review.controller;

import com.westpac.democode_review.dto.IncomingMessageDTO;
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
import java.util.*;

@RestController
@RequestMapping("api/v1")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
    private static final String MESSAGE = "message";
    private static final String RESULT = "result";
    private static final String ERROR = "error";
    private static final String TIMESTAMP = "timestamp";

    public static Map<String, Object> generatedResponseForQuery(Project project) {
        HashMap  <String, Object> result = new HashMap<>();
        result.put(MESSAGE, "Projects Found");
        result.put(RESULT, project);
        result.put(ERROR, false);
        result.put(TIMESTAMP, new Date().toString());
        return result;
    }

    @GetMapping("/projects")
    public ResponseEntity<HashMap<String, Object>> getAllProjects (@RequestParam(name="page", required = true) int page, @RequestParam(name="limit", required= true) int limit) throws ProjectNotFoundException {
        Pageable pageable= PageRequest.of(page, limit, Sort.by("id").ascending());
        logger.info("Get API '/projects' at timestamp {}", new Date());
        List<Project> projects;
        HashMap  <String, Object> result = new HashMap<>();
        try {
            projects = projectService.getAllProjects(pageable);
        }  catch (Exception exception) {
            throw new ProjectNotFoundException();
        }
        result.put(MESSAGE, "Projects Found");
        result.put(RESULT, projects.toString());
        result.put(ERROR, false);
        result.put(TIMESTAMP, new Date().toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<Map<String, Object>> getProjectById(@PathVariable int projectId) throws ProjectNotFoundException{
        logger.info("Get API '/projects' at timestamp {}", new Date());
        Optional<Project> project;
        project = projectService.getProjectById(projectId);
        if(!project.isPresent()) {
            throw new ProjectNotFoundException(projectId);
        }
        return new ResponseEntity<>(generatedResponseForQuery(project.get()), HttpStatus.FOUND);
    }

    @PostMapping("/projects")
    public ResponseEntity<Map<String, Object>> addProject(@RequestBody IncomingMessageDTO incomingMessage) {
        logger.info("Post API '/projects' at timestamp {}", new Date());
        Project project = new Project();
        project.setName(incomingMessage.getProjectName());
        project.setBudget(incomingMessage.getProjectBudget());
        project.setClient(incomingMessage.getProjectClient());
        project.setIsStarted(true);
        project.setProposedAt(new Date());
        project = projectService.addOrUpdateProject(project);
        return new ResponseEntity<>(generatedResponseForQuery(project), HttpStatus.OK);
    }

    @PatchMapping("/projects/{projectId}/deploy")
    public ResponseEntity<Map<String, Object>> updateLastDeployed(@PathVariable int projectId) throws ProjectNotFoundException{
        logger.info("Patch API '/projects/{}/deploy' at timestamp {}",projectId,  new Date());
        Optional<Project> project;
        project = projectService.getProjectById(projectId);

        if(!project.isPresent()) {
            throw new ProjectNotFoundException(projectId);
        }
        project.get().setLastDeployed(new Date());
        projectService.addOrUpdateProject(project.get());
        return new ResponseEntity<>(generatedResponseForQuery(project.get()), HttpStatus.ACCEPTED);
    }
    @PutMapping("/projects/{projectId}")
    public ResponseEntity<Map<String, Object>> updateProject(@PathVariable int projectId, @RequestBody IncomingMessageDTO updatedProject) throws ProjectNotFoundException{
        logger.info("PUT api /projects/{} at timestamp {}", projectId, new Date());
        Optional<Project> project;
        project = projectService.getProjectById(projectId);

        if(!project.isPresent()) {
            throw new ProjectNotFoundException(projectId);
        }

        project.get().setProposedAt(updatedProject.getProjectProposedAt());
        project.get().setIsStarted(updatedProject.getIsProjectStarted());
        project.get().setLastDeployed(updatedProject.getProjectLastDeployed());
        project.get().setBudget(updatedProject.getProjectBudget());
        project.get().setClient(updatedProject.getProjectClient());
        project.get().setName(updatedProject.getProjectName());
        return new ResponseEntity<>(generatedResponseForQuery(project.get()), HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<HashMap<String, Object>> deleteProject(@PathVariable int projectId) throws ProjectNotFoundException{
        logger.info("Delete API '/projects/{} at timestamp {}", projectId,new Date());

        Optional<Project> project;
        HashMap  <String, Object> result = new HashMap<>();
        project = projectService.getProjectById(projectId);

        if(!project.isPresent()) {
            throw new ProjectNotFoundException(projectId);
        }

        projectService.deleteProject(projectId);
        result.put(ERROR, false);
        result.put(MESSAGE, "Project Deleted");
        result.put(TIMESTAMP, new Date().toString());
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
}
