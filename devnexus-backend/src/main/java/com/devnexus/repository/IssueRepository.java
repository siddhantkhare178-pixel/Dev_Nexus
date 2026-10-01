package com.devnexus.repository;

import com.devnexus.entity.Issue;
import com.devnexus.entity.IssueSeverity;
import com.devnexus.entity.IssueStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, String> {
    Page<Issue> findByProjectId(String projectId, Pageable pageable);
    List<Issue> findByProjectIdAndStatus(String projectId, IssueStatus status);
    List<Issue> findByProjectIdAndSeverity(String projectId, IssueSeverity severity);
    long countByProjectId(String projectId);
}
