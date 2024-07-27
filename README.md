# GitHub User's repositories search API

This project is a Spring Boot application that acts as a proxy for the GitHub API. It provides endpoints to fetch information about GitHub repositories and their branches for a given user.

## Features

- Retrieve repositories for a specified GitHub username.
- Filter out forked repositories.
- Fetch branches and their SHA information for each repository.

## Endpoints

### GET `/api/github/repos/{username}`

Fetches repositories for the given GitHub username.

#### Parameters

- `username` (path variable): The GitHub username whose repositories you want to fetch.
- `Accept` (header): Must be set to `application/json`. If not, the API returns a 406 Not Acceptable status.

#### Responses

- **200 OK**: Returns a list of repositories and their branches.
- **404 Not Found**: If the user does not exist or no repositories are found.
- **406 Not Acceptable**: If the `Accept` header is not `application/json`.

## Requirements

- Java 21
- Maven

## Configuration

Set the GitHub API URL in `application.properties`:

```properties
github.api.url=https://api.github.com
