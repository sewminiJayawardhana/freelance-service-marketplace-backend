package com.example.demo.repository;

import com.example.demo.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    /**
     * Retrieves all applications submitted by a specific freelancer.
     * Used for the "My Applications" page.
     */
    List<Application> findByFreelancerId(Long freelancerId);

    /**
     * Checks if a freelancer has already applied for a specific job.
     * Used to prevent duplicate submissions.
     */
    boolean existsByJobIdAndFreelancerId(Long jobId, Long freelancerId);

    /**
     * Retrieves all applications for a specific job.
     * This is essential for the Client to see who has applied for their job posting.
     */
    List<Application> findByJobId(Long jobId);
}