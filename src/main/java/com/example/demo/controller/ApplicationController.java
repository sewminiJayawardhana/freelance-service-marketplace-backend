package com.example.demo.controller;

import com.example.demo.model.Application;
import com.example.demo.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") // Allow frontend requests
public class ApplicationController {

    private final ApplicationService applicationService;

    /**
     * POST endpoint to process a job application.
     * Expects jobId, freelancerId, and coverLetter in the JSON body.
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
            // Return the specific error message (e.g., "Already applied") to the frontend
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    /**
     * GET endpoint to retrieve applications for the currently logged-in freelancer.
     */
    @GetMapping("/freelancer/{id}")
    public ResponseEntity<List<Application>> getMyApplications(@PathVariable Long id) {
        List<Application> myApps = applicationService.getApplicationsByFreelancer(id);
        return ResponseEntity.ok(myApps);
    }
}