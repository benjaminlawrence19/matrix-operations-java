/*
 * PROJECT III: TriMatrix.java
 *
 * This file contains a template for the class TriMatrix. Not all methods are
 * implemented and they do not have placeholder return statements. Make sure 
 * you have carefully read the project formulation before starting to work 
 * on this file. You will also need to have completed the Matrix class.
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
 * NAME: Ben Lawrence
 * UNIVERSITY ID: 5687139
 * DEPARTMENT: Statistics
 */

public class TriMatrix extends Matrix {
    /**
     * An array holding the diagonal elements of the matrix.
     */
    private double[] diagonal;

    /**
     * An array holding the upper-diagonal elements of the matrix.
     */
    private double[] upperDiagonal;

    /**
     * An array holding the lower-diagonal elements of the matrix.
     */
    private double[] lowerDiagonal;
    
    /**
     * Constructor function: should initialise iDim and jDim through the Matrix
     * constructor and set up the values array.
     *
     * @param dimension  The dimension of the array.
     */
    public TriMatrix(int dimension) {
        // You need to fill in this method.
        super(dimension, dimension);
        diagonal = new double[dimension];
        upperDiagonal = new double[dimension-1];
        lowerDiagonal = new double[dimension-1];
    }
    
    /**
     * Getter function: return the (i,j)'th entry of the matrix.
     *
     * @param i  The location in the first co-ordinate.
     * @param j  The location in the second co-ordinate.
     * @return   The (i,j)'th entry of the matrix.
     */
    public double getIJ(int i, int j) {
        // You need to fill in this method.
        if (i<0 || i>=iDim || j<0 || j>=jDim) {
            throw new MatrixException("Out of bounds");
        } else if (Math.abs(i-j) > 1) {
            return 0.0;
        } else if (i == j) {
            return diagonal[i];
        } else if (i > j) {
            return lowerDiagonal[j];
        } else {
            return upperDiagonal[i];
        }
    }
    
    /**
     * Setter function: set the (i,j)'th entry of the data array.
     *
     * @param i      The location in the first co-ordinate.
     * @param j      The location in the second co-ordinate.
     * @param value  The value to set the (i,j)'th entry to.
     */
    public void setIJ(int i, int j, double value) {
        // You need to fill in this method.
        if (i<0 || i>=iDim || j<0 || j>=jDim) {
            throw new MatrixException("Out of bounds");
        } else if (Math.abs(i-j) > 1) {    
            throw new MatrixException("Element must be 0");
        } else if (i == j) {
            diagonal[i] = value;
        } else if (i > j) {
            lowerDiagonal[j] = value;
        } else {
            upperDiagonal[i] = value;
        }
    }
    
    /**
     * Return the determinant of this matrix.
     *
     * @return The determinant of the matrix.
     */
    public double determinant() {
        // You need to fill in this method.
        TriMatrix LU = this.LUdecomp();
        double det = 1.0;
        for (int i=0; i<iDim; i++) {
            det = det * LU.getIJ(i, i);
        }
        return det;
    }
    
    /**
     * Returns the LU decomposition of this matrix. See the formulation for a
     * more detailed description.
     * 
     * @return The LU decomposition of this matrix.
     */
    public TriMatrix LUdecomp() {
        // You need to fill in this method.
        TriMatrix LU = new TriMatrix(iDim);

        LU.diagonal[0] = this.diagonal[0];

        for (int i=0; i<iDim-1; i++) {
            LU.upperDiagonal[i] = this.upperDiagonal[i];
        }

        for (int i = 1; i < iDim; i++) {
            if (LU.diagonal[i-1] == 0) {
                throw new MatrixException("Can't divide by 0");
            }

            LU.lowerDiagonal[i-1] = this.lowerDiagonal[i-1]/LU.diagonal[i-1];
            LU.diagonal[i] = this.diagonal[i] - LU.lowerDiagonal[i-1] * this.upperDiagonal[i-1];
        }

        return LU;
    }

    /**
     * Add the matrix to another second matrix.
     *
     * @param second  The Matrix to add to this matrix.
     * @return        The sum of this matrix with the second matrix.
     */
    public Matrix add(Matrix second){
        // You need to fill in this method.
        if (second.iDim != iDim || second.jDim != jDim) {
            throw new MatrixException("Dimensions don't match");
        }

        GeneralMatrix sum = new GeneralMatrix(iDim, jDim);
        for (int i=0; i<iDim; i++) {
            for (int j=0; j<jDim; j++) {
                sum.setIJ(i, j, this.getIJ(i, j) + second.getIJ(i, j));
            }
        }
        return sum;        
    }
    
    /**
     * Multiply the matrix by another matrix A. This is a _left_ product,
     * i.e. if this matrix is called B then it calculates the product BA.
     *
     * @param A  The Matrix to multiply by.
     * @return   The product of this matrix with the matrix A.
     */
    public Matrix multiply(Matrix A) {
        // You need to fill in this method.
        if (jDim != A.iDim) {
            throw new MatrixException("Dimensions don't match");
        }
        GeneralMatrix pdt = new GeneralMatrix(this.iDim, A.jDim);
        for (int i=0; i<this.iDim; i++) {
            for (int j=0; j<A.jDim; j++) {
                double sum = 0.0;
                for (int k=Math.max(0, i-1); k<=Math.min(i+1, jDim-1); k++) {
                    sum += this.getIJ(i,k) * A.getIJ(k,j);
                }
                pdt.setIJ(i, j, sum);
            }
        }
        return pdt;        
    }
    
    /**
     * Multiply the matrix by a scalar.
     *
     * @param scalar  The scalar to multiply the matrix by.
     * @return        The product of this matrix with the scalar.
     */
    public Matrix multiply(double scalar) {
        // You need to fill in this method.
        TriMatrix pdt = new TriMatrix(iDim);

        for (int i = 0; i < iDim-1; i++) {
            pdt.lowerDiagonal[i] = scalar * this.lowerDiagonal[i];
            pdt.diagonal[i] = scalar * this.diagonal[i];
            pdt.upperDiagonal[i] = scalar * this.upperDiagonal[i];
        }
        pdt.diagonal[iDim-1] = scalar * this.diagonal[iDim-1];     

        return pdt;
    }

    /**
     * Populates the matrix with random numbers which are uniformly
     * distributed between 0 and 1.
     */
    public void random() {
        // You need to fill in this method.
        for (int i = 0; i < iDim-1; i++) {
            lowerDiagonal[i] = Math.random();
            diagonal[i] = Math.random();
            upperDiagonal[i] = Math.random();
        }
        diagonal[iDim-1] = Math.random();
    }
    
    /*
     * Your tester function should go here.
     */
    public static void main(String[] args) {
        // Test your class implementation using this method.
        TriMatrix A = new TriMatrix(5);
        A.random();
        System.out.println("Matrix A:");
        printMatrix(A);

        TriMatrix B = new TriMatrix(5);
        B.random();
        System.out.println("\nMatrix B:");
        printMatrix(B);

        Matrix C = A.add(B);
        System.out.println("\nA + B:");
        printMatrix(C);

        TriMatrix D = (TriMatrix) A.multiply(4.0);
        System.out.println("\nA * 4:");
        printMatrix(D);

        GeneralMatrix E = (GeneralMatrix) A.multiply(B);
        System.out.println("\nA * B:");
        printMatrix(E);

        double detA = A.determinant();
        System.out.println("\ndet(A) = " + detA);
    }

    public static void printMatrix(Matrix M) {
        for (int i=0; i<M.iDim; i++) {
            for (int j=0; j<M.jDim; j++) {
                System.out.printf("%8.3f ", M.getIJ(i, j));
            }
            System.out.println();
        }
    }
}