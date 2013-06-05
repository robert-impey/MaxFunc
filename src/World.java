
/**
 * The world in which the maximum of a complex function is sought
 *
 * @author Rob Impey
 */
public final class World {
    // Instance fields and class constants
    // -----------------------------------

    private final int parentsPopulationSize, childrenPopulationSize;
    private final int generations;
    private final double selectionRate, mutationRate;
    private final double[] meanFitnesses; // To store the mean fitness of each generation
    private final Phenotype[] bestIndividuals; // To store the best individual from each generation

    // The constructors
    // ----------------
    /**
     * @param _parentsPopulationSize The size of the parent population
     * @param _childrenPopulationSize The size of the children population
     * @param _generations The number of generations to evolve the populations
     * for
     * @param _selectionRate The selection rate as a percentage
     * @param _mutationRate The mutation rate
     */
    public World(int _parentsPopulationSize,
            int _childrenPopulationSize,
            int _generations,
            double _selectionRate,
            double _mutationRate) {
        parentsPopulationSize = _parentsPopulationSize;
        childrenPopulationSize = _childrenPopulationSize;
        generations = _generations;
        selectionRate = _selectionRate;
        mutationRate = _mutationRate;
        meanFitnesses = new double[generations + 1]; // Fence post issues
        bestIndividuals = new Phenotype[generations + 1]; // We want data both before and after evolution
        this.evolve();
    }

    // Access methods
    // --------------
    /**
     * @return The size of the population of parents
     */
    public int getParentsPopulationSize() {
        return parentsPopulationSize;
    }

    /**
     * @return The size of the population of children
     */
    public int getChildrenPopulationSize() {
        return childrenPopulationSize;
    }

    /**
     * @return The number of generations to evolve for
     */
    public int getGenerations() {
        return generations;
    }

    /**
     * @return The selection rate
     */
    public double getSelectionRate() {
        return selectionRate;
    }

    /**
     * @return The mutation rate
     */
    public double getMutationRate() {
        return mutationRate;
    }

    /**
     * Returns a description of the world Empirical data should be collated
     * elsewhere
     *
     * @return A String to represent the World
     */
    @Override
    public String toString() {
        return "Number of parents: " + parentsPopulationSize + " \n"
                + "Number of children: " + childrenPopulationSize + " \n"
                + "Number of generations: " + generations + " \n"
                + "Selection rate: " + selectionRate + " \n"
                + "Mutation rate: " + mutationRate;
    }

    // Methods for empirical data
    // --------------------------
    /**
     * @param _generation The generation of the requested mean fitness
     * @return The mean fitness of the _generationth generation
     */
    public double getMeanFitnessOfGeneration(int _generation) {
        return meanFitnesses[_generation];
    }

    /**
     * @param _generation The generation of the requested best individual
     * @return The best individual in the _generationth generation
     */
    public Phenotype getBestIndividualInGeneration(int _generation) {
        return bestIndividuals[_generation];
    }

    /**
     * Instead of a method to return the best mean fitness, the generation with
     * the best mean fitness should be found first. Then the mean fitness of
     * that generation should be found with
     * <code>
     *  int gWBMF = world.getGenerationWithBestMeanFitness();
     * <br>
     * double bMF = world.getMeanFitnessOfGeneration(gWBMF);
     * </code>
     *
     * @return The generation with the best mean fitness
     */
    public int getGenerationWithBestMeanFitness() {

        int best = 0;
        for (int i = 1; i < meanFitnesses.length; i++) {
            if (meanFitnesses[best] < meanFitnesses[i]) {
                best = i;
            }
        }

        return best;
    }

    /**
     * @return The generation in which the best Phenotype was produced
     */
    public int getGenerationWithBestIndividual() {
        int best = 0;
        for (int i = 1; i < bestIndividuals.length; i++) {
            if (bestIndividuals[best].getFitness() < bestIndividuals[i].getFitness()) {
                best = i;
            }
        }

        return best;
    }

    /**
     * Tells the user the generation in which some milestone in mean fitness
     * improvement was reached. If the milestone was not reached, -1 is
     * returned.
     *
     * @param _milestone The milestone
     * @return The generation in which a mean fitness of _milestone was reached
     */
    public int getGenerationsTakenToReachMeanFitnessOf(double _milestone) {
        int generationOfMilestone = -1;
        int currentGeneration = 0;

        while ((generationOfMilestone == -1) && (currentGeneration < meanFitnesses.length)) {
            if (meanFitnesses[currentGeneration] >= _milestone) {
                generationOfMilestone = currentGeneration;
            }

            currentGeneration++;
        }

        return generationOfMilestone;
    }

    /**
     * Tells the user the generation in which some milestone in best individual
     * improvement was reached. If the milestone was not reached, -1 is
     * returned.
     *
     * @param _milestone The milestone
     * @return The first generation in which an individual with a fitness of
     * _milestone or greater
     */
    public int getGenerationsTakenToReachBestIndividualFitnessOf(double _milestone) {
        int generationOfMilestone = -1;
        int currentGeneration = 0;

        while ((generationOfMilestone == -1) && (currentGeneration < bestIndividuals.length)) {
            if (bestIndividuals[currentGeneration].getFitness() >= _milestone) {
                generationOfMilestone = currentGeneration;
            }

            currentGeneration++;
        }

        return generationOfMilestone;
    }

    // Methods for evolution 
    // ---------------------
    /**
     * Performs evolution
     */
    public void evolve() {
        Population parents, selectedParents, children, parentsAndChildren;
        parents = new Population(parentsPopulationSize); // Set up the initial population
        Phenotype[] kids = new Phenotype[childrenPopulationSize];

        for (int gen = 0; gen < generations; gen++) // The loop of generations
        {
            // Collect data on the population
            meanFitnesses[gen] = parents.getMeanFitness();
            bestIndividuals[gen] = parents.getBestIndividual();

            // Select the best parents
            selectedParents = parents.binaryTournamentSelect(selectionRate);

            // Apply crossover to make the children
            for (int i = 0; i < kids.length; i++) {
                kids[i] = selectedParents.binaryTournamentSelectIndividual()
                        .mate(selectedParents.binaryTournamentSelectIndividual()); // Select parents and mate 'em
            }
            children = new Population(kids);

            // Combine the two sets
            parentsAndChildren = selectedParents.combine(children);

            // Apply mutation
            parentsAndChildren.mutate(mutationRate);

            // Form the population for the next generation
            parents = parentsAndChildren.binaryTournamentSelect(parentsPopulationSize);
        }
        // Collect data on the final population
        meanFitnesses[generations] = parents.getMeanFitness();
        bestIndividuals[generations] = parents.getBestIndividual();
    }
}
