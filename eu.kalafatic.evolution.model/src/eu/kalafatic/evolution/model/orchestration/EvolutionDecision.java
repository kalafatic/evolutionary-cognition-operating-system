package eu.kalafatic.evolution.model.orchestration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.common.util.Enumerator;

public enum EvolutionDecision implements Enumerator {
    MUTATE(0, "MUTATE", "MUTATE"),
    STABILIZE(1, "STABILIZE", "STABILIZE"),
    BACKTRACK(2, "BACKTRACK", "BACKTRACK"),
    ABORT(3, "ABORT", "ABORT");

    public static final int MUTATE_VALUE = 0;
    public static final int STABILIZE_VALUE = 1;
    public static final int BACKTRACK_VALUE = 2;
    public static final int ABORT_VALUE = 3;

    private static final EvolutionDecision[] VALUES_ARRAY = new EvolutionDecision[] { MUTATE, STABILIZE, BACKTRACK, ABORT };
    public static final List<EvolutionDecision> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    public static EvolutionDecision get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            EvolutionDecision result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) return result;
        }
        return null;
    }

    public static EvolutionDecision get(int value) {
        switch (value) {
            case MUTATE_VALUE: return MUTATE;
            case STABILIZE_VALUE: return STABILIZE;
            case BACKTRACK_VALUE: return BACKTRACK;
            case ABORT_VALUE: return ABORT;
        }
        return null;
    }

    private final int value;
    private final String name;
    private final String literal;

    private EvolutionDecision(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    @Override public int getValue() { return value; }
    @Override public String getName() { return name; }
    @Override public String getLiteral() { return literal; }
    @Override public String toString() { return literal; }
}
