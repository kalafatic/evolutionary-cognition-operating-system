package eu.kalafatic.evolution.controller.vcs;

import java.io.File;
import java.util.List;

/**
 * Interface for repository providers.
 */
public interface IRepositoryProvider {
    void commit(File root, String message) throws Exception;
    String diff(File root, String branch1, String branch2) throws Exception;
    void createBranch(File root, String name) throws Exception;
    List<String> getBranches(File root) throws Exception;

    // Legacy support for PeerReviewService
    String getDiff(File workingDir, String commitId) throws Exception;
    String getFileDiff(File workingDir, String commitId, String filePath) throws Exception;
    List<String> getChangedFiles(File workingDir, String commitId) throws Exception;
    void commitChanges(File workingDir, String message) throws Exception;
    void push(File workingDir) throws Exception;
}
