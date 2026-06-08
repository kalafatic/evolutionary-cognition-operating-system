package eu.kalafatic.evolution.supervisor.model;

import java.util.Map;

public class ContextSnapshot {
    private Map<String, Object> metadata;
    private String projectStructure;
    private long timestamp;

    public ContextSnapshot() {}

    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }

    public String getProjectStructure() { return projectStructure; }
    public void setProjectStructure(String projectStructure) { this.projectStructure = projectStructure; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
