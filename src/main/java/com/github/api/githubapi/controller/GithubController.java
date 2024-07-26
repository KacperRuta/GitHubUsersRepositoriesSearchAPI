package com.github.api.githubapi.controller;

import com.github.api.githubapi.model.ErrorResponse;
import com.github.api.githubapi.model.GithubRepository;
import com.github.api.githubapi.service.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/github")
public class GithubController {

    @Autowired
    private GithubService githubService;

    @GetMapping("/repos/{username}")
    public ResponseEntity<?> getRepos(@PathVariable String username) {
        try {
            List<GithubRepository> repositories = githubService.getReposByUsername(username);
            return ResponseEntity.ok(repositories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }
}
