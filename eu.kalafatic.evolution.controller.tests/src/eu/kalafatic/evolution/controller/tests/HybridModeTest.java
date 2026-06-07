package eu.kalafatic.evolution.controller.tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import eu.kalafatic.evolution.model.orchestration.AiMode;
import eu.kalafatic.evolution.model.orchestration.OrchestrationFactory;
import eu.kalafatic.evolution.model.orchestration.Orchestrator;
import eu.kalafatic.evolution.model.orchestration.Ollama;
import eu.kalafatic.evolution.controller.orchestration.TaskContext;
import eu.kalafatic.evolution.controller.orchestration.llm.LlmRouter;
import eu.kalafatic.evolution.controller.orchestration.llm.ILlmProvider;
import java.io.File;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Integration-like test for Hybrid Mode that uses Reflection to mock providers
 * and verify the three-step flow: Local Optimization -> Remote Execution -> Local Simplification.
 */
public class HybridModeTest {

    private Orchestrator orchestrator;
    private TaskContext context;
    private LlmRouter router;
    private MockProvider mockOllama;
    private MockProvider mockRemote;

    @Before
    public void setUp() throws Exception {
        orchestrator = OrchestrationFactory.eINSTANCE.createOrchestrator();
        orchestrator.setId("test-hybrid");
        orchestrator.setAiMode(AiMode.HYBRID);
        orchestrator.setRemoteModel("openai");
        orchestrator.setOpenAiToken("test-token");

        Ollama ollama = OrchestrationFactory.eINSTANCE.createOllama();
        ollama.setUrl("http://localhost:11434");
        ollama.setModel("llama3");
        orchestrator.setOllama(ollama);

        context = new TaskContext(orchestrator, new File("."));
        router = new LlmRouter();

        // Inject mock providers via reflection
        mockOllama = new MockProvider("Ollama Response");
        mockRemote = new MockProvider("Remote Response");

        injectProvider(router, "ollamaProvider", mockOllama);
        injectProvider(router, "openAiProvider", mockRemote);
    }

    private void injectProvider(LlmRouter router, String fieldName, ILlmProvider mock) throws Exception {
        Field field = LlmRouter.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(router, mock);
    }

    @Test
    public void testHybridThreeStepFlow() throws Exception {
        // Setup mock responses to simulate the flow
        // In the new evolutionary flow, optimizePromptLocally may loop up to 3 times.
        // We provide a response that will stabilize immediately to keep the test simple.
        String stablePrompt = "This is an optimized prompt that provides context and intent and is longer than fifty characters. ONLY return success.";

        mockOllama.setResponseSequence(new String[] {
            stablePrompt,               // 1st call: evolutionary optimization (Iteration 1)
            "Final Simplified Response" // 2nd call: simplification
        });
        mockRemote.setResponseSequence(new String[] {
            "Large Model Output" // call: remote execution
        });

        String result = router.sendRequest(orchestrator, "Initial User Request", 0.7f, null, context);

        // Assert the final result is the simplified one from the final step (Ollama)
        assertEquals("Final Simplified Response", result);

        // Verify call counts
        // 1 for optimization (it stabilizes because it's long and has keywords), 1 for simplification
        assertEquals(2, mockOllama.getCallCount());
        assertEquals(1, mockRemote.getCallCount());

        // Verify optimization prompt was sent to Ollama
        assertTrue(mockOllama.getReceivedPrompts()[0].contains("Initial User Request"));

        // Verify the optimized prompt was sent to Remote
        assertEquals(stablePrompt, mockRemote.getReceivedPrompts()[0]);

        // Verify simplification prompt was sent to Ollama last with the remote response
        assertTrue(mockOllama.getReceivedPrompts()[1].contains("Large Model Output"));
        assertTrue(mockOllama.getReceivedPrompts()[1].contains("simplify"));
    }

    private static class MockProvider implements ILlmProvider {
        private String[] responseSequence;
        private final AtomicInteger callCount = new AtomicInteger(0);
        private final String[] receivedPrompts = new String[5];

        public MockProvider(String singleResponse) {
            this.responseSequence = new String[] { singleResponse };
        }

        public void setResponseSequence(String[] sequence) {
            this.responseSequence = sequence;
        }

        @Override
        public String sendRequest(Orchestrator orchestrator, String prompt, float temperature, String proxyUrl, eu.kalafatic.evolution.controller.orchestration.TaskContext context) throws Exception {
            int current = callCount.getAndIncrement();
            receivedPrompts[current] = prompt;
            String response = (current < responseSequence.length) ? responseSequence[current] : "Default Response";
            System.out.println("MockProvider [" + this + "] Call " + current + " Prompt: " + (prompt.length() > 50 ? prompt.substring(0, 50) + "..." : prompt));
            System.out.println("MockProvider [" + this + "] Response: " + response);
            return response;
        }

        public int getCallCount() { return callCount.get(); }
        public String[] getReceivedPrompts() { return receivedPrompts; }
    }
}
