package eu.kalafatic.evolution.controller.headless;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import eu.kalafatic.evolution.controller.orchestration.*;
import eu.kalafatic.evolution.controller.orchestration.selfdev.BranchVariant;
import eu.kalafatic.evolution.controller.orchestration.selfdev.DarwinEngine;
import eu.kalafatic.evolution.controller.registry.PluginLoader;
import eu.kalafatic.evolution.model.orchestration.OrchestrationFactory;
import eu.kalafatic.evolution.model.orchestration.Orchestrator;

/**
 * Headless server that listens for Darwin proposal requests from the standalone supervisor.
 */
public class HeadlessEvolutionServer {
    private final File projectRoot;
    private final File runDir;
    private final IterationManager manager;

    public HeadlessEvolutionServer(File projectRoot) {
        this.projectRoot = projectRoot;
        this.runDir = new File(projectRoot, "self-dev-run");
        if (!runDir.exists()) runDir.mkdirs();

        // Load plugins
        new PluginLoader().loadDefaults();

        // Initialize enough context for DarwinEngine
        Orchestrator orchestrator = OrchestrationFactory.eINSTANCE.createOrchestrator();
        TaskContext context = new TaskContext(orchestrator, projectRoot);
        context.setSessionId("headless-session-" + System.currentTimeMillis());
        this.manager = KernelFactory.create(context, SessionManager.getInstance().getOrCreateSession(context.getSessionId()), new AiService());
    }

    public void start() {
        System.out.println("[HEADLESS] Starting Darwin Evolution Server...");
        try {
            while (true) {
                File requestFile = new File(runDir, "darwin-request.json");
                if (requestFile.exists()) {
                    System.out.println("[HEADLESS] Request detected. Generating proposals...");
                    handleRequest(requestFile);
                }
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            System.err.println("[HEADLESS] Server loop failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleRequest(File requestFile) {
        try {
            String content = new String(Files.readAllBytes(requestFile.toPath()));
            JSONObject requestJson = new JSONObject(content);
            String goal = requestJson.getString("goal");
            int iteration = requestJson.getInt("iteration");

            System.out.println("[HEADLESS] Goal: " + goal + " (Iteration: " + iteration + ")");

            List<BranchVariant> variants = manager.getDarwinEngine().generateVariants(goal, null, null, null, null);

            writeResponse(variants);
            requestFile.delete();
            System.out.println("[HEADLESS] Response written and request cleared.");
        } catch (Exception e) {
            System.err.println("[HEADLESS] Failed to handle request: " + e.getMessage());
        }
    }

    private void writeResponse(List<BranchVariant> variants) throws IOException {
        JSONObject response = new JSONObject();
        response.put("protocolVersion", "1.0.0");
        JSONArray proposals = new JSONArray();
        for (BranchVariant v : variants) {
            JSONObject p = new JSONObject();
            p.put("id", v.getId());
            p.put("strategy", v.getStrategy());
            p.put("diff", v.getMutationTrace()); // Assuming trace contains the diff/patch
            p.put("score", v.getScore());
            proposals.put(p);
        }
        response.put("proposals", proposals);
        Files.write(new File(runDir, "darwin-response.json").toPath(), response.toString(4).getBytes());
    }

    public static void main(String[] args) {
        String path = args.length > 0 ? args[0] : ".";
        new HeadlessEvolutionServer(new File(path)).start();
    }
}
