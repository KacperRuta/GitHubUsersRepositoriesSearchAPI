package com.github.api.githubapi.service;

import com.github.api.githubapi.model.GithubBranch;
import com.github.api.githubapi.model.GithubRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GithubService {

    @Value("${github.api.url}")
    private String githubApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<GithubRepository> getReposByUsername(String username) {
        String url = String.format("%s/users/%s/repos", githubApiUrl, username);
        try {
            GithubRepository[] repos = restTemplate.getForObject(url, GithubRepository[].class);
            if (repos == null) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found");
            }

            return Arrays.stream(repos)
                    .filter(repo -> !repo.isFork()).map(repo -> {
                        List<GithubBranch> branches = getBranches(username, repo.getName());
                        repo.setBranches(branches);
                        return repo;
                    })
                    .collect(Collectors.toList());

        } catch (HttpClientErrorException e) {
            throw new RuntimeException(e.getStatusText());
        }
    }

    public List<GithubBranch> getBranches(String username, String repoName) {
        String url = String.format("%s/repos/%s/%s/branches", githubApiUrl, username, repoName);
        try {
            GithubBranch[] branches = restTemplate.getForObject(url, GithubBranch[].class);
            return branches != null ? Arrays.asList(branches) : List.of();
        } catch (HttpClientErrorException e) {
            return List.of();
        }
    }
}
