package eu.kalafatic.evolution.controller.agents;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import eu.kalafatic.evolution.controller.orchestration.WebSearchAgent;
import eu.kalafatic.evolution.controller.orchestration.SessionContainer;
import eu.kalafatic.evolution.controller.registry.ComponentRegistry;

/**
 * Factory for AI Agents.
 */
public class AgentFactory {

    /**
     * Creates new instances of all default agents for session-specific isolation.
     */
    public static List<IAgent> createIsolatedAgents(SessionContainer container) {
        List<IAgent> isolated = new ArrayList<>();

        // Load from registry
        ComponentRegistry registry = ComponentRegistry.getInstance();
        List<IAgent> registered = registry.findPlugins(IAgent.class);

        for (IAgent agent : registered) {
            agent.setSessionContainer(container);
            isolated.add(agent);
        }

        return isolated;
    }

    /**
     * @deprecated Use createIsolatedAgents(SessionContainer) for strict session isolation.
     */
    @Deprecated
    public static List<IAgent> getAllAgents() {
        return new ArrayList<>();
    }
}
