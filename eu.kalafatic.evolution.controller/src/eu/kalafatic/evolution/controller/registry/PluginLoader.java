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

        // 4. Agents (Note: These often need SessionContainer, so we register factory-like descriptors or generic instances if possible)
        // For now, we register them as available types.
        registry.register(new PluginDescriptor("agent.analytic", "1.0", IAgent.class, Map.of("type", "Analytic"), 0), new AnalyticAgent(null));
        registry.register(new PluginDescriptor("agent.validator", "1.0", IAgent.class, Map.of("type", "Validator"), 0), new ValidatorAgent(null));
        registry.register(new PluginDescriptor("agent.repair", "1.0", IAgent.class, Map.of("type", "Repair"), 0), new RepairAgent(null));
    }
}
