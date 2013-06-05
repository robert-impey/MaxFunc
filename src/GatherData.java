
/**
 * To gather some empirical data about the function maximising
 *
 * @author Rob Impey
 */
public class GatherData {

    public static void main(String[] args) {
        // Varibles for controls and results
        final int pPS = 60;
        final int cPS = 60;
        final int gen = 60;
        final double sR = 0.5;
        final double mR = 0.1;

        final double[] milestones = {2.0, 2.1, 2.2, 2.3, 2.4, 2.5};

        int gWBMF; // Generation with best mean fitness
        int gWBI; // Generation with best individual
        double bMF; // Best generational mean fitness
        Phenotype bI; // Best individual

        // Experiments with varying the number of parents

        World[] wPPS = new World[6];
        wPPS[0] = new World(10, cPS, gen, sR, mR);
        wPPS[1] = new World(20, cPS, gen, sR, mR);
        wPPS[2] = new World(40, cPS, gen, sR, mR);
        wPPS[3] = new World(80, cPS, gen, sR, mR);
        wPPS[4] = new World(160, cPS, gen, sR, mR);
        wPPS[5] = new World(320, cPS, gen, sR, mR);

        // Experiments with varying the number of children
        World[] wCPS = new World[6];
        wCPS[0] = new World(pPS, 10, gen, sR, mR);
        wCPS[1] = new World(pPS, 20, gen, sR, mR);
        wCPS[2] = new World(pPS, 40, gen, sR, mR);
        wCPS[3] = new World(pPS, 80, gen, sR, mR);
        wCPS[4] = new World(pPS, 160, gen, sR, mR);
        wCPS[5] = new World(pPS, 320, gen, sR, mR);

        // Experiments with varying the number of generations
        World[] wGen = new World[6];
        wGen[0] = new World(pPS, cPS, 10, sR, mR);
        wGen[1] = new World(pPS, cPS, 20, sR, mR);
        wGen[2] = new World(pPS, cPS, 40, sR, mR);
        wGen[3] = new World(pPS, cPS, 80, sR, mR);
        wGen[4] = new World(pPS, cPS, 160, sR, mR);
        wGen[5] = new World(pPS, cPS, 320, sR, mR);

        // Experiments with varying the selection rate
        World[] wSR = new World[9];
        wSR[0] = new World(pPS, cPS, gen, 0.1, mR);
        wSR[1] = new World(pPS, cPS, gen, 0.2, mR);
        wSR[2] = new World(pPS, cPS, gen, 0.3, mR);
        wSR[3] = new World(pPS, cPS, gen, 0.4, mR);
        wSR[4] = new World(pPS, cPS, gen, 0.5, mR);
        wSR[5] = new World(pPS, cPS, gen, 0.6, mR);
        wSR[6] = new World(pPS, cPS, gen, 0.7, mR);
        wSR[7] = new World(pPS, cPS, gen, 0.8, mR);
        wSR[8] = new World(pPS, cPS, gen, 0.9, mR);

        // Experiments with varying the mutation rate
        World[] wMR = new World[9];
        wMR[0] = new World(pPS, cPS, gen, sR, 0.1);
        wMR[1] = new World(pPS, cPS, gen, sR, 0.2);
        wMR[2] = new World(pPS, cPS, gen, sR, 0.3);
        wMR[3] = new World(pPS, cPS, gen, sR, 0.4);
        wMR[4] = new World(pPS, cPS, gen, sR, 0.5);
        wMR[5] = new World(pPS, cPS, gen, sR, 0.6);
        wMR[6] = new World(pPS, cPS, gen, sR, 0.7);
        wMR[7] = new World(pPS, cPS, gen, sR, 0.8);
        wMR[8] = new World(pPS, cPS, gen, sR, 0.9);

        // Display the results

        System.out.println();
        System.out.println("Experiments with varying the size of the population of parents");
        System.out.println();

        System.out.println("Child population: " + cPS);
        System.out.println("Generations: " + gen);
        System.out.println("Selection rate: " + sR);
        System.out.println("Mutation rate: " + mR);
        System.out.println();

        System.out.println("PPS\t|\tGWBMF\tBMF\tGWBI\tBIX\tBIY\tBIF");
        System.out.println();

        for (int i = 0; i < wPPS.length; i++) {
            gWBMF = wPPS[i].getGenerationWithBestMeanFitness();
            bMF = wPPS[i].getMeanFitnessOfGeneration(gWBMF);
            gWBI = wPPS[i].getGenerationWithBestIndividual();
            bI = wPPS[i].getBestIndividualInGeneration(gWBI);

            System.out.println(
                    wPPS[i].getParentsPopulationSize() + "\t|\t"
                    + gWBMF + "\t"
                    + shorten(bMF) + "\t"
                    + gWBI + "\t"
                    + shorten(bI.getX()) + "\t"
                    + shorten(bI.getY()) + "\t"
                    + shorten(bI.getFitness()));
        }

        System.out.println();
        System.out.println("Experiments with varying the size of the population of children");
        System.out.println();

        System.out.println("Parent population: " + pPS);
        System.out.println("Generations: " + gen);
        System.out.println("Selection rate: " + sR);
        System.out.println("Mutation rate: " + mR);
        System.out.println();

        System.out.println("CPS\t|\tGWBMF\tBMF\tGWBI\tBIX\tBIY\tBIF");
        System.out.println();

        for (int i = 0; i < wCPS.length; i++) {
            gWBMF = wCPS[i].getGenerationWithBestMeanFitness();
            bMF = wCPS[i].getMeanFitnessOfGeneration(gWBMF);
            gWBI = wCPS[i].getGenerationWithBestIndividual();
            bI = wCPS[i].getBestIndividualInGeneration(gWBI);

            System.out.println(
                    wCPS[i].getChildrenPopulationSize() + "\t|\t"
                    + gWBMF + "\t"
                    + shorten(bMF) + "\t"
                    + gWBI + "\t"
                    + shorten(bI.getX()) + "\t"
                    + shorten(bI.getY()) + "\t"
                    + shorten(bI.getFitness()));
        }

        System.out.println();
        System.out.println("Experiments with varying the number of generations");
        System.out.println();

        System.out.println("Parent population: " + pPS);
        System.out.println("Child population: " + cPS);
        System.out.println("Selection rate: " + sR);
        System.out.println("Mutation rate: " + mR);
        System.out.println();

        System.out.println("G\t|\tGWBMF\tBMF\tGWBI\tBIX\tBIY\tBIF");
        System.out.println();

        for (int i = 0; i < wGen.length; i++) {
            gWBMF = wGen[i].getGenerationWithBestMeanFitness();
            bMF = wGen[i].getMeanFitnessOfGeneration(gWBMF);
            gWBI = wGen[i].getGenerationWithBestIndividual();
            bI = wGen[i].getBestIndividualInGeneration(gWBI);

            System.out.println(
                    wGen[i].getGenerations() + "\t|\t"
                    + gWBMF + "\t"
                    + shorten(bMF) + "\t"
                    + gWBI + "\t"
                    + shorten(bI.getX()) + "\t"
                    + shorten(bI.getY()) + "\t"
                    + shorten(bI.getFitness()));
        }

        System.out.println();
        System.out.println("Experiments with varying the selection rate");
        System.out.println();

        System.out.println("Parent population: " + pPS);
        System.out.println("Child population: " + cPS);
        System.out.println("Generations: " + gen);
        System.out.println("Mutation rate: " + mR);
        System.out.println();

        System.out.println("SR\t|\tGWBMF\tBMF\tGWBI\tBIX\tBIY\tBIF");
        System.out.println();

        for (int i = 0; i < wSR.length; i++) {
            gWBMF = wSR[i].getGenerationWithBestMeanFitness();
            bMF = wSR[i].getMeanFitnessOfGeneration(gWBMF);
            gWBI = wSR[i].getGenerationWithBestIndividual();
            bI = wSR[i].getBestIndividualInGeneration(gWBI);

            System.out.println(
                    wSR[i].getSelectionRate() + "\t|\t"
                    + gWBMF + "\t"
                    + shorten(bMF) + "\t"
                    + gWBI + "\t"
                    + shorten(bI.getX()) + "\t"
                    + shorten(bI.getY()) + "\t"
                    + shorten(bI.getFitness()));
        }

        System.out.println();
        System.out.println("Experiments with varying the mutation rate");
        System.out.println();

        System.out.println("Parent population: " + pPS);
        System.out.println("Child population: " + cPS);
        System.out.println("Generations: " + gen);
        System.out.println("Selection rate: " + sR);
        System.out.println();

        System.out.println("MR\t|\tGWBMF\tBMF\tGWBI\tBIX\tBIY\tBIF");
        System.out.println();

        for (int i = 0; i < wMR.length; i++) {
            gWBMF = wMR[i].getGenerationWithBestMeanFitness();
            bMF = wMR[i].getMeanFitnessOfGeneration(gWBMF);
            gWBI = wMR[i].getGenerationWithBestIndividual();
            bI = wMR[i].getBestIndividualInGeneration(gWBI);

            System.out.println(
                    wMR[i].getMutationRate() + "\t|\t"
                    + gWBMF + "\t"
                    + shorten(bMF) + "\t"
                    + gWBI + "\t"
                    + shorten(bI.getX()) + "\t"
                    + shorten(bI.getY()) + "\t"
                    + shorten(bI.getFitness()));
        }
    }

    /**
     * To shorten doubles to 3 decimal places
     *
     * @param _d The number to shorten
     * @return The shortened number
     */
    public static double shorten(double _d) {
        int dTimes1000 = (int) (_d * 1000);
        return dTimes1000 / 1000.0;
    }
}
