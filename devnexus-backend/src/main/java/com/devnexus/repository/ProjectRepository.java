package com.devnexus.repository;

import com.devnexus.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
    Optional<Project> findBySdkApiKey(String sdkApiKey);
    List<Project> findByOrganizationId(String orgId);

    @Query("SELECT p FROM Project p WHERE LOWER(p.githubRepoUrl) = LOWER(:url)")
    Optional<Project> findByGithubRepoUrlIgnoreCase(@Param("url") String url);
}
