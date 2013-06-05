/**
 * The Population 
 * @author Rob Impey
 */

import java.util.*;

public class Population 
{
    // Instance variables and class constants
    // --------------------------------------
    private Phenotype[] individuals;

    // Constructors
    // ------------
    /**
     * @param _p Phenotypes to give to the population
     */
    public Population(Phenotype[] _p)
    {
	individuals = _p;
    }

    /**
     * @param _size The size of the population
     */
    public Population(int _size)
    {
	individuals = new Phenotype[_size];
	for(int i = 0; i < individuals.length; i++)
	    individuals[i] = new Phenotype();
    }

    // Access methods
    // --------------
    /**
     * @return The size of the population
     */
    public int getSize()
    {
	return individuals.length;
    }

    /**
     * @return The mean fitness of the population
     */
    public double getMeanFitness()
    {
	double total = 0.0;

	for (int i = 0; i < individuals.length; i++)
	    total += individuals[i].getFitness();
	
	return total / individuals.length;
    }

    /**
     * @param _i The index of the requested Phenotype
     * @return The _ith Phenotype
     */
    public Phenotype getPhenotype(int _i)
    {
	return individuals[_i];
    }

    /**
     * @return The best individual, i.e. the last in a sorted population
     */
    public Phenotype getBestIndividual()
    {
	Arrays.sort(individuals);
	return individuals[individuals.length - 1];
    }

    /**
     * @return A String to represent the population
     */
    public String toString()
    {
	return "Size: " + individuals.length + " \n"
	    + "Mean fitness: " + this.getMeanFitness() + " \n"
	    + "Best individual: " + this.getBestIndividual().toString();
    }

    /**
     * Combines this Population with another to make a new Population
     * @param _other The other population
     * @return The new Population
     */
    public Population combine(Population _other)
    {
	Phenotype[] combined = new Phenotype[individuals.length + _other.getSize()];

	// Add the Phenotypes from this Population
	for (int i = 0; i < individuals.length; i++)
	    combined[i] = individuals[i];

	// Add them from the other
	for (int i = individuals.length; i < combined.length; i++)
	    combined[i] = _other.getPhenotype(i - individuals.length); 

	//Arrays.sort(combined); // Yes or no? Populations get sorted soon enough whatever

	return new Population(combined);
    }

    // Evolutionary Methods
    // --------------------
    /**
     * Selects an individual using binary tournament selection
     * That is, 2 individuals are selected at random and the fitter one is returned
     * @return The selected individual
     */
    public Phenotype binaryTournamentSelectIndividual()
    {
	int randomIndex1 = (int)(Math.random() * individuals.length);
	int randomIndex2 = (int)(Math.random() * individuals.length);
	Phenotype selection;

	if (individuals[randomIndex1].compareTo(individuals[randomIndex2]) == 1) // 1 is better than 2
	    selection = individuals[randomIndex1];
	else
	    {
		if (individuals[randomIndex1].compareTo(individuals[randomIndex2]) == -1) // 1 is worse than 2
		    selection = individuals[randomIndex2];
		else // They have the same fitness, so return either
		    {
			if (Math.random() < 0.5)
			    selection = individuals[randomIndex1];
			else
			    selection = individuals[randomIndex2];   
		    }	
	    }	
		
	return selection;
    }
    
    /**
     * Selects a new Population of Phenotypes using Tournament selection.
     * The Population was a set size.
     * @param _size The size of the new population
     * @return The new population of selected individuals
     */
    public Population binaryTournamentSelect(int _size)
    {
	if (_size < 1)
	    _size = 1; // To avoid an abvious error
	    
	Phenotype[] selection = new Phenotype[_size];

	for (int i = 0; i < selection.length; i++)
	    selection[i] = this.binaryTournamentSelectIndividual();

	return new Population(selection);
    }

    /**
     * Selects a new Population of Phenotypes using Tournament selection.
     * The selection rate is a real number between 0 and 1.
     * If a selection rate larger than 1 is given, there will be at least one repeat
     * @param _selectionRate The selection rate
     * @return The new Population of selected individuals
     */
    public Population binaryTournamentSelect(double _selectionRate)
    {
	int size = (int)(individuals.length * _selectionRate);
	return this.binaryTournamentSelect(size);
    }

    /**
     * Mutates all the individuals a set amount
     * @param _mutRat The mutation rate
     */
    public void mutate(double _mutRat)
    {
	for(int i = 0; i < individuals.length; i++)
	    individuals[i].getGenotype().mutate(_mutRat);
    }
}
