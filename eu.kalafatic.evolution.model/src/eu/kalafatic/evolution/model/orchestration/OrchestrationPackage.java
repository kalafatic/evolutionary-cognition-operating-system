package eu.kalafatic.evolution.model.orchestration;
import org.eclipse.emf.ecore.*;
public interface OrchestrationPackage extends EPackage {
    String eNAME = "orchestration";
    String eNS_URI = "http://eu.kalafatic.evolution/orchestration";
    String eNS_PREFIX = "orchestration";
    OrchestrationPackage eINSTANCE = eu.kalafatic.evolution.model.orchestration.impl.OrchestrationPackageImpl.init();

    int TASK = 0;
    int TASK__ID = 0;
    int TASK__NAME = 1;
    int TASK__TYPE = 2;
    int TASK__STATUS = 3;
    int TASK__NEXT = 4;
    int TASK__SUB_TASKS = 5;
    int TASK__RESPONSE = 6;
    int TASK__FEEDBACK = 7;
    int TASK__APPROVAL_REQUIRED = 8;
    int TASK__LOOP_TO_TASK_ID = 9;
    int TASK__PRIORITY = 10;
    int TASK__RESULT_SUMMARY = 11;
    int TASK__DESCRIPTION = 12;
    int TASK_FEATURE_COUNT = 13;

    int AGENT = 1;
    int AGENT__ID = 0;
    int AGENT__TYPE = 1;
    int AGENT__TASKS = 2;
    int AGENT__EXECUTION_MODE = 3;
    int AGENT__RULES = 4;
    int AGENT_FEATURE_COUNT = 5;

    int ORCHESTRATOR = 2;
    int ORCHESTRATOR__ID = 0;
    int ORCHESTRATOR__NAME = 1;
    int ORCHESTRATOR__AGENTS = 2;
    int ORCHESTRATOR__TASKS = 3;
    int ORCHESTRATOR__GIT = 4;
    int ORCHESTRATOR__MAVEN = 5;
    int ORCHESTRATOR__LLM = 6;
    int ORCHESTRATOR__COMPILER = 7;
    int ORCHESTRATOR__OLLAMA = 8;
    int ORCHESTRATOR__AI_CHAT = 9;
    int ORCHESTRATOR__NEURON_AI = 10;
    int ORCHESTRATOR__REMOTE_MODEL = 11;
    int ORCHESTRATOR__AI_MODE = 12;
    int ORCHESTRATOR__MCP_SERVER_URL = 13;
    int ORCHESTRATOR__OPEN_AI_TOKEN = 14;
    int ORCHESTRATOR__OPEN_AI_MODEL = 15;
    int ORCHESTRATOR__LOCAL_MODEL = 16;
    int ORCHESTRATOR__HYBRID_MODEL = 17;
    int ORCHESTRATOR__OFFLINE_MODE = 18;
    int ORCHESTRATOR__SELF_DEV_SESSION = 19;
    int ORCHESTRATOR__DATABASE = 20;
    int ORCHESTRATOR__FILE_CONFIG = 21;
    int ORCHESTRATOR__SHARED_MEMORY = 22;
    int ORCHESTRATOR__LINEAGES = 23;
    int ORCHESTRATOR__PRESSURES = 24;
    int ORCHESTRATOR_FEATURE_COUNT = 25;

    int GIT = 3;
    int GIT__REPOSITORY_URL = 0;
    int GIT__BRANCH = 1;
    int GIT__USERNAME = 2;
    int GIT__LOCAL_PATH = 3;
    int GIT_FEATURE_COUNT = 4;

    int MAVEN = 4;
    int MAVEN__GOALS = 0;
    int MAVEN__PROFILES = 1;
    int MAVEN_FEATURE_COUNT = 2;

    int LLM = 5;
    int LLM__MODEL = 0;
    int LLM__TEMPERATURE = 1;
    int LLM_FEATURE_COUNT = 2;

    int COMPILER = 6;
    int COMPILER__SOURCE_VERSION = 0;
    int COMPILER__TARGET_VERSION = 1;
    int COMPILER_FEATURE_COUNT = 2;

    int COMMAND = 7;
    int COMMAND__NAME = 0;
    int COMMAND__STATUS = 1;
    int COMMAND_FEATURE_COUNT = 2;

    int OLLAMA = 8;
    int OLLAMA__URL = 0;
    int OLLAMA__MODEL = 1;
    int OLLAMA__PATH = 2;
    int OLLAMA_FEATURE_COUNT = 3;

    int AI_CHAT = 9;
    int AI_CHAT__URL = 0;
    int AI_CHAT__TOKEN = 1;
    int AI_CHAT__PROMPT = 2;
    int AI_CHAT__PROXY_URL = 3;
    int AI_CHAT_FEATURE_COUNT = 4;

    int NEURON_AI = 10;
    int NEURON_AI__URL = 0;
    int NEURON_AI__MODEL = 1;
    int NEURON_AI__TYPE = 2;
    int NEURON_AI__TRAINING_DATA = 3;
    int NEURON_AI_FEATURE_COUNT = 4;

    int EVO_PROJECT = 11;
    int EVO_PROJECT__NAME = 0;
    int EVO_PROJECT__ORCHESTRATIONS = 1;
    int EVO_PROJECT__GLOBAL_LINEAGES = 2;
    int EVO_PROJECT_FEATURE_COUNT = 3;

    int RULE = 12;
    int RULE__NAME = 0;
    int RULE__DESCRIPTION = 1;
    int RULE_FEATURE_COUNT = 2;

    int ACCESS_RULE = 13;
    int ACCESS_RULE__NAME = 0;
    int ACCESS_RULE__DESCRIPTION = 1;
    int ACCESS_RULE__ALLOWED_PATHS = 2;
    int ACCESS_RULE__DENIED_PATHS = 3;
    int ACCESS_RULE_FEATURE_COUNT = 4;

    int NETWORK_RULE = 14;
    int NETWORK_RULE__NAME = 0;
    int NETWORK_RULE__DESCRIPTION = 1;
    int NETWORK_RULE__ALLOWED_DOMAINS = 2;
    int NETWORK_RULE__ALLOW_ALL = 3;
    int NETWORK_RULE_FEATURE_COUNT = 4;

    int MEMORY_RULE = 15;
    int MEMORY_RULE__NAME = 0;
    int MEMORY_RULE__DESCRIPTION = 1;
    int MEMORY_RULE__STORAGE_LIMIT = 2;
    int MEMORY_RULE__RETENTION_PERIOD = 3;
    int MEMORY_RULE_FEATURE_COUNT = 4;

    int SECRET_RULE = 16;
    int SECRET_RULE__NAME = 0;
    int SECRET_RULE__DESCRIPTION = 1;
    int SECRET_RULE__ALLOWED_SECRETS = 2;
    int SECRET_RULE_FEATURE_COUNT = 3;

    int SELF_DEV_SESSION = 17;
    int SELF_DEV_SESSION__ID = 0;
    int SELF_DEV_SESSION__START_TIME = 1;
    int SELF_DEV_SESSION__MAX_ITERATIONS = 2;
    int SELF_DEV_SESSION__STATUS = 3;
    int SELF_DEV_SESSION__ITERATIONS = 4;
    int SELF_DEV_SESSION_FEATURE_COUNT = 5;

    int ITERATION = 18;
    int ITERATION__ID = 0;
    int ITERATION__BRANCH_NAME = 1;
    int ITERATION__TASKS = 2;
    int ITERATION__EVALUATION_RESULT = 3;
    int ITERATION__STATUS = 4;
    int ITERATION_FEATURE_COUNT = 5;

    int EVALUATION_RESULT = 19;
    int EVALUATION_RESULT__SUCCESS = 0;
    int EVALUATION_RESULT__TEST_PASS_RATE = 1;
    int EVALUATION_RESULT__COVERAGE_CHANGE = 2;
    int EVALUATION_RESULT__ERRORS = 3;
    int EVALUATION_RESULT__DECISION = 4;
    int EVALUATION_RESULT_FEATURE_COUNT = 5;

    int DATABASE = 20;
    int DATABASE__URL = 0;
    int DATABASE__USERNAME = 1;
    int DATABASE__PASSWORD = 2;
    int DATABASE__DRIVER = 3;
    int DATABASE_FEATURE_COUNT = 4;

    int FILE_CONFIG = 21;
    int FILE_CONFIG__LOCAL_PATH = 0;
    int FILE_CONFIG_FEATURE_COUNT = 1;

    int ARTIFACT = 22;
    int ARTIFACT__ID = 0;
    int ARTIFACT__TYPE = 1;
    int ARTIFACT__CONTENT = 2;
    int ARTIFACT__PROPERTIES = 3;
    int ARTIFACT_FEATURE_COUNT = 4;

    int LINEAGE = 23;
    int LINEAGE__ID = 0;
    int LINEAGE__SURVIVOR = 1;
    int LINEAGE__CANDIDATES = 2;
    int LINEAGE__HISTORY = 3;
    int LINEAGE_FEATURE_COUNT = 4;

    int EVOLUTION_STEP = 24;
    int EVOLUTION_STEP__TIMESTAMP = 0;
    int EVOLUTION_STEP__MUTATION = 1;
    int EVOLUTION_STEP__EVALUATIONS = 2;
    int EVOLUTION_STEP__SELECTED_SURVIVOR = 3;
    int EVOLUTION_STEP_FEATURE_COUNT = 4;

    int MUTATION = 25;
    int MUTATION__DESCRIPTION = 0;
    int MUTATION__TYPE = 1;
    int MUTATION_FEATURE_COUNT = 2;

    int EVALUATION = 26;
    int EVALUATION__PRESSURE = 0;
    int EVALUATION__SCORE = 1;
    int EVALUATION__COMMENT = 2;
    int EVALUATION_FEATURE_COUNT = 3;

    int PRESSURE = 27;
    int PRESSURE__NAME = 0;
    int PRESSURE__DESCRIPTION = 1;
    int PRESSURE_FEATURE_COUNT = 2;

    int PROPERTY = 28;
    int PROPERTY__KEY = 0;
    int PROPERTY__VALUE = 1;
    int PROPERTY_FEATURE_COUNT = 2;

    int TASK_STATUS = 29;
    int COMMAND_STATUS = 30;
    int EXECUTION_MODE = 31;
    int NEURON_TYPE = 32;
    int AI_MODE = 33;
    int SELF_DEV_STATUS = 34;
    int ITERATION_STATUS = 35;
    int SELF_DEV_DECISION = 36;

    EClass getTask();
    EAttribute getTask_Id();
    EAttribute getTask_Name();
    EAttribute getTask_Type();
    EAttribute getTask_Status();
    EReference getTask_Next();
    EReference getTask_SubTasks();
    EAttribute getTask_Response();
    EAttribute getTask_Feedback();
    EAttribute getTask_ApprovalRequired();
    EAttribute getTask_LoopToTaskId();
    EAttribute getTask_Priority();
    EAttribute getTask_ResultSummary();
    EAttribute getTask_Description();

    EClass getAgent();
    EAttribute getAgent_Id();
    EAttribute getAgent_Type();
    EReference getAgent_Tasks();
    EAttribute getAgent_ExecutionMode();
    EReference getAgent_Rules();

    EClass getOrchestrator();
    EAttribute getOrchestrator_Id();
    EAttribute getOrchestrator_Name();
    EReference getOrchestrator_Agents();
    EReference getOrchestrator_Tasks();
    EReference getOrchestrator_Git();
    EReference getOrchestrator_Maven();
    EReference getOrchestrator_Llm();
    EReference getOrchestrator_Compiler();
    EReference getOrchestrator_Ollama();
    EReference getOrchestrator_AiChat();
    EReference getOrchestrator_NeuronAI();
    EAttribute getOrchestrator_RemoteModel();
    EAttribute getOrchestrator_AiMode();
    EAttribute getOrchestrator_McpServerUrl();
    EAttribute getOrchestrator_OpenAiToken();
    EAttribute getOrchestrator_OpenAiModel();
    EAttribute getOrchestrator_LocalModel();
    EAttribute getOrchestrator_HybridModel();
    EAttribute getOrchestrator_OfflineMode();
    EReference getOrchestrator_SelfDevSession();
    EReference getOrchestrator_Database();
    EReference getOrchestrator_FileConfig();
    EAttribute getOrchestrator_SharedMemory();
    EReference getOrchestrator_Lineages();
    EReference getOrchestrator_Pressures();

    EClass getGit();
    EAttribute getGit_RepositoryUrl();
    EAttribute getGit_Branch();
    EAttribute getGit_Username();
    EAttribute getGit_LocalPath();

    EClass getMaven();
    EAttribute getMaven_Goals();
    EAttribute getMaven_Profiles();

    EClass getLLM();
    EAttribute getLLM_Model();
    EAttribute getLLM_Temperature();

    EClass getCompiler();
    EAttribute getCompiler_SourceVersion();
    EAttribute getCompiler_TargetVersion();

    EClass getCommand();
    EAttribute getCommand_Name();
    EAttribute getCommand_Status();

    EClass getOllama();
    EAttribute getOllama_Url();
    EAttribute getOllama_Model();
    EAttribute getOllama_Path();

    EClass getAiChat();
    EAttribute getAiChat_Url();
    EAttribute getAiChat_Token();
    EAttribute getAiChat_Prompt();
    EAttribute getAiChat_ProxyUrl();

    EClass getNeuronAI();
    EAttribute getNeuronAI_Url();
    EAttribute getNeuronAI_Model();
    EAttribute getNeuronAI_Type();
    EAttribute getNeuronAI_TrainingData();

    EClass getEvoProject();
    EAttribute getEvoProject_Name();
    EReference getEvoProject_Orchestrations();
    EReference getEvoProject_GlobalLineages();

    EClass getRule();
    EAttribute getRule_Name();
    EAttribute getRule_Description();

    EClass getAccessRule();
    EAttribute getAccessRule_AllowedPaths();
    EAttribute getAccessRule_DeniedPaths();

    EClass getNetworkRule();
    EAttribute getNetworkRule_AllowedDomains();
    EAttribute getNetworkRule_AllowAll();

    EClass getMemoryRule();
    EAttribute getMemoryRule_StorageLimit();
    EAttribute getMemoryRule_RetentionPeriod();

    EClass getSecretRule();
    EAttribute getSecretRule_AllowedSecrets();

    EClass getSelfDevSession();
    EAttribute getSelfDevSession_Id();
    EAttribute getSelfDevSession_StartTime();
    EAttribute getSelfDevSession_MaxIterations();
    EAttribute getSelfDevSession_Status();
    EReference getSelfDevSession_Iterations();

    EClass getIteration();
    EAttribute getIteration_Id();
    EAttribute getIteration_BranchName();
    EReference getIteration_Tasks();
    EReference getIteration_EvaluationResult();
    EAttribute getIteration_Status();

    EClass getEvaluationResult();
    EAttribute getEvaluationResult_Success();
    EAttribute getEvaluationResult_TestPassRate();
    EAttribute getEvaluationResult_CoverageChange();
    EAttribute getEvaluationResult_Errors();
    EAttribute getEvaluationResult_Decision();

    EClass getDatabase();
    EAttribute getDatabase_Url();
    EAttribute getDatabase_Username();
    EAttribute getDatabase_Password();
    EAttribute getDatabase_Driver();

    EClass getFileConfig();
    EAttribute getFileConfig_LocalPath();

    EClass getArtifact();
    EAttribute getArtifact_Id();
    EAttribute getArtifact_Type();
    EAttribute getArtifact_Content();
    EReference getArtifact_Properties();

    EClass getLineage();
    EAttribute getLineage_Id();
    EReference getLineage_Survivor();
    EReference getLineage_Candidates();
    EReference getLineage_History();

    EClass getEvolutionStep();
    EAttribute getEvolutionStep_Timestamp();
    EReference getEvolutionStep_Mutation();
    EReference getEvolutionStep_Evaluations();
    EReference getEvolutionStep_SelectedSurvivor();

    EClass getMutation();
    EAttribute getMutation_Description();
    EAttribute getMutation_Type();

    EClass getEvaluation();
    EReference getEvaluation_Pressure();
    EAttribute getEvaluation_Score();
    EAttribute getEvaluation_Comment();

    EClass getPressure();
    EAttribute getPressure_Name();
    EAttribute getPressure_Description();

    EClass getProperty();
    EAttribute getProperty_Key();
    EAttribute getProperty_Value();

    EEnum getTaskStatus();
    EEnum getCommandStatus();
    EEnum getExecutionMode();
    EEnum getNeuronType();
    EEnum getAiMode();
    EEnum getSelfDevStatus();
    EEnum getIterationStatus();
    EEnum getSelfDevDecision();

    OrchestrationFactory getOrchestrationFactory();

    interface Literals {
        EClass TASK = eINSTANCE.getTask();
        EAttribute TASK__ID = eINSTANCE.getTask_Id();
        EAttribute TASK__NAME = eINSTANCE.getTask_Name();
        EAttribute TASK__TYPE = eINSTANCE.getTask_Type();
        EAttribute TASK__STATUS = eINSTANCE.getTask_Status();
        EReference TASK__NEXT = eINSTANCE.getTask_Next();
        EReference TASK__SUB_TASKS = eINSTANCE.getTask_SubTasks();
        EAttribute TASK__RESPONSE = eINSTANCE.getTask_Response();
        EAttribute TASK__FEEDBACK = eINSTANCE.getTask_Feedback();
        EAttribute TASK__APPROVAL_REQUIRED = eINSTANCE.getTask_ApprovalRequired();
        EAttribute TASK__LOOP_TO_TASK_ID = eINSTANCE.getTask_LoopToTaskId();
        EAttribute TASK__PRIORITY = eINSTANCE.getTask_Priority();
        EAttribute TASK__RESULT_SUMMARY = eINSTANCE.getTask_ResultSummary();
        EAttribute TASK__DESCRIPTION = eINSTANCE.getTask_Description();

        EClass AGENT = eINSTANCE.getAgent();
        EAttribute AGENT__ID = eINSTANCE.getAgent_Id();
        EAttribute AGENT__TYPE = eINSTANCE.getAgent_Type();
        EReference AGENT__TASKS = eINSTANCE.getAgent_Tasks();
        EAttribute AGENT__EXECUTION_MODE = eINSTANCE.getAgent_ExecutionMode();
        EReference AGENT__RULES = eINSTANCE.getAgent_Rules();

        EClass ORCHESTRATOR = eINSTANCE.getOrchestrator();
        EAttribute ORCHESTRATOR__ID = eINSTANCE.getOrchestrator_Id();
        EAttribute ORCHESTRATOR__NAME = eINSTANCE.getOrchestrator_Name();
        EReference ORCHESTRATOR__AGENTS = eINSTANCE.getOrchestrator_Agents();
        EReference ORCHESTRATOR__TASKS = eINSTANCE.getOrchestrator_Tasks();
        EReference ORCHESTRATOR__GIT = eINSTANCE.getOrchestrator_Git();
        EReference ORCHESTRATOR__MAVEN = eINSTANCE.getOrchestrator_Maven();
        EReference ORCHESTRATOR__LLM = eINSTANCE.getOrchestrator_Llm();
        EReference ORCHESTRATOR__COMPILER = eINSTANCE.getOrchestrator_Compiler();
        EReference ORCHESTRATOR__OLLAMA = eINSTANCE.getOrchestrator_Ollama();
        EReference ORCHESTRATOR__AI_CHAT = eINSTANCE.getOrchestrator_AiChat();
        EReference ORCHESTRATOR__NEURON_AI = eINSTANCE.getOrchestrator_NeuronAI();
        EAttribute ORCHESTRATOR__REMOTE_MODEL = eINSTANCE.getOrchestrator_RemoteModel();
        EAttribute ORCHESTRATOR__AI_MODE = eINSTANCE.getOrchestrator_AiMode();
        EAttribute ORCHESTRATOR__MCP_SERVER_URL = eINSTANCE.getOrchestrator_McpServerUrl();
        EAttribute ORCHESTRATOR__OPEN_AI_TOKEN = eINSTANCE.getOrchestrator_OpenAiToken();
        EAttribute ORCHESTRATOR__OPEN_AI_MODEL = eINSTANCE.getOrchestrator_OpenAiModel();
        EAttribute ORCHESTRATOR__LOCAL_MODEL = eINSTANCE.getOrchestrator_LocalModel();
        EAttribute ORCHESTRATOR__HYBRID_MODEL = eINSTANCE.getOrchestrator_HybridModel();
        EAttribute ORCHESTRATOR__OFFLINE_MODE = eINSTANCE.getOrchestrator_OfflineMode();
        EReference ORCHESTRATOR__SELF_DEV_SESSION = eINSTANCE.getOrchestrator_SelfDevSession();
        EReference ORCHESTRATOR__DATABASE = eINSTANCE.getOrchestrator_Database();
        EReference ORCHESTRATOR__FILE_CONFIG = eINSTANCE.getOrchestrator_FileConfig();
        EAttribute ORCHESTRATOR__SHARED_MEMORY = eINSTANCE.getOrchestrator_SharedMemory();
        EReference ORCHESTRATOR__LINEAGES = eINSTANCE.getOrchestrator_Lineages();
        EReference ORCHESTRATOR__PRESSURES = eINSTANCE.getOrchestrator_Pressures();

        EClass GIT = eINSTANCE.getGit();
        EAttribute GIT__REPOSITORY_URL = eINSTANCE.getGit_RepositoryUrl();
        EAttribute GIT__BRANCH = eINSTANCE.getGit_Branch();
        EAttribute GIT__USERNAME = eINSTANCE.getGit_Username();
        EAttribute GIT__LOCAL_PATH = eINSTANCE.getGit_LocalPath();

        EClass MAVEN = eINSTANCE.getMaven();
        EAttribute MAVEN__GOALS = eINSTANCE.getMaven_Goals();
        EAttribute MAVEN__PROFILES = eINSTANCE.getMaven_Profiles();

        EClass LLM = eINSTANCE.getLLM();
        EAttribute LLM__MODEL = eINSTANCE.getLLM_Model();
        EAttribute LLM__TEMPERATURE = eINSTANCE.getLLM_Temperature();

        EClass COMPILER = eINSTANCE.getCompiler();
        EAttribute COMPILER__SOURCE_VERSION = eINSTANCE.getCompiler_SourceVersion();
        EAttribute COMPILER__TARGET_VERSION = eINSTANCE.getCompiler_TargetVersion();

        EClass COMMAND = eINSTANCE.getCommand();
        EAttribute COMMAND__NAME = eINSTANCE.getCommand_Name();
        EAttribute COMMAND__STATUS = eINSTANCE.getCommand_Status();

        EClass OLLAMA = eINSTANCE.getOllama();
        EAttribute OLLAMA__URL = eINSTANCE.getOllama_Url();
        EAttribute OLLAMA__MODEL = eINSTANCE.getOllama_Model();
        EAttribute OLLAMA__PATH = eINSTANCE.getOllama_Path();

        EClass AI_CHAT = eINSTANCE.getAiChat();
        EAttribute AI_CHAT__URL = eINSTANCE.getAiChat_Url();
        EAttribute AI_CHAT__TOKEN = eINSTANCE.getAiChat_Token();
        EAttribute AI_CHAT__PROMPT = eINSTANCE.getAiChat_Prompt();
        EAttribute AI_CHAT__PROXY_URL = eINSTANCE.getAiChat_ProxyUrl();

        EClass NEURON_AI = eINSTANCE.getNeuronAI();
        EAttribute NEURON_AI__URL = eINSTANCE.getNeuronAI_Url();
        EAttribute NEURON_AI__MODEL = eINSTANCE.getNeuronAI_Model();
        EAttribute NEURON_AI__TYPE = eINSTANCE.getNeuronAI_Type();
        EAttribute NEURON_AI__TRAINING_DATA = eINSTANCE.getNeuronAI_TrainingData();

        EClass EVO_PROJECT = eINSTANCE.getEvoProject();
        EAttribute EVO_PROJECT__NAME = eINSTANCE.getEvoProject_Name();
        EReference EVO_PROJECT__ORCHESTRATIONS = eINSTANCE.getEvoProject_Orchestrations();
        EReference EVO_PROJECT__GLOBAL_LINEAGES = eINSTANCE.getEvoProject_GlobalLineages();

        EClass RULE = eINSTANCE.getRule();
        EAttribute RULE__NAME = eINSTANCE.getRule_Name();
        EAttribute RULE__DESCRIPTION = eINSTANCE.getRule_Description();

        EClass ACCESS_RULE = eINSTANCE.getAccessRule();
        EAttribute ACCESS_RULE__NAME = eINSTANCE.getRule_Name();
        EAttribute ACCESS_RULE__DESCRIPTION = eINSTANCE.getRule_Description();
        EAttribute ACCESS_RULE__ALLOWED_PATHS = eINSTANCE.getAccessRule_AllowedPaths();
        EAttribute ACCESS_RULE__DENIED_PATHS = eINSTANCE.getAccessRule_DeniedPaths();

        EClass NETWORK_RULE = eINSTANCE.getNetworkRule();
        EAttribute NETWORK_RULE__NAME = eINSTANCE.getRule_Name();
        EAttribute NETWORK_RULE__DESCRIPTION = eINSTANCE.getRule_Description();
        EAttribute NETWORK_RULE__ALLOWED_DOMAINS = eINSTANCE.getNetworkRule_AllowedDomains();
        EAttribute NETWORK_RULE__ALLOW_ALL = eINSTANCE.getNetworkRule_AllowAll();

        EClass MEMORY_RULE = eINSTANCE.getMemoryRule();
        EAttribute MEMORY_RULE__NAME = eINSTANCE.getRule_Name();
        EAttribute MEMORY_RULE__DESCRIPTION = eINSTANCE.getRule_Description();
        EAttribute MEMORY_RULE__STORAGE_LIMIT = eINSTANCE.getMemoryRule_StorageLimit();
        EAttribute MEMORY_RULE__RETENTION_PERIOD = eINSTANCE.getMemoryRule_RetentionPeriod();

        EClass SECRET_RULE = eINSTANCE.getSecretRule();
        EAttribute SECRET_RULE__NAME = eINSTANCE.getRule_Name();
        EAttribute SECRET_RULE__DESCRIPTION = eINSTANCE.getRule_Description();
        EAttribute SECRET_RULE__ALLOWED_SECRETS = eINSTANCE.getSecretRule_AllowedSecrets();

        EClass SELF_DEV_SESSION = eINSTANCE.getSelfDevSession();
        EAttribute SELF_DEV_SESSION__ID = eINSTANCE.getSelfDevSession_Id();
        EAttribute SELF_DEV_SESSION__START_TIME = eINSTANCE.getSelfDevSession_StartTime();
        EAttribute SELF_DEV_SESSION__MAX_ITERATIONS = eINSTANCE.getSelfDevSession_MaxIterations();
        EAttribute SELF_DEV_SESSION__STATUS = eINSTANCE.getSelfDevSession_Status();
        EReference SELF_DEV_SESSION__ITERATIONS = eINSTANCE.getSelfDevSession_Iterations();

        EClass ITERATION = eINSTANCE.getIteration();
        EAttribute ITERATION__ID = eINSTANCE.getIteration_Id();
        EAttribute ITERATION__BRANCH_NAME = eINSTANCE.getIteration_BranchName();
        EReference ITERATION__TASKS = eINSTANCE.getIteration_Tasks();
        EReference ITERATION__EVALUATION_RESULT = eINSTANCE.getIteration_EvaluationResult();
        EAttribute ITERATION__STATUS = eINSTANCE.getIteration_Status();

        EClass EVALUATION_RESULT = eINSTANCE.getEvaluationResult();
        EAttribute EVALUATION_RESULT__SUCCESS = eINSTANCE.getEvaluationResult_Success();
        EAttribute EVALUATION_RESULT__TEST_PASS_RATE = eINSTANCE.getEvaluationResult_TestPassRate();
        EAttribute EVALUATION_RESULT__COVERAGE_CHANGE = eINSTANCE.getEvaluationResult_CoverageChange();
        EAttribute EVALUATION_RESULT__ERRORS = eINSTANCE.getEvaluationResult_Errors();
        EAttribute EVALUATION_RESULT__DECISION = eINSTANCE.getEvaluationResult_Decision();

        EClass DATABASE = eINSTANCE.getDatabase();
        EAttribute DATABASE__URL = eINSTANCE.getDatabase_Url();
        EAttribute DATABASE__USERNAME = eINSTANCE.getDatabase_Username();
        EAttribute DATABASE__PASSWORD = eINSTANCE.getDatabase_Password();
        EAttribute DATABASE__DRIVER = eINSTANCE.getDatabase_Driver();

        EClass FILE_CONFIG = eINSTANCE.getFileConfig();
        EAttribute FILE_CONFIG__LOCAL_PATH = eINSTANCE.getFileConfig_LocalPath();

        EClass ARTIFACT = eINSTANCE.getArtifact();
        EAttribute ARTIFACT__ID = eINSTANCE.getArtifact_Id();
        EAttribute ARTIFACT__TYPE = eINSTANCE.getArtifact_Type();
        EAttribute ARTIFACT__CONTENT = eINSTANCE.getArtifact_Content();
        EReference ARTIFACT__PROPERTIES = eINSTANCE.getArtifact_Properties();

        EClass LINEAGE = eINSTANCE.getLineage();
        EAttribute LINEAGE__ID = eINSTANCE.getLineage_Id();
        EReference LINEAGE__SURVIVOR = eINSTANCE.getLineage_Survivor();
        EReference LINEAGE__CANDIDATES = eINSTANCE.getLineage_Candidates();
        EReference LINEAGE__HISTORY = eINSTANCE.getLineage_History();

        EClass EVOLUTION_STEP = eINSTANCE.getEvolutionStep();
        EAttribute EVOLUTION_STEP__TIMESTAMP = eINSTANCE.getEvolutionStep_Timestamp();
        EReference EVOLUTION_STEP__MUTATION = eINSTANCE.getEvolutionStep_Mutation();
        EReference EVOLUTION_STEP__EVALUATIONS = eINSTANCE.getEvolutionStep_Evaluations();
        EReference EVOLUTION_STEP__SELECTED_SURVIVOR = eINSTANCE.getEvolutionStep_SelectedSurvivor();

        EClass MUTATION = eINSTANCE.getMutation();
        EAttribute MUTATION__DESCRIPTION = eINSTANCE.getMutation_Description();
        EAttribute MUTATION__TYPE = eINSTANCE.getMutation_Type();

        EClass EVALUATION = eINSTANCE.getEvaluation();
        EReference EVALUATION__PRESSURE = eINSTANCE.getEvaluation_Pressure();
        EAttribute EVALUATION__SCORE = eINSTANCE.getEvaluation_Score();
        EAttribute EVALUATION__COMMENT = eINSTANCE.getEvaluation_Comment();

        EClass PRESSURE = eINSTANCE.getPressure();
        EAttribute PRESSURE__NAME = eINSTANCE.getPressure_Name();
        EAttribute PRESSURE__DESCRIPTION = eINSTANCE.getPressure_Description();

        EClass PROPERTY = eINSTANCE.getProperty();
        EAttribute PROPERTY__KEY = eINSTANCE.getProperty_Key();
        EAttribute PROPERTY__VALUE = eINSTANCE.getProperty_Value();
    }
}
