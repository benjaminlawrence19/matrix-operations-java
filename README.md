# Matrix Operations and Numerical Linear Algebra (Java)

A Java implementation of matrix operations using an abstract matrix type and two concrete representations: a general dense matrix and a tridiagonal matrix. The project also includes a Monte Carlo routine that estimates the variance of determinants for randomly generated matrices.

## Project structure

| File | Responsibility |
|---|---|
| `Matrix.java` | Abstract base class defining matrix dimensions and the common operation interface |
| `GeneralMatrix.java` | Dense matrix backed by a two-dimensional `double` array |
| `TriMatrix.java` | Tridiagonal matrix stored using three one-dimensional arrays |
| `Project3.java` | Monte Carlo variance calculation and experiment driver |

## Implemented functionality

### Shared matrix interface

`Matrix` stores the row and column dimensions and defines abstract methods for element access, element updates, determinant calculation, addition, matrix multiplication, scalar multiplication and random population. It also provides a formatted `toString()` representation.

### General matrices

`GeneralMatrix` stores every entry in a `double[][]` and implements:

- construction and copying;
- indexed element access with bounds checks;
- matrix addition, with dimension compatibility checks;
- matrix multiplication, with inner-dimension checks;
- scalar multiplication;
- random entries drawn using `Math.random()` (uniformly on `[0, 1)`);
- determinant calculation using LU decomposition.

Its LU routine uses row pivoting and records the sign change caused by row swaps. The determinant is calculated as the pivot sign multiplied by the diagonal entries of the resulting upper-triangular factor. The packed LU representation stores the upper-triangular entries and the multipliers below the diagonal in one matrix.

### Tridiagonal matrices

`TriMatrix` represents a square tridiagonal matrix using:

- `diagonal` for the main diagonal;
- `upperDiagonal` for the entries immediately above it;
- `lowerDiagonal` for the entries immediately below it.

Entries outside these three diagonals are implicitly zero. The class implements element access, addition, matrix multiplication, scalar multiplication, random population and determinant calculation. Its LU decomposition exploits the tridiagonal structure rather than expanding the matrix into a dense representation.

### Monte Carlo experiment

`Project3.matVariance(Matrix matrix, int nSamp)` repeatedly fills a supplied matrix with random values, calculates its determinant and estimates the variance using

\[
\widehat{\operatorname{Var}}(D)
= \frac{1}{n}\sum_{k=1}^{n}D_k^2
- \left(\frac{1}{n}\sum_{k=1}^{n}D_k\right)^2.
\]

The `main` method runs the experiment for matrix dimensions from 2 to 50. It uses 20,000 samples for general matrices and 200,000 samples for tridiagonal matrices, printing one row per dimension.

## Concepts demonstrated

- Abstract classes, inheritance and method overriding
- Encapsulation of different matrix storage formats
- Dense versus structured matrix representations
- Matrix addition and multiplication
- LU decomposition and determinant calculation
- Pivoting in dense LU factorisation
- Monte Carlo estimation and numerical computation

## Build and run

The source files are intended to be compiled together. The supplied archive does **not** contain a definition of `MatrixException`, which is referenced by the matrix classes. Add the relevant exception class (or include the course-provided version on the classpath) before compiling.

For example, once `MatrixException` is available:

```bash
javac Matrix.java GeneralMatrix.java TriMatrix.java Project3.java
java Project3
```

The experiment can be computationally intensive because it performs many determinant calculations for each matrix size.

## Scope and notes

The supplied source contains four Java classes. Despite the broader linear-algebra context, **there is no separate complex-number class or complex arithmetic implementation in this archive**. The implementation operates on real-valued `double` entries.

The code is an educational implementation rather than a general-purpose numerical linear algebra library. In particular, the tridiagonal LU routine does not use the same pivoting strategy as the dense routine, and numerical edge cases should be considered when using either implementation beyond the intended coursework experiments.
