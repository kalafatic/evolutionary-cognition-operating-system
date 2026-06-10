package eu.kalafatic.evolution.controller.agents;

import java.util.ArrayList;
import java.util.List;
import eu.kalafatic.evolution.controller.orchestration.WebSearchAgent;
import eu.kalafatic.evolution.controller.orchestration.SessionContainer;

/**
 * Factory for AI Agents.
 */
public class AgentFactory {

    /**
     * Creates new instances of all default agents for session-specific isolation.
     */
    public static List<IAgent> createIsolatedAgents(SessionContainer container) {
        List<IAgent> isolated = eu.kalafatic.evolution.controller.registry.ComponentRegistry.getInstance().getProviders(IAgent.class);
        for (IAgent agent : isolated) {
            agent.setSessionContainer(container);
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
