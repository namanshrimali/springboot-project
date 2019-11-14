package com.westpac.democode_review.repository;

import com.westpac.democode_review.model.Project;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>{

}
