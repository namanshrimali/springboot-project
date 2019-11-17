package com.westpac.democode_review.service;

import com.westpac.democode_review.model.Project;
import com.westpac.democode_review.repository.ProjectRepository;
import com.westpac.democode_review.service.impl.ProjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProjectServiceMockTest {
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService = new ProjectServiceImpl();

    private Pageable pageable= PageRequest.of(0, 5);
    private int randomId;
    private Project project;
    @BeforeEach
    void getAllProjectsOutput() {
        List<Project> projects= new ArrayList<>(5);
        project = new Project();
        project.setId((int)(Math.random()*10));
        Page<Project> page = new PageImpl<>(projects);
        randomId = (int)(Math.random()*10);
        Optional<Project> optionalProject = Optional.of(project);
        when(projectRepository.findAll(pageable)).thenReturn(page);
        when(projectRepository.findById(randomId)).thenReturn(optionalProject);
        when(projectRepository.save(project)).thenReturn(project);
    }


    @Test
    void getAllProjects() {
        assertTrue(projectService.getAllProjects(pageable).size() <= 5);
    }

    @Test
    void getProjectById() {
        assertTrue(projectService.getProjectById(randomId).isPresent(), "If found, returned object must be of type Project");
    }

    @Test
    void addOrUpdateProject() {
        assertEquals("class com.westpac.democode_review.model.Project", ""+ projectService.addOrUpdateProject(project).getClass());
    }
}
