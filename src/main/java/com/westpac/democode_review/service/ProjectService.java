package com.westpac.democode_review.service;

import com.westpac.democode_review.model.Project;

import java.util.List;

public interface ProjectService {
    public List<Project> getAllProjects();
    public Project getProjectById(String id);
    public Project addOrUpdateProject(Project project);
}
