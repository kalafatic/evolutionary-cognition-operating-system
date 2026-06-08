package eu.kalafatic.evolution.supervisor.model;

import java.util.List;

public class ExecutionPlan {
    private String id;
    private List<String> steps;
    private String targetBranch;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public List<String> getSteps() { return steps; }
    public void setSteps(List<String> steps) { this.steps = steps; }
    public String getTargetBranch() { return targetBranch; }
    public void setTargetBranch(String targetBranch) { this.targetBranch = targetBranch; }
}
