package com.example.demo.controller;

import com.example.demo.model.Application;
import com.example.demo.service.ApplicationService;
import com.example.demo.repository.ApplicationRepository; // repository එක direct පාවිච්චි කරනවා නම්
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") // frontend access තහවුරු කිරීම
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ApplicationRepository applicationRepository;

    /**
     * POST endpoint to apply for a job
     */
    @PostMapping("/apply")
    public ResponseEntity<?> applyToJob(@RequestBody Map<String, Object> payload) {
        try {
            Long jobId = Long.valueOf(payload.get("jobId").toString());
            Long freelancerId = Long.valueOf(payload.get("freelancerId").toString());
            String coverLetter = payload.get("coverLetter").toString();

            Application result = applicationService.applyToJob(jobId, freelancerId, coverLetter);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    /**
     * GET endpoint for freelancers to see their applications
     */
    @GetMapping("/freelancer/{id}")
    public ResponseEntity<List<Application>> getMyApplications(@PathVariable Long id) {
        List<Application> myApps = applicationService.getApplicationsByFreelancer(id);
        return ResponseEntity.ok(myApps);
    }

    /**
     * CRITICAL FIX: GET endpoint for clients to see applications for a specific job.
     * Path must exactly match: /api/applications/job/{jobId}
     */
    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<Application>> getApplicationsByJob(@PathVariable Long jobId) {
        // Repository එකෙන් කෙලින්ම data ගමු service එකේ අවුලක් ඇත්දැයි බැලීමට
        List<Application> jobApps = applicationRepository.findByJobId(jobId);
        return ResponseEntity.ok(jobApps);
    }

    /**
     * PUT endpoint to update application status
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<Application> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> statusRequest) {
        String newStatus = statusRequest.get("status");
        return ResponseEntity.ok(applicationService.updateApplicationStatus(id, newStatus));
    }
}