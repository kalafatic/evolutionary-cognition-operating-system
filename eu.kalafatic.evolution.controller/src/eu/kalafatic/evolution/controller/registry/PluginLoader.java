package eu.kalafatic.evolution.controller.registry;

import java.util.Map;
import eu.kalafatic.evolution.controller.orchestration.llm.OllamaProvider;
import eu.kalafatic.evolution.controller.orchestration.llm.OpenAIProvider;
import eu.kalafatic.evolution.controller.orchestration.llm.GeminiProvider;
import eu.kalafatic.evolution.controller.orchestration.llm.ILlmProvider;
import eu.kalafatic.evolution.controller.tools.*;
import eu.kalafatic.evolution.controller.vcs.GitVersionControlProvider;
import eu.kalafatic.evolution.controller.vcs.IRepositoryProvider;
import eu.kalafatic.evolution.controller.agents.*;
import eu.kalafatic.evolution.controller.orchestration.WebSearchAgent;
import java.util.HashMap;

/**
 * Handles the initial discovery and loading of built-in and external plugins.
 */
public class PluginLoader {

    /**
     * Bootstraps the registry with default implementations.
     */
    public void loadDefaults() {
        ComponentRegistry registry = ComponentRegistry.getInstance();

        // 1. LLM Providers
        registry.register(new PluginDescriptor("llm.ollama", "1.0", ILlmProvider.class, Map.of("provider", "ollama"), 10), new OllamaProvider());
        registry.register(new PluginDescriptor("llm.openai", "1.0", ILlmProvider.class, Map.of("provider", "openai"), 5), new OpenAIProvider());
        registry.register(new PluginDescriptor("llm.gemini", "1.0", ILlmProvider.class, Map.of("provider", "gemini"), 5), new GeminiProvider());

        // 2. Tools
        registry.register(new PluginDescriptor("tool.file", "1.0", ITool.class, Map.of("name", "file"), 0), new FileTool());
        registry.register(new PluginDescriptor("tool.git", "1.0", ITool.class, Map.of("name", "git"), 0), new GitTool());
        registry.register(new PluginDescriptor("tool.maven", "1.0", ITool.class, Map.of("name", "maven"), 0), new MavenTool());
        registry.register(new PluginDescriptor("tool.shell", "1.0", ITool.class, Map.of("name", "shell"), 0), new ShellTool());

        // 3. Repository
        registry.register(new PluginDescriptor("repo.git", "1.0", IRepositoryProvider.class, Map.of("type", "git"), 0), new GitVersionControlProvider());

        // 4. Agents (Registered as classes to ensure fresh instantiation per session for isolation)
        registry.register(new PluginDescriptor("agent.analytic", "1.0", IAgent.class, Map.of("type", "Analytic"), 0), AnalyticAgent.class);
        registry.register(new PluginDescriptor("agent.validator", "1.0", IAgent.class, Map.of("type", "Validator"), 0), ValidatorAgent.class);
        registry.register(new PluginDescriptor("agent.repair", "1.0", IAgent.class, Map.of("type", "Repair"), 0), RepairAgent.class);
        registry.register(new PluginDescriptor("agent.reviewer", "1.0", IAgent.class, Map.of("type", "Reviewer"), 0), ReviewerAgent.class);
        registry.register(new PluginDescriptor("agent.constraint", "1.0", IAgent.class, Map.of("type", "Constraint"), 0), ConstraintAgent.class);
        registry.register(new PluginDescriptor("agent.architect", "1.0", IAgent.class, Map.of("type", "Architect"), 0), ArchitectAgent.class);
        registry.register(new PluginDescriptor("agent.javadev", "1.0", IAgent.class, Map.of("type", "JavaDev"), 0), JavaDevAgent.class);
        registry.register(new PluginDescriptor("agent.tester", "1.0", IAgent.class, Map.of("type", "Tester"), 0), TesterAgent.class);
        registry.register(new PluginDescriptor("agent.general", "1.0", IAgent.class, Map.of("type", "General"), 0), GeneralAgent.class);
        registry.register(new PluginDescriptor("agent.terminal", "1.0", IAgent.class, Map.of("type", "Terminal"), 0), TerminalAgent.class);
        registry.register(new PluginDescriptor("agent.file", "1.0", IAgent.class, Map.of("type", "File"), 0), FileAgent.class);
        registry.register(new PluginDescriptor("agent.maven", "1.0", IAgent.class, Map.of("type", "Maven"), 0), MavenAgent.class);
        registry.register(new PluginDescriptor("agent.git", "1.0", IAgent.class, Map.of("type", "Git"), 0), GitAgent.class);
        registry.register(new PluginDescriptor("agent.structure", "1.0", IAgent.class, Map.of("type", "Structure"), 0), StructureAgent.class);
        registry.register(new PluginDescriptor("agent.websearch", "1.0", IAgent.class, Map.of("type", "WebSearch"), 0), WebSearchAgent.class);
        registry.register(new PluginDescriptor("agent.quality", "1.0", IAgent.class, Map.of("type", "Quality"), 0), QualityAgent.class);
        registry.register(new PluginDescriptor("agent.observability", "1.0", IAgent.class, Map.of("type", "Observability"), 0), ObservabilityAgent.class);
        registry.register(new PluginDescriptor("agent.planner", "1.0", IAgent.class, Map.of("type", "Planner"), 0), PlannerAgent.class);
        registry.register(new PluginDescriptor("agent.proposalconsolidator", "1.0", IAgent.class, Map.of("type", "ProposalConsolidator"), 0), ProposalConsolidatorAgent.class);
        registry.register(new PluginDescriptor("agent.critic", "1.0", IAgent.class, Map.of("type", "Critic"), 0), CriticAgent.class);
        registry.register(new PluginDescriptor("agent.finalresponse", "1.0", IAgent.class, Map.of("type", "FinalResponse"), 0), FinalResponseAgent.class);
    }
}
