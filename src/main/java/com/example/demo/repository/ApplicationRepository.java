package com.example.demo.repository;

import com.example.demo.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    /**
     * Retrieves all applications submitted by a specific freelancer
     */
    List<Application> findByFreelancerId(Long freelancerId);

    boolean existsByJobIdAndFreelancerId(Long jobId, Long freelancerId);
}