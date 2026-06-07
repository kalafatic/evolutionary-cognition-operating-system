package eu.kalafatic.evolution.model.orchestration.impl;

import eu.kalafatic.evolution.model.orchestration.*;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.impl.EPackageImpl;

public class OrchestrationPackageImpl extends EPackageImpl implements OrchestrationPackage {
    private EClass taskEClass = null;
    private EClass agentEClass = null;
    private EClass orchestratorEClass = null;
    private EClass gitEClass = null;
    private EClass mavenEClass = null;
    private EClass llmEClass = null;
    private EClass compilerEClass = null;
    private EClass commandEClass = null;
    private EClass ollamaEClass = null;
    private EClass aiChatEClass = null;
    private EClass neuronAIEClass = null;
    private EClass evoProjectEClass = null;
    private EClass databaseEClass = null;
    private EClass fileConfigEClass = null;
    private EClass ruleEClass = null;
    private EClass accessRuleEClass = null;
    private EClass networkRuleEClass = null;
    private EClass memoryRuleEClass = null;
    private EClass secretRuleEClass = null;
    private EClass selfDevSessionEClass = null;
    private EClass iterationEClass = null;
    private EClass evaluationResultEClass = null;
    private EClass artifactEClass = null;
    private EClass lineageEClass = null;
    private EClass evolutionStepEClass = null;
    private EClass mutationEClass = null;
    private EClass evaluationEClass = null;
    private EClass pressureEClass = null;
    private EClass propertyEClass = null;

    private EEnum taskStatusEEnum = null;
    private EEnum commandStatusEEnum = null;
    private EEnum executionModeEEnum = null;
    private EEnum neuronTypeEEnum = null;
    private EEnum aiModeEEnum = null;
    private EEnum selfDevStatusEEnum = null;
    private EEnum iterationStatusEEnum = null;
    private EEnum selfDevDecisionEEnum = null;

    private OrchestrationPackageImpl() {
        super(eNS_URI, OrchestrationFactory.eINSTANCE);
    }

    private static boolean isInited = false;

    public static OrchestrationPackage init() {
        if (isInited) return (OrchestrationPackage)EPackage.Registry.INSTANCE.getEPackage(OrchestrationPackage.eNS_URI);
        OrchestrationPackageImpl theOrchestrationPackage = new OrchestrationPackageImpl();
        isInited = true;
        theOrchestrationPackage.createPackageContents();
        theOrchestrationPackage.initializePackageContents();
        theOrchestrationPackage.freeze();
        EPackage.Registry.INSTANCE.put(OrchestrationPackage.eNS_URI, theOrchestrationPackage);
        return theOrchestrationPackage;
    }

    @Override public EClass getTask() { return taskEClass; }
    @Override public EAttribute getTask_Id() { return (EAttribute)taskEClass.getEStructuralFeatures().get(0); }
    @Override public EAttribute getTask_Name() { return (EAttribute)taskEClass.getEStructuralFeatures().get(1); }
    @Override public EAttribute getTask_Type() { return (EAttribute)taskEClass.getEStructuralFeatures().get(2); }
    @Override public EAttribute getTask_Status() { return (EAttribute)taskEClass.getEStructuralFeatures().get(3); }
    @Override public EReference getTask_Next() { return (EReference)taskEClass.getEStructuralFeatures().get(4); }
    @Override public EReference getTask_SubTasks() { return (EReference)taskEClass.getEStructuralFeatures().get(5); }
    @Override public EAttribute getTask_Response() { return (EAttribute)taskEClass.getEStructuralFeatures().get(6); }
    @Override public EAttribute getTask_Feedback() { return (EAttribute)taskEClass.getEStructuralFeatures().get(7); }
    @Override public EAttribute getTask_ApprovalRequired() { return (EAttribute)taskEClass.getEStructuralFeatures().get(8); }
    @Override public EAttribute getTask_LoopToTaskId() { return (EAttribute)taskEClass.getEStructuralFeatures().get(9); }
    @Override public EAttribute getTask_Priority() { return (EAttribute)taskEClass.getEStructuralFeatures().get(10); }
    @Override public EAttribute getTask_ResultSummary() { return (EAttribute)taskEClass.getEStructuralFeatures().get(11); }
    @Override public EAttribute getTask_Description() { return (EAttribute)taskEClass.getEStructuralFeatures().get(12); }

    @Override public EClass getAgent() { return agentEClass; }
    @Override public EAttribute getAgent_Id() { return (EAttribute)agentEClass.getEStructuralFeatures().get(0); }
    @Override public EAttribute getAgent_Type() { return (EAttribute)agentEClass.getEStructuralFeatures().get(1); }
    @Override public EReference getAgent_Tasks() { return (EReference)agentEClass.getEStructuralFeatures().get(2); }
    @Override public EAttribute getAgent_ExecutionMode() { return (EAttribute)agentEClass.getEStructuralFeatures().get(3); }
    @Override public EReference getAgent_Rules() { return (EReference)agentEClass.getEStructuralFeatures().get(4); }

    @Override public EClass getOrchestrator() { return orchestratorEClass; }
    @Override public EAttribute getOrchestrator_Id() { return (EAttribute)orchestratorEClass.getEStructuralFeatures().get(0); }
    @Override public EAttribute getOrchestrator_Name() { return (EAttribute)orchestratorEClass.getEStructuralFeatures().get(1); }
    @Override public EReference getOrchestrator_Agents() { return (EReference)orchestratorEClass.getEStructuralFeatures().get(2); }
    @Override public EReference getOrchestrator_Tasks() { return (EReference)orchestratorEClass.getEStructuralFeatures().get(3); }
    @Override public EReference getOrchestrator_Git() { return (EReference)orchestratorEClass.getEStructuralFeatures().get(4); }
    @Override public EReference getOrchestrator_Maven() { return (EReference)orchestratorEClass.getEStructuralFeatures().get(5); }
    @Override public EReference getOrchestrator_Llm() { return (EReference)orchestratorEClass.getEStructuralFeatures().get(6); }
    @Override public EReference getOrchestrator_Compiler() { return (EReference)orchestratorEClass.getEStructuralFeatures().get(7); }
    @Override public EReference getOrchestrator_Ollama() { return (EReference)orchestratorEClass.getEStructuralFeatures().get(8); }
    @Override public EReference getOrchestrator_AiChat() { return (EReference)orchestratorEClass.getEStructuralFeatures().get(9); }
    @Override public EReference getOrchestrator_NeuronAI() { return (EReference)orchestratorEClass.getEStructuralFeatures().get(10); }
    @Override public EAttribute getOrchestrator_RemoteModel() { return (EAttribute)orchestratorEClass.getEStructuralFeatures().get(11); }
    @Override public EAttribute getOrchestrator_AiMode() { return (EAttribute)orchestratorEClass.getEStructuralFeatures().get(12); }
    @Override public EAttribute getOrchestrator_McpServerUrl() { return (EAttribute)orchestratorEClass.getEStructuralFeatures().get(13); }
    @Override public EAttribute getOrchestrator_OpenAiToken() { return (EAttribute)orchestratorEClass.getEStructuralFeatures().get(14); }
    @Override public EAttribute getOrchestrator_OpenAiModel() { return (EAttribute)orchestratorEClass.getEStructuralFeatures().get(15); }
    @Override public EAttribute getOrchestrator_LocalModel() { return (EAttribute)orchestratorEClass.getEStructuralFeatures().get(16); }
    @Override public EAttribute getOrchestrator_HybridModel() { return (EAttribute)orchestratorEClass.getEStructuralFeatures().get(17); }
    @Override public EAttribute getOrchestrator_OfflineMode() { return (EAttribute)orchestratorEClass.getEStructuralFeatures().get(18); }
    @Override public EReference getOrchestrator_SelfDevSession() { return (EReference)orchestratorEClass.getEStructuralFeatures().get(19); }
    @Override public EReference getOrchestrator_Database() { return (EReference)orchestratorEClass.getEStructuralFeatures().get(20); }
    @Override public EReference getOrchestrator_FileConfig() { return (EReference)orchestratorEClass.getEStructuralFeatures().get(21); }
    @Override public EAttribute getOrchestrator_SharedMemory() { return (EAttribute)orchestratorEClass.getEStructuralFeatures().get(22); }
    @Override public EReference getOrchestrator_Lineages() { return (EReference)orchestratorEClass.getEStructuralFeatures().get(23); }
    @Override public EReference getOrchestrator_Pressures() { return (EReference)orchestratorEClass.getEStructuralFeatures().get(24); }

    @Override public EClass getGit() { return gitEClass; }
    @Override public EAttribute getGit_RepositoryUrl() { return (EAttribute)gitEClass.getEStructuralFeatures().get(0); }
    @Override public EAttribute getGit_Branch() { return (EAttribute)gitEClass.getEStructuralFeatures().get(1); }
    @Override public EAttribute getGit_Username() { return (EAttribute)gitEClass.getEStructuralFeatures().get(2); }
    @Override public EAttribute getGit_LocalPath() { return (EAttribute)gitEClass.getEStructuralFeatures().get(3); }

    @Override public EClass getMaven() { return mavenEClass; }
    @Override public EAttribute getMaven_Goals() { return (EAttribute)mavenEClass.getEStructuralFeatures().get(0); }
    @Override public EAttribute getMaven_Profiles() { return (EAttribute)mavenEClass.getEStructuralFeatures().get(1); }

    @Override public EClass getLLM() { return llmEClass; }
    @Override public EAttribute getLLM_Model() { return (EAttribute)llmEClass.getEStructuralFeatures().get(0); }
    @Override public EAttribute getLLM_Temperature() { return (EAttribute)llmEClass.getEStructuralFeatures().get(1); }

    @Override public EClass getCompiler() { return compilerEClass; }
    @Override public EAttribute getCompiler_SourceVersion() { return (EAttribute)compilerEClass.getEStructuralFeatures().get(0); }
    @Override public EAttribute getCompiler_TargetVersion() { return (EAttribute)compilerEClass.getEStructuralFeatures().get(1); }

    @Override public EClass getCommand() { return commandEClass; }
    @Override public EAttribute getCommand_Name() { return (EAttribute)commandEClass.getEStructuralFeatures().get(0); }
    @Override public EAttribute getCommand_Status() { return (EAttribute)commandEClass.getEStructuralFeatures().get(1); }

    @Override public EClass getOllama() { return ollamaEClass; }
    @Override public EAttribute getOllama_Url() { return (EAttribute)ollamaEClass.getEStructuralFeatures().get(0); }
    @Override public EAttribute getOllama_Model() { return (EAttribute)ollamaEClass.getEStructuralFeatures().get(1); }
    @Override public EAttribute getOllama_Path() { return (EAttribute)ollamaEClass.getEStructuralFeatures().get(2); }

    @Override public EClass getAiChat() { return aiChatEClass; }
    @Override public EAttribute getAiChat_Url() { return (EAttribute)aiChatEClass.getEStructuralFeatures().get(0); }
    @Override public EAttribute getAiChat_Token() { return (EAttribute)aiChatEClass.getEStructuralFeatures().get(1); }
    @Override public EAttribute getAiChat_Prompt() { return (EAttribute)aiChatEClass.getEStructuralFeatures().get(2); }
    @Override public EAttribute getAiChat_ProxyUrl() { return (EAttribute)aiChatEClass.getEStructuralFeatures().get(3); }

    @Override public EClass getNeuronAI() { return neuronAIEClass; }
    @Override public EAttribute getNeuronAI_Url() { return (EAttribute)neuronAIEClass.getEStructuralFeatures().get(0); }
    @Override public EAttribute getNeuronAI_Model() { return (EAttribute)neuronAIEClass.getEStructuralFeatures().get(1); }
    @Override public EAttribute getNeuronAI_Type() { return (EAttribute)neuronAIEClass.getEStructuralFeatures().get(2); }
    @Override public EAttribute getNeuronAI_TrainingData() { return (EAttribute)neuronAIEClass.getEStructuralFeatures().get(3); }

    @Override public EClass getEvoProject() { return evoProjectEClass; }
    @Override public EAttribute getEvoProject_Name() { return (EAttribute)evoProjectEClass.getEStructuralFeatures().get(0); }
    @Override public EReference getEvoProject_Orchestrations() { return (EReference)evoProjectEClass.getEStructuralFeatures().get(1); }
    @Override public EReference getEvoProject_GlobalLineages() { return (EReference)evoProjectEClass.getEStructuralFeatures().get(2); }

    @Override public EClass getRule() { return ruleEClass; }
    @Override public EAttribute getRule_Name() { return (EAttribute)ruleEClass.getEStructuralFeatures().get(0); }
    @Override public EAttribute getRule_Description() { return (EAttribute)ruleEClass.getEStructuralFeatures().get(1); }

    @Override public EClass getAccessRule() { return accessRuleEClass; }
    @Override public EAttribute getAccessRule_AllowedPaths() { return (EAttribute)accessRuleEClass.getEStructuralFeatures().get(0); }
    @Override public EAttribute getAccessRule_DeniedPaths() { return (EAttribute)accessRuleEClass.getEStructuralFeatures().get(1); }

    @Override public EClass getNetworkRule() { return networkRuleEClass; }
    @Override public EAttribute getNetworkRule_AllowedDomains() { return (EAttribute)networkRuleEClass.getEStructuralFeatures().get(0); }
    @Override public EAttribute getNetworkRule_AllowAll() { return (EAttribute)networkRuleEClass.getEStructuralFeatures().get(1); }

    @Override public EClass getMemoryRule() { return memoryRuleEClass; }
    @Override public EAttribute getMemoryRule_StorageLimit() { return (EAttribute)memoryRuleEClass.getEStructuralFeatures().get(0); }
    @Override public EAttribute getMemoryRule_RetentionPeriod() { return (EAttribute)memoryRuleEClass.getEStructuralFeatures().get(1); }

    @Override public EClass getSecretRule() { return secretRuleEClass; }
    @Override public EAttribute getSecretRule_AllowedSecrets() { return (EAttribute)secretRuleEClass.getEStructuralFeatures().get(0); }

    @Override public EClass getSelfDevSession() { return selfDevSessionEClass; }
    @Override public EAttribute getSelfDevSession_Id() { return (EAttribute)selfDevSessionEClass.getEStructuralFeatures().get(0); }
    @Override public EAttribute getSelfDevSession_StartTime() { return (EAttribute)selfDevSessionEClass.getEStructuralFeatures().get(1); }
    @Override public EAttribute getSelfDevSession_MaxIterations() { return (EAttribute)selfDevSessionEClass.getEStructuralFeatures().get(2); }
    @Override public EAttribute getSelfDevSession_Status() { return (EAttribute)selfDevSessionEClass.getEStructuralFeatures().get(3); }
    @Override public EReference getSelfDevSession_Iterations() { return (EReference)selfDevSessionEClass.getEStructuralFeatures().get(4); }

    @Override public EClass getIteration() { return iterationEClass; }
    @Override public EAttribute getIteration_Id() { return (EAttribute)iterationEClass.getEStructuralFeatures().get(0); }
    @Override public EAttribute getIteration_BranchName() { return (EAttribute)iterationEClass.getEStructuralFeatures().get(1); }
    @Override public EReference getIteration_Tasks() { return (EReference)iterationEClass.getEStructuralFeatures().get(2); }
    @Override public EReference getIteration_EvaluationResult() { return (EReference)iterationEClass.getEStructuralFeatures().get(3); }
    @Override public EAttribute getIteration_Status() { return (EAttribute)iterationEClass.getEStructuralFeatures().get(4); }

    @Override public EClass getEvaluationResult() { return evaluationResultEClass; }
    @Override public EAttribute getEvaluationResult_Success() { return (EAttribute)evaluationResultEClass.getEStructuralFeatures().get(0); }
    @Override public EAttribute getEvaluationResult_TestPassRate() { return (EAttribute)evaluationResultEClass.getEStructuralFeatures().get(1); }
    @Override public EAttribute getEvaluationResult_CoverageChange() { return (EAttribute)evaluationResultEClass.getEStructuralFeatures().get(2); }
    @Override public EAttribute getEvaluationResult_Errors() { return (EAttribute)evaluationResultEClass.getEStructuralFeatures().get(3); }
    @Override public EAttribute getEvaluationResult_Decision() { return (EAttribute)evaluationResultEClass.getEStructuralFeatures().get(4); }

    @Override public EClass getDatabase() { return databaseEClass; }
    @Override public EAttribute getDatabase_Url() { return (EAttribute)databaseEClass.getEStructuralFeatures().get(0); }
    @Override public EAttribute getDatabase_Username() { return (EAttribute)databaseEClass.getEStructuralFeatures().get(1); }
    @Override public EAttribute getDatabase_Password() { return (EAttribute)databaseEClass.getEStructuralFeatures().get(2); }
    @Override public EAttribute getDatabase_Driver() { return (EAttribute)databaseEClass.getEStructuralFeatures().get(3); }

    @Override public EClass getFileConfig() { return fileConfigEClass; }
    @Override public EAttribute getFileConfig_LocalPath() { return (EAttribute)fileConfigEClass.getEStructuralFeatures().get(0); }

    @Override public EClass getArtifact() { return artifactEClass; }
    @Override public EAttribute getArtifact_Id() { return (EAttribute)artifactEClass.getEStructuralFeatures().get(0); }
    @Override public EAttribute getArtifact_Type() { return (EAttribute)artifactEClass.getEStructuralFeatures().get(1); }
    @Override public EAttribute getArtifact_Content() { return (EAttribute)artifactEClass.getEStructuralFeatures().get(2); }
    @Override public EReference getArtifact_Properties() { return (EReference)artifactEClass.getEStructuralFeatures().get(3); }

    @Override public EClass getLineage() { return lineageEClass; }
    @Override public EAttribute getLineage_Id() { return (EAttribute)lineageEClass.getEStructuralFeatures().get(0); }
    @Override public EReference getLineage_Survivor() { return (EReference)lineageEClass.getEStructuralFeatures().get(1); }
    @Override public EReference getLineage_Candidates() { return (EReference)lineageEClass.getEStructuralFeatures().get(2); }
    @Override public EReference getLineage_History() { return (EReference)lineageEClass.getEStructuralFeatures().get(3); }

    @Override public EClass getEvolutionStep() { return evolutionStepEClass; }
    @Override public EAttribute getEvolutionStep_Timestamp() { return (EAttribute)evolutionStepEClass.getEStructuralFeatures().get(0); }
    @Override public EReference getEvolutionStep_Mutation() { return (EReference)evolutionStepEClass.getEStructuralFeatures().get(1); }
    @Override public EReference getEvolutionStep_Evaluations() { return (EReference)evolutionStepEClass.getEStructuralFeatures().get(2); }
    @Override public EReference getEvolutionStep_SelectedSurvivor() { return (EReference)evolutionStepEClass.getEStructuralFeatures().get(3); }

    @Override public EClass getMutation() { return mutationEClass; }
    @Override public EAttribute getMutation_Description() { return (EAttribute)mutationEClass.getEStructuralFeatures().get(0); }
    @Override public EAttribute getMutation_Type() { return (EAttribute)mutationEClass.getEStructuralFeatures().get(1); }

    @Override public EClass getEvaluation() { return evaluationEClass; }
    @Override public EReference getEvaluation_Pressure() { return (EReference)evaluationEClass.getEStructuralFeatures().get(0); }
    @Override public EAttribute getEvaluation_Score() { return (EAttribute)evaluationEClass.getEStructuralFeatures().get(1); }
    @Override public EAttribute getEvaluation_Comment() { return (EAttribute)evaluationEClass.getEStructuralFeatures().get(2); }

    @Override public EClass getPressure() { return pressureEClass; }
    @Override public EAttribute getPressure_Name() { return (EAttribute)pressureEClass.getEStructuralFeatures().get(0); }
    @Override public EAttribute getPressure_Description() { return (EAttribute)pressureEClass.getEStructuralFeatures().get(1); }

    @Override public EClass getProperty() { return propertyEClass; }
    @Override public EAttribute getProperty_Key() { return (EAttribute)propertyEClass.getEStructuralFeatures().get(0); }
    @Override public EAttribute getProperty_Value() { return (EAttribute)propertyEClass.getEStructuralFeatures().get(1); }

    @Override public EEnum getTaskStatus() { return taskStatusEEnum; }
    @Override public EEnum getCommandStatus() { return commandStatusEEnum; }
    @Override public EEnum getExecutionMode() { return executionModeEEnum; }
    @Override public EEnum getNeuronType() { return neuronTypeEEnum; }
    @Override public EEnum getAiMode() { return aiModeEEnum; }
    @Override public EEnum getSelfDevStatus() { return selfDevStatusEEnum; }
    @Override public EEnum getIterationStatus() { return iterationStatusEEnum; }
    @Override public EEnum getSelfDevDecision() { return selfDevDecisionEEnum; }

    @Override public OrchestrationFactory getOrchestrationFactory() { return (OrchestrationFactory)getEFactoryInstance(); }

    private boolean isCreated = false;
    public void createPackageContents() {
        if (isCreated) return;
        isCreated = true;
        taskEClass = createEClass(TASK);
        createEAttribute(taskEClass, TASK__ID);
        createEAttribute(taskEClass, TASK__NAME);
        createEAttribute(taskEClass, TASK__TYPE);
        createEAttribute(taskEClass, TASK__STATUS);
        createEReference(taskEClass, TASK__NEXT);
        createEReference(taskEClass, TASK__SUB_TASKS);
        createEAttribute(taskEClass, TASK__RESPONSE);
        createEAttribute(taskEClass, TASK__FEEDBACK);
        createEAttribute(taskEClass, TASK__APPROVAL_REQUIRED);
        createEAttribute(taskEClass, TASK__LOOP_TO_TASK_ID);
        createEAttribute(taskEClass, TASK__PRIORITY);
        createEAttribute(taskEClass, TASK__RESULT_SUMMARY);
        createEAttribute(taskEClass, TASK__DESCRIPTION);

        agentEClass = createEClass(AGENT);
        createEAttribute(agentEClass, AGENT__ID);
        createEAttribute(agentEClass, AGENT__TYPE);
        createEReference(agentEClass, AGENT__TASKS);
        createEAttribute(agentEClass, AGENT__EXECUTION_MODE);
        createEReference(agentEClass, AGENT__RULES);

        orchestratorEClass = createEClass(ORCHESTRATOR);
        createEAttribute(orchestratorEClass, ORCHESTRATOR__ID);
        createEAttribute(orchestratorEClass, ORCHESTRATOR__NAME);
        createEReference(orchestratorEClass, ORCHESTRATOR__AGENTS);
        createEReference(orchestratorEClass, ORCHESTRATOR__TASKS);
        createEReference(orchestratorEClass, ORCHESTRATOR__GIT);
        createEReference(orchestratorEClass, ORCHESTRATOR__MAVEN);
        createEReference(orchestratorEClass, ORCHESTRATOR__LLM);
        createEReference(orchestratorEClass, ORCHESTRATOR__COMPILER);
        createEReference(orchestratorEClass, ORCHESTRATOR__OLLAMA);
        createEReference(orchestratorEClass, ORCHESTRATOR__AI_CHAT);
        createEReference(orchestratorEClass, ORCHESTRATOR__NEURON_AI);
        createEAttribute(orchestratorEClass, ORCHESTRATOR__REMOTE_MODEL);
        createEAttribute(orchestratorEClass, ORCHESTRATOR__AI_MODE);
        createEAttribute(orchestratorEClass, ORCHESTRATOR__MCP_SERVER_URL);
        createEAttribute(orchestratorEClass, ORCHESTRATOR__OPEN_AI_TOKEN);
        createEAttribute(orchestratorEClass, ORCHESTRATOR__OPEN_AI_MODEL);
        createEAttribute(orchestratorEClass, ORCHESTRATOR__LOCAL_MODEL);
        createEAttribute(orchestratorEClass, ORCHESTRATOR__HYBRID_MODEL);
        createEAttribute(orchestratorEClass, ORCHESTRATOR__OFFLINE_MODE);
        createEReference(orchestratorEClass, ORCHESTRATOR__SELF_DEV_SESSION);
        createEReference(orchestratorEClass, ORCHESTRATOR__DATABASE);
        createEReference(orchestratorEClass, ORCHESTRATOR__FILE_CONFIG);
        createEAttribute(orchestratorEClass, ORCHESTRATOR__SHARED_MEMORY);
        createEReference(orchestratorEClass, ORCHESTRATOR__LINEAGES);
        createEReference(orchestratorEClass, ORCHESTRATOR__PRESSURES);

        gitEClass = createEClass(GIT);
        createEAttribute(gitEClass, GIT__REPOSITORY_URL);
        createEAttribute(gitEClass, GIT__BRANCH);
        createEAttribute(gitEClass, GIT__USERNAME);
        createEAttribute(gitEClass, GIT__LOCAL_PATH);

        mavenEClass = createEClass(MAVEN);
        createEAttribute(mavenEClass, MAVEN__GOALS);
        createEAttribute(mavenEClass, MAVEN__PROFILES);

        llmEClass = createEClass(LLM);
        createEAttribute(llmEClass, LLM__MODEL);
        createEAttribute(llmEClass, LLM__TEMPERATURE);

        compilerEClass = createEClass(COMPILER);
        createEAttribute(compilerEClass, COMPILER__SOURCE_VERSION);
        createEAttribute(compilerEClass, COMPILER__TARGET_VERSION);

        commandEClass = createEClass(COMMAND);
        createEAttribute(commandEClass, COMMAND__NAME);
        createEAttribute(commandEClass, COMMAND__STATUS);

        ollamaEClass = createEClass(OLLAMA);
        createEAttribute(ollamaEClass, OLLAMA__URL);
        createEAttribute(ollamaEClass, OLLAMA__MODEL);
        createEAttribute(ollamaEClass, OLLAMA__PATH);

        aiChatEClass = createEClass(AI_CHAT);
        createEAttribute(aiChatEClass, AI_CHAT__URL);
        createEAttribute(aiChatEClass, AI_CHAT__TOKEN);
        createEAttribute(aiChatEClass, AI_CHAT__PROMPT);
        createEAttribute(aiChatEClass, AI_CHAT__PROXY_URL);

        neuronAIEClass = createEClass(NEURON_AI);
        createEAttribute(neuronAIEClass, NEURON_AI__URL);
        createEAttribute(neuronAIEClass, NEURON_AI__MODEL);
        createEAttribute(neuronAIEClass, NEURON_AI__TYPE);
        createEAttribute(neuronAIEClass, NEURON_AI__TRAINING_DATA);

        evoProjectEClass = createEClass(EVO_PROJECT);
        createEAttribute(evoProjectEClass, EVO_PROJECT__NAME);
        createEReference(evoProjectEClass, EVO_PROJECT__ORCHESTRATIONS);
        createEReference(evoProjectEClass, EVO_PROJECT__GLOBAL_LINEAGES);

        ruleEClass = createEClass(RULE);
        createEAttribute(ruleEClass, RULE__NAME);
        createEAttribute(ruleEClass, RULE__DESCRIPTION);

        accessRuleEClass = createEClass(ACCESS_RULE);
        createEAttribute(accessRuleEClass, ACCESS_RULE__ALLOWED_PATHS);
        createEAttribute(accessRuleEClass, ACCESS_RULE__DENIED_PATHS);

        networkRuleEClass = createEClass(NETWORK_RULE);
        createEAttribute(networkRuleEClass, NETWORK_RULE__ALLOWED_DOMAINS);
        createEAttribute(networkRuleEClass, NETWORK_RULE__ALLOW_ALL);

        memoryRuleEClass = createEClass(MEMORY_RULE);
        createEAttribute(memoryRuleEClass, MEMORY_RULE__STORAGE_LIMIT);
        createEAttribute(memoryRuleEClass, MEMORY_RULE__RETENTION_PERIOD);

        secretRuleEClass = createEClass(SECRET_RULE);
        createEAttribute(secretRuleEClass, SECRET_RULE__ALLOWED_SECRETS);

        selfDevSessionEClass = createEClass(SELF_DEV_SESSION);
        createEAttribute(selfDevSessionEClass, SELF_DEV_SESSION__ID);
        createEAttribute(selfDevSessionEClass, SELF_DEV_SESSION__START_TIME);
        createEAttribute(selfDevSessionEClass, SELF_DEV_SESSION__MAX_ITERATIONS);
        createEAttribute(selfDevSessionEClass, SELF_DEV_SESSION__STATUS);
        createEReference(selfDevSessionEClass, SELF_DEV_SESSION__ITERATIONS);

        iterationEClass = createEClass(ITERATION);
        createEAttribute(iterationEClass, ITERATION__ID);
        createEAttribute(iterationEClass, ITERATION__BRANCH_NAME);
        createEReference(iterationEClass, ITERATION__TASKS);
        createEReference(iterationEClass, ITERATION__EVALUATION_RESULT);
        createEAttribute(iterationEClass, ITERATION__STATUS);

        evaluationResultEClass = createEClass(EVALUATION_RESULT);
        createEAttribute(evaluationResultEClass, EVALUATION_RESULT__SUCCESS);
        createEAttribute(evaluationResultEClass, EVALUATION_RESULT__TEST_PASS_RATE);
        createEAttribute(evaluationResultEClass, EVALUATION_RESULT__COVERAGE_CHANGE);
        createEAttribute(evaluationResultEClass, EVALUATION_RESULT__ERRORS);
        createEAttribute(evaluationResultEClass, EVALUATION_RESULT__DECISION);

        databaseEClass = createEClass(DATABASE);
        createEAttribute(databaseEClass, DATABASE__URL);
        createEAttribute(databaseEClass, DATABASE__USERNAME);
        createEAttribute(databaseEClass, DATABASE__PASSWORD);
        createEAttribute(databaseEClass, DATABASE__DRIVER);

        fileConfigEClass = createEClass(FILE_CONFIG);
        createEAttribute(fileConfigEClass, FILE_CONFIG__LOCAL_PATH);

        artifactEClass = createEClass(ARTIFACT);
        createEAttribute(artifactEClass, ARTIFACT__ID);
        createEAttribute(artifactEClass, ARTIFACT__TYPE);
        createEAttribute(artifactEClass, ARTIFACT__CONTENT);
        createEReference(artifactEClass, ARTIFACT__PROPERTIES);

        lineageEClass = createEClass(LINEAGE);
        createEAttribute(lineageEClass, LINEAGE__ID);
        createEReference(lineageEClass, LINEAGE__SURVIVOR);
        createEReference(lineageEClass, LINEAGE__CANDIDATES);
        createEReference(lineageEClass, LINEAGE__HISTORY);

        evolutionStepEClass = createEClass(EVOLUTION_STEP);
        createEAttribute(evolutionStepEClass, EVOLUTION_STEP__TIMESTAMP);
        createEReference(evolutionStepEClass, EVOLUTION_STEP__MUTATION);
        createEReference(evolutionStepEClass, EVOLUTION_STEP__EVALUATIONS);
        createEReference(evolutionStepEClass, EVOLUTION_STEP__SELECTED_SURVIVOR);

        mutationEClass = createEClass(MUTATION);
        createEAttribute(mutationEClass, MUTATION__DESCRIPTION);
        createEAttribute(mutationEClass, MUTATION__TYPE);

        evaluationEClass = createEClass(EVALUATION);
        createEReference(evaluationEClass, EVALUATION__PRESSURE);
        createEAttribute(evaluationEClass, EVALUATION__SCORE);
        createEAttribute(evaluationEClass, EVALUATION__COMMENT);

        pressureEClass = createEClass(PRESSURE);
        createEAttribute(pressureEClass, PRESSURE__NAME);
        createEAttribute(pressureEClass, PRESSURE__DESCRIPTION);

        propertyEClass = createEClass(PROPERTY);
        createEAttribute(propertyEClass, PROPERTY__KEY);
        createEAttribute(propertyEClass, PROPERTY__VALUE);
    }

    private boolean isInitialized = false;
    public void initializePackageContents() {
        if (isInitialized) return;
        isInitialized = true;
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);
        accessRuleEClass.getESuperTypes().add(this.getRule());
        networkRuleEClass.getESuperTypes().add(this.getRule());
        memoryRuleEClass.getESuperTypes().add(this.getRule());
        secretRuleEClass.getESuperTypes().add(this.getRule());

        initEClass(taskEClass, Task.class, "Task", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(agentEClass, Agent.class, "Agent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(orchestratorEClass, Orchestrator.class, "Orchestrator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(gitEClass, Git.class, "Git", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(mavenEClass, Maven.class, "Maven", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(llmEClass, LLM.class, "LLM", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(compilerEClass, Compiler.class, "Compiler", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(commandEClass, Command.class, "Command", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(ollamaEClass, Ollama.class, "Ollama", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(aiChatEClass, AiChat.class, "AiChat", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(neuronAIEClass, NeuronAI.class, "NeuronAI", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(evoProjectEClass, EvoProject.class, "EvoProject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(ruleEClass, Rule.class, "Rule", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(accessRuleEClass, AccessRule.class, "AccessRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(networkRuleEClass, NetworkRule.class, "NetworkRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(memoryRuleEClass, MemoryRule.class, "MemoryRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(secretRuleEClass, SecretRule.class, "SecretRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(selfDevSessionEClass, SelfDevSession.class, "SelfDevSession", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(iterationEClass, Iteration.class, "Iteration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(evaluationResultEClass, EvaluationResult.class, "EvaluationResult", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(databaseEClass, Database.class, "Database", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(fileConfigEClass, FileConfig.class, "FileConfig", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(artifactEClass, Artifact.class, "Artifact", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(lineageEClass, Lineage.class, "Lineage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(evolutionStepEClass, EvolutionStep.class, "EvolutionStep", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(mutationEClass, Mutation.class, "Mutation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(evaluationEClass, Evaluation.class, "Evaluation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(pressureEClass, Pressure.class, "Pressure", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEClass(propertyEClass, Property.class, "Property", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        createResource(eNS_URI);
    }
}
