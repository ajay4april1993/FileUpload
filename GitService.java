package com.example.git.demo.service;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;

@Service
public class GitService {

    private static final String LOCAL_REPO_PATH = "local-repo"; // Path to local repository
    private static final String REMOTE_REPO_URL = "https://github.com/ajay4april1993/FileUpload.git"; // Remote Git repository URL
    private static final String GIT_USERNAME = "ajay4april1993"; // Your Git username
    private static final String GIT_PASSWORD = "Dugdug@123"; // Your Git personal access token or password

    /**
     * Clone the repository if it doesn't already exist locally.
     */
    private static final String DOWNLOAD_FILE="https://github.com/ajay4april1993/FileUpload/blob/main/TestFile";
    public void cloneRepository() throws GitAPIException {
        File repoDir = new File(LOCAL_REPO_PATH);

        if (!repoDir.exists()) {
            org.eclipse.jgit.api.Git.cloneRepository()
                .setURI(REMOTE_REPO_URL)
                .setDirectory(repoDir)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(GIT_USERNAME, GIT_PASSWORD))
                .call();

            System.out.println("Repository cloned successfully!");
        } else {
            System.out.println("Repository already exists locally.");
        }
    }

    /**
     * Pull the latest changes from the remote repository.
     */
    public void pullChanges() throws GitAPIException, IOException {
        File repoDir = new File(DOWNLOAD_FILE);

        if (!repoDir.exists()) {
            throw new IOException("Local repository does not exist. Clone it first.");
        }

        try (org.eclipse.jgit.api.Git git = org.eclipse.jgit.api.Git.open(repoDir)) {
            PullResult result = git.pull()
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(GIT_USERNAME, GIT_PASSWORD))
                .call();

            if (result.isSuccessful()) {
                System.out.println("Repository pulled successfully!");
            } else {
                System.out.println("Pull operation failed!");
            }
        }
    }

    /**
     * Commit and push changes to the remote repository.
     *
     * @param commitMessage Commit message for the changes.
     */
    public void pushChanges(String commitMessage) throws GitAPIException, IOException {
        File repoDir = new File(DOWNLOAD_FILE);

        if (!repoDir.exists()) {
            throw new IOException("Local repository does not exist. Clone it first.");
        }

        try (org.eclipse.jgit.api.Git git = org.eclipse.jgit.api.Git.open(repoDir)) {
            // Add all changes to the index
            git.add()
                .addFilepattern(".")
                .call();

            // Commit the changes
            git.commit()
                .setMessage(commitMessage)
                .call();

            // Push the changes
            git.push()
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(GIT_USERNAME, GIT_PASSWORD))
                .call();

            System.out.println("Changes pushed to remote repository!");
        }
    }
}
