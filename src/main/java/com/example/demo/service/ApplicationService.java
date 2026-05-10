package com.example.demo.service;

import com.example.demo.model.Application;
import com.example.demo.model.Job;
import com.example.demo.model.User;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.repository.JobRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    /**
     * Handles the logic for a freelancer applying to a specific job.
     * Includes validation for existence and duplicate prevention.
     */
    @Transactional
    public Application applyToJob(Long jobId, Long freelancerId, String coverLetter) {

        // 1. Validation: Prevent duplicate applications for the same job
        if (applicationRepository.existsByJobIdAndFreelancerId(jobId, freelancerId)) {
            throw new RuntimeException("You have already submitted an application for this job!");
        }

        // 2. Fetch Job details from database
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Target Job not found with ID: " + jobId));

        // 3. Fetch Freelancer details from database
        User freelancer = userRepository.findById(freelancerId)
                .orElseThrow(() -> new RuntimeException("Freelancer not found with ID: " + freelancerId));

        // 4. Create new Application instance
        Application application = new Application();
        application.setJob(job);
        application.setFreelancer(freelancer);
        application.setCoverLetter(coverLetter);
        application.setStatus("PENDING");

        // 5. Persist to database
        return applicationRepository.save(application);
    }

    /**
     * Fetches the application history for a specific user.
     */
    public List<Application> getApplicationsByFreelancer(Long freelancerId) {
        return applicationRepository.findByFreelancerId(freelancerId);
    }
}