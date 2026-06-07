package eu.kalafatic.evolution.controller.orchestration;

import eu.kalafatic.evolution.model.orchestration.Artifact;
import eu.kalafatic.evolution.model.orchestration.Evaluation;
import eu.kalafatic.evolution.model.orchestration.EvolutionDecision;
import eu.kalafatic.evolution.model.orchestration.Lineage;
import eu.kalafatic.evolution.model.orchestration.Pressure;
import eu.kalafatic.evolution.model.orchestration.SelfDevDecision;

/**
 * The core ECOS Evolution Kernel interface.
 */
public interface IEvolutionKernel {

    Artifact evolve(Lineage lineage, Pressure pressure, IEvolutionEnvironment environment, TaskContext context) throws Exception;

    void applyPressure(Pressure pressure, TaskContext context);

    EvolutionDecision analyze(Artifact artifact, Evaluation evaluation, TaskContext context);

    Artifact selectTarget(Lineage lineage, EvolutionDecision decision, TaskContext context);

    SelfDevDecision decideStrategicAction(Lineage lineage, Evaluation evaluation, TaskContext context);
}
