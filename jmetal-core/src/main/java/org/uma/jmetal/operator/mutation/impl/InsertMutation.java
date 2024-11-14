package org.uma.jmetal.operator.mutation.impl;

import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.solution.permutationsolution.PermutationSolution;
import org.uma.jmetal.util.errorchecking.Check;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

/**
 * Insert Mutation operator for permutation-based genetic algorithms.
 * This operator removes an element from a random position and inserts it at another random position.
 * @author Nicolás R. Uribe
 */
public class InsertMutation<T> implements MutationOperator<PermutationSolution<T>> {
    private double mutationProbability;
    private final JMetalRandom randomNumberGenerator = JMetalRandom.getInstance();

    public InsertMutation(double mutationProbability) {
        Check.probabilityIsValid(mutationProbability);
        this.mutationProbability = mutationProbability;
    }

    public double mutationProbability() {
        return this.mutationProbability;
    }

    public void setMutationProbability(double mutationProbability) {
        Check.probabilityIsValid(mutationProbability);
        this.mutationProbability = mutationProbability;
    }

    @Override
    public PermutationSolution<T> execute(PermutationSolution<T> solution) {
        Check.notNull(solution);
        this.doMutation(solution);
        return solution;
    }

    public void doMutation(PermutationSolution<T> solution) {
        int permutationLength = solution.variables().size();

        // Proceed only if the permutation length is greater than 1 and mutation should be applied
        if (permutationLength > 1 && randomNumberGenerator.nextDouble() < this.mutationProbability) {

            // Select two distinct positions in the permutation
            int pos1 = randomNumberGenerator.nextInt(0, permutationLength - 1);
            int pos2 = randomNumberGenerator.nextInt(0, permutationLength - 1);

            // Ensure that pos1 and pos2 are different
            while (pos1 == pos2) {
                pos2 = randomNumberGenerator.nextInt(0, permutationLength - 1);
            }

            // Get the value to insert from position pos1
            T valueToInsert = solution.variables().get(pos1);

            // Remove the value from its original position
            solution.variables().remove(pos1);

            // Adjust pos2 if necessary and insert the value at the new position
            if (pos1 < pos2) {
                // Since we've removed an element before pos2, we need to adjust pos2
                solution.variables().add(pos2 - 1, valueToInsert);
            } else {
                solution.variables().add(pos2, valueToInsert);
            }
        }
    }
}
