package eu.kalafatic.evolution.controller.orchestration.llm;

import eu.kalafatic.evolution.model.orchestration.*;
import eu.kalafatic.evolution.controller.orchestration.*;
import eu.kalafatic.evolution.controller.providers.AiProviders;
import eu.kalafatic.evolution.controller.providers.ProviderConfig;

/**
 * Router that chooses between LLM providers based on orchestrator settings.
 * Implements HYBRID mode with local proxy optimization and simplification.
 */
public class LlmRouter {

    private final IEvolutionKernel kernel = new BaseEvolutionKernel();
    private final ILlmProvider ollamaProvider = new OllamaProvider();
    private final ILlmProvider openAiProvider = new OpenAIProvider();
    private final ILlmProvider geminiProvider = new GeminiProvider();

    /**
     * Routes the request to the appropriate LLM provider.
     *
     * @param orchestrator The orchestrator model
     * @param prompt The prompt string
     * @param temperature The temperature setting
     * @param proxyUrl Optional proxy URL
     * @param context The task context
     * @return The LLM response
     * @throws Exception If an error occurs
     */
    public String sendRequest(Orchestrator orchestrator, String prompt, float temperature, String proxyUrl, TaskContext context) throws Exception {
        AiMode mode = orchestrator.getAiMode();
        if (mode == AiMode.REMOTE) {
            return sendRemoteRequest(orchestrator, prompt, temperature, proxyUrl, context);
        } else if (mode == AiMode.HYBRID) {
            // HYBRID: 3-step process
            // 1. Optimize prompt using local model
            String optimizedPrompt = optimizePromptLocally(orchestrator, prompt, temperature, proxyUrl, context);

            // 2. Execute using remote model
            String remoteResponse = sendRemoteRequest(orchestrator, optimizedPrompt, temperature, proxyUrl, context);

            // 3. Simplify response using local model
            return simplifyResponseLocally(orchestrator, remoteResponse, temperature, proxyUrl, context);
        } else {
            // LOCAL, ollama+selected local model
            String model = orchestrator.getLocalModel();
            if (model != null && !model.isEmpty()) {
                if (orchestrator.getOllama() == null) {
                    orchestrator.setOllama(eu.kalafatic.evolution.model.orchestration.OrchestrationFactory.eINSTANCE.createOllama());
                }
                orchestrator.getOllama().setModel(model);
            }
            return ollamaProvider.sendRequest(orchestrator, prompt, temperature, proxyUrl, context);
        }
    }

    private String sendRemoteRequest(Orchestrator orchestrator, String prompt, float temperature, String proxyUrl, TaskContext context) throws Exception {
        String remoteModel = orchestrator.getRemoteModel();

        // Default to deepseek if none selected
        if (remoteModel == null || remoteModel.isEmpty()) {
            remoteModel = "deepseek";
            orchestrator.setRemoteModel(remoteModel);
        }

        ProviderConfig config = AiProviders.PROVIDERS.get(remoteModel.toLowerCase());

        if (config != null && "google".equals(config.getFormat())) {
            return geminiProvider.sendRequest(orchestrator, prompt, temperature, proxyUrl, context);
        }

        // Default to common calling (OpenAI format)
        return openAiProvider.sendRequest(orchestrator, prompt, temperature, proxyUrl, context);
    }

    private String optimizePromptLocally(Orchestrator orchestrator, String prompt, float temperature, String proxyUrl, TaskContext context) throws Exception {
        String hybridModel = orchestrator.getHybridModel();
        if (hybridModel != null && !hybridModel.isEmpty()) {
            if (orchestrator.getOllama() == null) {
                orchestrator.setOllama(eu.kalafatic.evolution.model.orchestration.OrchestrationFactory.eINSTANCE.createOllama());
            }
            orchestrator.getOllama().setModel(hybridModel);
        }

        // Phase I: Evolutionary Prompt Optimization
        if (context != null) context.log("LlmRouter: Starting evolutionary prompt optimization...");

        Lineage promptLineage = new SimpleLineageAdapter("prompt-evolution-" + System.currentTimeMillis());
        Artifact initialArtifact = new PromptArtifactAdapter("initial-prompt", prompt);
        promptLineage.getCandidates().add(initialArtifact);

        IEvolutionEnvironment env = new MediationEnvironment();
        Pressure clarityPressure = OrchestrationFactory.eINSTANCE.createPressure();
        clarityPressure.setName("Prompt Clarity");
        clarityPressure.setDescription("Prompt must be clear and effective for AI communication.");

        int iterations = 0;
        int maxIterations = 3;
        Artifact current = initialArtifact;

        while (iterations < maxIterations) {
            iterations++;
            if (context != null) context.log("LlmRouter: Prompt Optimization Iteration " + iterations);

            // Generate Mutation (Local Model)
            String mutationPrompt = "Optimize this AI prompt for better results. " +
                "Improve clarity, add necessary context, and keep it concise. " +
                "Return ONLY the optimized prompt text.\n\n" +
                "Current Prompt:\n" + current.getContent();

            String mutatedContent = ollamaProvider.sendRequest(orchestrator, mutationPrompt, temperature, proxyUrl, context);

            Artifact mutatedArtifact = new PromptArtifactAdapter("optimized-v" + iterations, mutatedContent);
            promptLineage.getCandidates().add(mutatedArtifact);

            // Evaluate and Decide via Kernel
            Artifact survivor = kernel.evolve(promptLineage, clarityPressure, env, context);

            if (promptLineage.getSurvivor() != null) {
                current = promptLineage.getSurvivor();
                if (context != null) context.log("LlmRouter: Prompt stabilized via survivor selection.");
                break;
            }

            if (survivor != null && survivor.getContent().equals(current.getContent())) {
                if (context != null) context.log("LlmRouter: Prompt stabilized via content identity.");
                break;
            }

            current = survivor != null ? survivor : mutatedArtifact;
        }

        String finalPrompt = current.getContent();
        if (context != null) context.log("LlmRouter: Final optimized prompt (Length: " + finalPrompt.length() + ")");

        // Final fallback: if the kernel didn't explicitly select a survivor, use the best known candidate
        if (promptLineage.getSurvivor() == null) {
            promptLineage.setSurvivor(current);
        }

        return finalPrompt;
    }

    private String simplifyResponseLocally(Orchestrator orchestrator, String remoteResponse, float temperature, String proxyUrl, TaskContext context) throws Exception {
        String simplificationPrompt = "The following is a response from a 'big' AI model. " +
                "Analyze it and simplify it for a human user. " +
                "Focus on the most important information and make it easy to understand. " +
                "Provide ONLY the simplified response text.\n\n" +
                "Response: " + remoteResponse;

        return ollamaProvider.sendRequest(orchestrator, simplificationPrompt, temperature, proxyUrl, context);
    }

    /**
     * Tests the connection to the appropriate LLM provider.
     *
     * @param orchestrator The orchestrator model
     * @param temperature The temperature setting
     * @param proxyUrl Optional proxy URL
     * @param context The task context
     * @return The LLM response
     * @throws Exception If an error occurs
     */
    public String testConnection(Orchestrator orchestrator, float temperature, String proxyUrl, TaskContext context) throws Exception {
        AiMode mode = orchestrator.getAiMode();
        if (mode == AiMode.REMOTE || mode == AiMode.HYBRID) {
            // For HYBRID, test remote connection as it's the most critical part
            String remoteModel = orchestrator.getRemoteModel();

            // Default to deepseek if none selected
            if (remoteModel == null || remoteModel.isEmpty()) {
                remoteModel = "deepseek";
                orchestrator.setRemoteModel(remoteModel);
            }

            ProviderConfig config = AiProviders.PROVIDERS.get(remoteModel.toLowerCase());

            if (config != null && "google".equals(config.getFormat())) {
                return geminiProvider.testConnection(orchestrator, temperature, proxyUrl, context);
            }

            // Default to common calling (OpenAI format)
            return openAiProvider.testConnection(orchestrator, temperature, proxyUrl, context);

        } else {
            return ollamaProvider.testConnection(orchestrator, temperature, proxyUrl, context);
        }
    }
}
