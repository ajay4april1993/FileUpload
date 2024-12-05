package com.example.git.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.git.demo.service.GitService;
@RestController
@RequestMapping("/git")
public class GitController {

    private final GitService gitService;

    public GitController(GitService gitService) {
        this.gitService = gitService;
    }

    @PostMapping("/clone")
    public ResponseEntity<String> cloneRepository() {
        try {
            gitService.cloneRepository();
            return ResponseEntity.ok("Repository cloned successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error cloning repository: " + e.getMessage());
        }
    }

    @PostMapping("/pull")
    public ResponseEntity<String> pullChanges() {
        try {
            gitService.pullChanges();
            return ResponseEntity.ok("Repository pulled successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error pulling changes: " + e.getMessage());
        }
    }

    @PostMapping("/push")
    public ResponseEntity<String> pushChanges(@RequestParam String commitMessage) {
        try {
            gitService.pushChanges(commitMessage);
            return ResponseEntity.ok("Changes pushed successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error pushing changes: " + e.getMessage());
        }
    }
}

