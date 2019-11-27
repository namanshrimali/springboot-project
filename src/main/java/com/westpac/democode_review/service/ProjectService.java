package com.westpac.democode_review.service;

import com.westpac.democode_review.model.Project;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface ProjectService {
    public List<Project> getAllProjects(Pageable pageable);
    public Optional<Project> getProjectById(int id);
    public Project addOrUpdateProject(Project project);
    public void deleteProject(int id);
}
