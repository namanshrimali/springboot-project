package com.westpac.democode_review.service.impl;

import com.westpac.democode_review.model.Project;
import com.westpac.democode_review.repository.ProjectRepository;
import com.westpac.democode_review.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectRepository projectRepository;
    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(int id) {
        return projectRepository.findById(id).get();
    }

    @Override
    public Project addOrUpdateProject(Project project) {
        return projectRepository.save(project);
    }

    public void deleteProject(int id) {
        projectRepository.deleteById(id);
    }
}
