package com.example.demo.controller;

import com.example.demo.model.Job;
import com.example.demo.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "http://localhost:4200")
public class
JobController {

    @Autowired
    private JobRepository jobRepository;

    // අලුත් Job එකක් Save කිරීමට
    @PostMapping("/create")
    public Job createJob(@RequestBody Job job) {
        return jobRepository.save(job);
    }

    // තියෙන ඔක්කොම Jobs බැලීමට
    @GetMapping("/all")
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
}