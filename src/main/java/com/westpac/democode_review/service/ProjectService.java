package com.westpac.democode_review.service;

import com.westpac.democode_review.model.Project;
import org.springframework.stereotype.Service;

import java.util.List;
public interface ProjectService {
    public List<Project> getAllProjects();
    public Project getProjectById(int id);
    public Project addOrUpdateProject(Project project);
    public void deleteProject(int id);
}
