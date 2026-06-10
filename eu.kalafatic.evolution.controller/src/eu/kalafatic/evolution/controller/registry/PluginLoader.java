package eu.kalafatic.evolution.controller.registry;

import java.util.Map;
import eu.kalafatic.evolution.controller.orchestration.llm.OllamaProvider;
import eu.kalafatic.evolution.controller.orchestration.llm.OpenAIProvider;
import eu.kalafatic.evolution.controller.orchestration.llm.GeminiProvider;
import eu.kalafatic.evolution.controller.orchestration.llm.ILlmProvider;
import eu.kalafatic.evolution.controller.tools.*;
import eu.kalafatic.evolution.controller.vcs.GitVersionControlProvider;
import eu.kalafatic.evolution.controller.vcs.IRepositoryProvider;
import eu.kalafatic.evolution.controller.orchestration.selfdev.IterationMemoryService;
import eu.kalafatic.evolution.controller.orchestration.selfdev.IMemoryProvider;
import eu.kalafatic.evolution.controller.orchestration.selfdev.DarwinEngine;
import eu.kalafatic.evolution.controller.orchestration.selfdev.IEvolutionEngine;
import eu.kalafatic.evolution.controller.agents.*;
import eu.kalafatic.evolution.controller.orchestration.WebSearchAgent;

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
        registry.register(new PluginDescriptor("tool.cpp", "1.0", ITool.class, Map.of("name", "cpp"), 0), new CppTool());
        registry.register(new PluginDescriptor("tool.database", "1.0", ITool.class, Map.of("name", "database"), 0), new DatabaseTool());
        registry.register(new PluginDescriptor("tool.eclipse", "1.0", ITool.class, Map.of("name", "eclipse"), 0), new EclipseTool());
        registry.register(new PluginDescriptor("tool.aicontext", "1.0", ITool.class, Map.of("name", "aicontext"), 0), new eu.kalafatic.utils.semantic.AIContextTool());

        // 3. Repository
        registry.register(new PluginDescriptor("repo.git", "1.0", IRepositoryProvider.class, Map.of("type", "git"), 0), new GitVersionControlProvider());

        // 4. Memory
        registry.register(new PluginDescriptor("memory.default", "1.0", IMemoryProvider.class, Map.of("type", "default"), 0), IterationMemoryService.class);

        // 5. Evolution
        registry.register(new PluginDescriptor("engine.darwin", "1.0", IEvolutionEngine.class, Map.of("type", "darwin"), 0), DarwinEngine.class);

        // 6. Kernel Engines
        registry.register(new PluginDescriptor("kernel.phase", "1.0", eu.kalafatic.evolution.controller.kernel.IPhaseEngine.class, Map.of("type", "default"), 0), eu.kalafatic.evolution.controller.kernel.DefaultPhaseEngine.class);
        registry.register(new PluginDescriptor("kernel.branch", "1.0", eu.kalafatic.evolution.controller.kernel.IBranchManager.class, Map.of("type", "default"), 0), eu.kalafatic.evolution.controller.kernel.DefaultBranchManager.class);
        registry.register(new PluginDescriptor("kernel.mutation", "1.0", eu.kalafatic.evolution.controller.kernel.IMutationEngine.class, Map.of("type", "default"), 0), eu.kalafatic.evolution.controller.kernel.DefaultMutationEngine.class);
        registry.register(new PluginDescriptor("kernel.fitness", "1.0", eu.kalafatic.evolution.controller.kernel.IFitnessEngine.class, Map.of("type", "default"), 0), eu.kalafatic.evolution.controller.kernel.DefaultFitnessEngine.class);
        registry.register(new PluginDescriptor("kernel.reality", "1.0", eu.kalafatic.evolution.controller.kernel.IRealityEngine.class, Map.of("type", "default"), 0), eu.kalafatic.evolution.controller.kernel.DefaultRealityEngine.class);
        registry.register(new PluginDescriptor("kernel.authority", "1.0", eu.kalafatic.evolution.controller.kernel.IAuthorityEngine.class, Map.of("type", "default"), 0), eu.kalafatic.evolution.controller.kernel.DefaultAuthorityEngine.class);
        registry.register(new PluginDescriptor("kernel.trajectory", "1.0", eu.kalafatic.evolution.controller.kernel.ITrajectoryEngine.class, Map.of("type", "default"), 0), eu.kalafatic.evolution.controller.kernel.DefaultTrajectoryEngine.class);
        registry.register(new PluginDescriptor("kernel.gitadapter", "1.0", eu.kalafatic.evolution.controller.kernel.IGitEvolutionAdapter.class, Map.of("type", "default"), 0), eu.kalafatic.evolution.controller.kernel.DefaultGitEvolutionAdapter.class);

        // 7. Intent & Mediation
        registry.register(new PluginDescriptor("intent.expansion", "1.0", eu.kalafatic.evolution.controller.orchestration.intent.IIntentExpansionEngine.class, Map.of("type", "default"), 0), eu.kalafatic.evolution.controller.orchestration.intent.IntentExpansionEngine.class);
        registry.register(new PluginDescriptor("intent.dimension", "1.0", eu.kalafatic.evolution.controller.orchestration.intent.IDimensionInferenceEngine.class, Map.of("type", "default"), 0), eu.kalafatic.evolution.controller.orchestration.intent.DefaultDimensionInferenceEngine.class);
        registry.register(new PluginDescriptor("intent.clarification_manager", "1.0", eu.kalafatic.evolution.controller.orchestration.intent.IClarificationManager.class, Map.of("type", "default"), 0), eu.kalafatic.evolution.controller.orchestration.intent.ClarificationManager.class);
        registry.register(new PluginDescriptor("intent.clarification_planner", "1.0", eu.kalafatic.evolution.controller.orchestration.intent.IClarificationPlanner.class, Map.of("type", "default"), 0), eu.kalafatic.evolution.controller.orchestration.intent.ClarificationPlanner.class);
        registry.register(new PluginDescriptor("mediation.scanner", "1.0", eu.kalafatic.evolution.controller.mediation.scanner.TargetScanner.class, Map.of("type", "default"), 0), eu.kalafatic.evolution.controller.mediation.scanner.TargetScanner.class);
        registry.register(new PluginDescriptor("mediation.curator", "1.0", eu.kalafatic.evolution.controller.mediation.analysis.ContextCurator.class, Map.of("type", "default"), 0), eu.kalafatic.evolution.controller.mediation.analysis.ContextCurator.class);
        registry.register(new PluginDescriptor("mediation.extractor", "1.0", eu.kalafatic.evolution.controller.mediation.analysis.SemanticExtractor.class, Map.of("type", "default"), 0), eu.kalafatic.evolution.controller.mediation.analysis.SemanticExtractor.class);
        registry.register(new PluginDescriptor("mediation.export", "1.0", eu.kalafatic.evolution.controller.workflow.MediatedExportManager.class, Map.of("type", "default"), 0), eu.kalafatic.evolution.controller.workflow.MediatedExportManager.class);
        registry.register(new PluginDescriptor("mediation.synthesizer", "1.0", eu.kalafatic.evolution.controller.mediation.analysis.PromptSynthesizer.class, Map.of("type", "default"), 0), eu.kalafatic.evolution.controller.mediation.analysis.PromptSynthesizer.class);
        registry.register(new PluginDescriptor("kernel.replay", "1.0", eu.kalafatic.evolution.controller.orchestration.diagnostics.ReplayEngine.class, Map.of("type", "default"), 0), eu.kalafatic.evolution.controller.orchestration.diagnostics.ReplayEngine.class);
        registry.register(new PluginDescriptor("kernel.assembler", "1.0", eu.kalafatic.evolution.controller.orchestration.FinalResponseAssembler.class, Map.of("type", "default"), 0), eu.kalafatic.evolution.controller.orchestration.FinalResponseAssembler.class);
        registry.register(new PluginDescriptor("kernel.router", "1.0", eu.kalafatic.evolution.controller.orchestration.ModeRouter.class, Map.of("type", "default"), 0), eu.kalafatic.evolution.controller.orchestration.ModeRouter.class);
        registry.register(new PluginDescriptor("intent.context_resolver", "1.0", eu.kalafatic.evolution.controller.orchestration.workspace.ContextResolver.class, Map.of("type", "default"), 0), eu.kalafatic.evolution.controller.orchestration.workspace.ContextResolver.class);

        // 8. Agents
        registry.register(new PluginDescriptor("agent.analytic", "1.0", IAgent.class, Map.of("type", "Analytic"), 0), AnalyticAgent.class);
        registry.register(new PluginDescriptor("agent.architect", "1.0", IAgent.class, Map.of("type", "Architect"), 0), ArchitectAgent.class);
        registry.register(new PluginDescriptor("agent.javadev", "1.0", IAgent.class, Map.of("type", "JavaDev"), 0), JavaDevAgent.class);
        registry.register(new PluginDescriptor("agent.tester", "1.0", IAgent.class, Map.of("type", "Tester"), 0), TesterAgent.class);
        registry.register(new PluginDescriptor("agent.validator", "1.0", IAgent.class, Map.of("type", "Validator"), 0), ValidatorAgent.class);
        registry.register(new PluginDescriptor("agent.general", "1.0", IAgent.class, Map.of("type", "General"), 0), GeneralAgent.class);
        registry.register(new PluginDescriptor("agent.terminal", "1.0", IAgent.class, Map.of("type", "Terminal"), 0), TerminalAgent.class);
        registry.register(new PluginDescriptor("agent.file", "1.0", IAgent.class, Map.of("type", "File"), 0), FileAgent.class);
        registry.register(new PluginDescriptor("agent.maven", "1.0", IAgent.class, Map.of("type", "Maven"), 0), MavenAgent.class);
        registry.register(new PluginDescriptor("agent.git", "1.0", IAgent.class, Map.of("type", "Git"), 0), GitAgent.class);
        registry.register(new PluginDescriptor("agent.structure", "1.0", IAgent.class, Map.of("type", "Structure"), 0), StructureAgent.class);
        registry.register(new PluginDescriptor("agent.websearch", "1.0", IAgent.class, Map.of("type", "WebSearch"), 0), WebSearchAgent.class);
        registry.register(new PluginDescriptor("agent.quality", "1.0", IAgent.class, Map.of("type", "Quality"), 0), QualityAgent.class);
        registry.register(new PluginDescriptor("agent.observability", "1.0", IAgent.class, Map.of("type", "Observability"), 0), ObservabilityAgent.class);
        registry.register(new PluginDescriptor("agent.repair", "1.0", IAgent.class, Map.of("type", "Repair"), 0), RepairAgent.class);
        registry.register(new PluginDescriptor("agent.planner", "1.0", IAgent.class, Map.of("type", "Planner"), 0), PlannerAgent.class);
        registry.register(new PluginDescriptor("agent.proposalconsolidator", "1.0", IAgent.class, Map.of("type", "ProposalConsolidator"), 0), ProposalConsolidatorAgent.class);
        registry.register(new PluginDescriptor("agent.critic", "1.0", IAgent.class, Map.of("type", "Critic"), 0), CriticAgent.class);
        registry.register(new PluginDescriptor("agent.finalresponse", "1.0", IAgent.class, Map.of("type", "FinalResponse"), 0), FinalResponseAgent.class);
    }
}
