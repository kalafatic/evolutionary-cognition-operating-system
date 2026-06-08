package eu.kalafatic.evolution.controller.kernel;

import eu.kalafatic.evolution.controller.orchestration.TaskContext;

public class DefaultGitEvolutionAdapter implements GitEvolutionAdapter {

    public DefaultGitEvolutionAdapter() {
    }

    @Override
    public boolean isGitRepository() {
        return false;
    }

    @Override
    public void ensureInitialCommit() throws Exception {
    }

    @Override
    public String getCurrentBranch() throws Exception {
        return "main";
    }

    @Override
    public String getHeadCommit() throws Exception {
        return "";
    }

    @Override
    public void forceCheckout(String branchName) throws Exception {
    }

    @Override
    public void merge(String branchName) throws Exception {
    }

    @Override
    public void commit(String message, TaskContext context) throws Exception {
    }

    @Override
    public void rollback() throws Exception {
    }
}
