/*
 * PROJECT III: Project3.java
 *
 * This file contains a template for the class Project3. None of methods are
 * implemented and they do not have placeholder return statements. Make sure 
 * you have carefully read the project formulation before starting to work 
 * on this file. You will also need to have completed the Matrix class, as 
 * well as GeneralMatrix and TriMatrix.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file!
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 * 
 * Tasks:
 *
 * 1) Complete this class with the indicated methods and instance variables.
 *
 * 2) Fill in the following fields:
 *
 * NAME: Benjamin Lawrence
 * UNIVERSITY ID: 5687139
 * DEPARTMENT: Statistics
 */

public class Project3 {
    /**
     * Calculates the variance of the distribution defined by the determinant
     * of a random matrix. See the formulation for a detailed description.
     *
     * @param matrix      The matrix object that will be filled with random
     *                    samples.
     * @param nSamp       The number of samples to generate when calculating 
     *                    the variance. 
     * @return            The variance of the distribution.
     */
    public static double matVariance(Matrix matrix, int nSamp) {
        // You need to fill in this method.
        double detSum = 0.0;
        double detSumSqr = 0.0;
        for (int i=1; i<=nSamp; i++) {
            matrix.random();
            double det = matrix.determinant();
            detSum += det;
            detSumSqr += det*det;
        }
        return detSumSqr/nSamp - detSum/nSamp * detSum/nSamp;
    }
    
    /**
     * This function should calculate the variances of matrices for matrices
     * of size 2 <= n <= 50 and print the results to the output. See the 
     * formulation for more detail.
     */
    public static void main(String[] args) {
        for (int n=2; n<=50; n++) {
            Matrix gen = new GeneralMatrix(n, n);
            Matrix tri = new TriMatrix(n);

            double varGen = matVariance(gen, 20000);
            double varTri = matVariance(tri, 200000);

            System.out.printf("%d %.15e %.15e%n", n, varGen, varTri);
        }
    }
}