
/**
 * A Genotype that uses binary strings. The genotype stores two numbers.
 *
 * @author Rob Impey
 */
public class Genotype {
    // Instance fields and class constants
    // -----------------------------------

    private boolean[] x, y; // The two numbers are stored as binary strings 
    private final static int BIN_LENGTH = 16; // The default length for the binary strings

    // Constructors
    // ------------
    /**
     * Creates a new Genotype using set arrays. The arrays must 16 bits long
     *
     * @param _x The array for x
     * @param _y The array for y
     */
    public Genotype(boolean[] _x, boolean[] _y) // Should an exception be thrown if wrong length?
    {
        x = _x;
        y = _y;
    }

    /**
     * Creates a new Genotype. The default length for the binary strings is 16
     * bits
     */
    public Genotype() {
        x = new boolean[BIN_LENGTH];
        y = new boolean[BIN_LENGTH];

        for (int i = 0; i < BIN_LENGTH; i++) {
            x[i] = (Math.random() < 0.5);
            y[i] = (Math.random() < 0.5);
        }
    }

    // Access methods
    // --------------
    /**
     * @return x as a decimal integer
     */
    public int getX() {
        int decX = 0;
        int magnitude = 1;

        for (int i = (BIN_LENGTH - 1); i >= 0; i--) {
            if (x[i]) {
                decX += magnitude;
            }

            magnitude *= 2;
        }

        return decX;
    }

    /**
     * @return y as a decimal integer
     */
    public int getY() {
        int decY = 0;
        int magnitude = 1;

        for (int i = (BIN_LENGTH - 1); i >= 0; i--) {
            if (y[i]) {
                decY += magnitude;
            }

            magnitude *= 2;
        }

        return decY;
    }

    /**
     * @return The x string
     */
    public boolean[] getXString() {
        return x;
    }

    /**
     * @return The y string
     */
    public boolean[] getYString() {
        return y;
    }

    /**
     * @return A string to represent the genotype
     */
    @Override
    public String toString() {
        String s = "x: ";
        for (int i = 0; i < BIN_LENGTH; i++) {
            if (x[i]) {
                s += "1";
            } else {
                s += "0";
            }
        }

        s += "\ty: ";
        for (int i = 0; i < BIN_LENGTH; i++) {
            if (y[i]) {
                s += "1";
            } else {
                s += "0";
            }
        }

        return s;
    }

    // Evolutionary Methods
    // --------------------
    /**
     * Recombines this genotype with another using uniform cross over
     *
     * @param _mate The other genotype
     * @return The child
     */
    public Genotype cross(Genotype _mate) {
        // Set up the binary strings of the parents and child
        // One parent is already set up
        boolean[] mateX, mateY, childX, childY;
        mateX = _mate.getXString();
        mateY = _mate.getYString();
        childX = new boolean[BIN_LENGTH];
        childY = new boolean[BIN_LENGTH];

        for (int i = 0; i < BIN_LENGTH; i++) {
            // Make the child's x string
            if (Math.random() < 0.5) {
                childX[i] = x[i];
            } else {
                childX[i] = mateX[i];
            }

            // Make the child's y string
            if (Math.random() < 0.5) {
                childY[i] = y[i];
            } else {
                childY[i] = mateY[i];
            }
        }

        return new Genotype(childX, childY);
    }

    /**
     * Mutates the strings
     *
     * @param _mutationRate The rate by which to mutate the strings
     */
    public void mutate(double _mutationRate) {
        for (int i = 0; i < BIN_LENGTH; i++) {
            // Mutate the x string
            if (Math.random() < _mutationRate) {
                x[i] = !x[i];
            }

            // Mutate the y string
            if (Math.random() < _mutationRate) {
                y[i] = !y[i];
            }
        }
    }
}
