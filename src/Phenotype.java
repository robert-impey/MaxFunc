/**
 * A Phenotype of 2 real numbers between -10 and 10
 * Their fitness is the output of a complex function
 * @author Rob Impey
 */

public class Phenotype implements Comparable
{
    // Instance fields and class constants
    // -----------------------------------
    private static final double MAX = 10.0;
    private static final double MIN = -10.0;
    private static final int BITS = 16;
    private final Genotype genotype;
    
    // Constructors
    // ------------
    /**
     * Creates a new pair of numbers.
     * @param _genotype The genetic material
     */
    public Phenotype(Genotype _genotype)
    {
	genotype = _genotype;
    }

    /**
     * The default constructor
     */
    public Phenotype()
    {
	genotype = new Genotype();
    }

    // Access methods
    // --------------
    /**
     * @return The value of x as a real
     */
    public double getX()
    {
	return MIN 
	    + 
	    (
	     genotype.getX()
	     *
	     ((MAX - MIN) / Math.pow(2, BITS))
	     );
    }

    /**
     * @return The value of y as a real
     */
    public double getY()
    {
	return MIN 
	    + 
	    (
	     genotype.getY()
	     *
	     ((MAX - MIN) / Math.pow(2, BITS))
	     );
    }
    
    /**
     * @return A string to represent this Phenotype
     */
    public String toString()
    {
	return "x: " + this.getX() 
	    + " y: " + this.getY() 
	    + " f(x, y): " + this.getFitness();
    }

    /**
     * @return The genetic material of this phenotype
     */
    public Genotype getGenotype()
    {
	return genotype;
    }

    // Evolutionary Methods
    // --------------------    
    /**
     * Mates this phenotype with another.
     * The mate must be of the same type
     * @param _mate The mate
     * @return The child
     */
    public Phenotype mate(Phenotype _mate)
    {
	Genotype mateGenotype = _mate.getGenotype();
	Genotype childGenotype = genotype.cross(mateGenotype);
	return new Phenotype(childGenotype);
    }

    /**
     * The fitness is the output of a complex function
     * We want to find the maximum of this function so the larger the better.
     * @return The fitness of this phenotype
     */
    public double getFitness()
    {
	double x = this.getX();
	double y = this.getY();
	double f = 
	    (Math.exp(-0.7 * (x + 2.0) * (x + 2.0))
	     * Math.exp(-0.9 * y * y))
	    + ((2.0 * Math.exp(-1.0 * (x - 5.0) * (x - 6.0)))
	       * Math.exp(-1.0 * (y - 2.0) * (y - 2.0)));
	
	return f;
    }

    /**
     * For the Comparable interface, so that these objects can be sorted
     * @return Whether it's better or not
     */
    public int compareTo(Object _o)
    {
	Phenotype other = (Phenotype)_o;
	int comparison;

	if (this.getFitness() > other.getFitness())
	    comparison = 1;
	else 
	    if (this.getFitness() < other.getFitness())
		comparison = -1;
	    else
		comparison = 0;
	
	return comparison;
    }
}
