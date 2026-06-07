package eu.kalafatic.evolution.model.orchestration.impl;

import eu.kalafatic.evolution.model.orchestration.*;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

public class OrchestrationFactoryImpl extends EFactoryImpl implements OrchestrationFactory {
    public static OrchestrationFactory init() {
        try {
            OrchestrationFactory theOrchestrationFactory = (OrchestrationFactory)EPackage.Registry.INSTANCE.getEFactory(OrchestrationPackage.eNS_URI);
            if (theOrchestrationFactory != null) return theOrchestrationFactory;
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new OrchestrationFactoryImpl();
    }

    public OrchestrationFactoryImpl() { super(); }

    @Override public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case OrchestrationPackage.TASK: return createTask();
            case OrchestrationPackage.AGENT: return createAgent();
            case OrchestrationPackage.ORCHESTRATOR: return createOrchestrator();
            case OrchestrationPackage.GIT: return createGit();
            case OrchestrationPackage.MAVEN: return createMaven();
            case OrchestrationPackage.LLM: return createLLM();
            case OrchestrationPackage.COMPILER: return createCompiler();
            case OrchestrationPackage.COMMAND: return createCommand();
            case OrchestrationPackage.OLLAMA: return createOllama();
            case OrchestrationPackage.AI_CHAT: return createAiChat();
            case OrchestrationPackage.NEURON_AI: return createNeuronAI();
            case OrchestrationPackage.EVO_PROJECT: return createEvoProject();
            case OrchestrationPackage.ACCESS_RULE: return createAccessRule();
            case OrchestrationPackage.NETWORK_RULE: return createNetworkRule();
            case OrchestrationPackage.MEMORY_RULE: return createMemoryRule();
            case OrchestrationPackage.SECRET_RULE: return createSecretRule();
            case OrchestrationPackage.SELF_DEV_SESSION: return createSelfDevSession();
            case OrchestrationPackage.ITERATION: return createIteration();
            case OrchestrationPackage.EVALUATION_RESULT: return createEvaluationResult();
            case OrchestrationPackage.DATABASE: return createDatabase();
            case OrchestrationPackage.FILE_CONFIG: return createFileConfig();
            case OrchestrationPackage.ARTIFACT: return createArtifact();
            case OrchestrationPackage.LINEAGE: return createLineage();
            case OrchestrationPackage.EVOLUTION_STEP: return createEvolutionStep();
            case OrchestrationPackage.MUTATION: return createMutation();
            case OrchestrationPackage.EVALUATION: return createEvaluation();
            case OrchestrationPackage.PRESSURE: return createPressure();
            case OrchestrationPackage.PROPERTY: return createProperty();
            default: throw new IllegalArgumentException("Unknown class");
        }
    }

    @Override public Task createTask() { return new TaskImpl(); }
    @Override public Agent createAgent() { return new AgentImpl(); }
    @Override public Orchestrator createOrchestrator() { return new OrchestratorImpl(); }
    @Override public Git createGit() { return new GitImpl(); }
    @Override public Maven createMaven() { return new MavenImpl(); }
    @Override public LLM createLLM() { return new LLMImpl(); }
    @Override public Compiler createCompiler() { return new CompilerImpl(); }
    @Override public Command createCommand() { return new CommandImpl(); }
    @Override public Ollama createOllama() { return new OllamaImpl(); }
    @Override public AiChat createAiChat() { return new AiChatImpl(); }
    @Override public NeuronAI createNeuronAI() { return new NeuronAIImpl(); }
    @Override public EvoProject createEvoProject() { return new EvoProjectImpl(); }
    @Override public AccessRule createAccessRule() { return new AccessRuleImpl(); }
    @Override public NetworkRule createNetworkRule() { return new NetworkRuleImpl(); }
    @Override public MemoryRule createMemoryRule() { return new MemoryRuleImpl(); }
    @Override public SecretRule createSecretRule() { return new SecretRuleImpl(); }
    @Override public SelfDevSession createSelfDevSession() { return new SelfDevSessionImpl(); }
    @Override public Iteration createIteration() { return new IterationImpl(); }
    @Override public EvaluationResult createEvaluationResult() { return new EvaluationResultImpl(); }
    @Override public Database createDatabase() { return new DatabaseImpl(); }
    @Override public FileConfig createFileConfig() { return new FileConfigImpl(); }
    @Override public Artifact createArtifact() { return new ArtifactImpl(); }
    @Override public Lineage createLineage() { return new LineageImpl(); }
    @Override public EvolutionStep createEvolutionStep() { return new EvolutionStepImpl(); }
    @Override public Mutation createMutation() { return new MutationImpl(); }
    @Override public Evaluation createEvaluation() { return new EvaluationImpl(); }
    @Override public Pressure createPressure() { return new PressureImpl(); }
    @Override public Property createProperty() { return new PropertyImpl(); }
    @Override public OrchestrationPackage getOrchestrationPackage() { return (OrchestrationPackage)getEPackage(); }
}
