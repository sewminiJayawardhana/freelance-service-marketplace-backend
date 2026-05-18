package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "jobs")
@Data
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Double budget;
    private String category;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private User user; // මේකෙන් Job එක දාපු User ව සම්බන්ධ කරනවා

    // field to store job status (OPEN/CLOSED)
    private String status = "OPEN"; // Setting default value as OPEN
}