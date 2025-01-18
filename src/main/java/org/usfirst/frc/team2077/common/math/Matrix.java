package org.usfirst.frc.team2077.common.math;

import java.util.Arrays;
import java.util.function.Function;

public class Matrix {
    protected double[][] matrix;

    public Matrix(int height, int width) {
        this(new double[height][width]);
    }

    protected Matrix(Matrix toTranspose) {
        double[][] original = toTranspose.matrix;
        double[][] transposedMatrix = new double[toTranspose.getWidth()][toTranspose.getHeight()];

        for (int x = 0; x < original.length; x++) {
            for (int y = 0; y < transposedMatrix.length; y++) {
                transposedMatrix[y][x] = original[x][y];
            }
        }

        matrix = transposedMatrix;
    }

    public Matrix(double[][] matrix) {
        this.matrix = matrix;
    }

    /**
     * Returns a transposed version of this matrix.
     *
     * @return A matrix similar to new double[{@link #getWidth()}][{@link #getHeight()}]
     */
    public Matrix transpose() {
        return new Matrix(this);
    }

    /**
     * This directly correlates to valid "X" arguments made to
     * {@link #get(int, int)}/{@link #set(int, int, double)}/{@link #calculate(int, int, Function)}
     *
     * @return the "width" of the matrix
     * @throws IndexOutOfBoundsException if the height of the matrix is 0
     */
    public int getWidth() {
        return matrix[0].length;
    }

    /**
     * This directly correlates to valid "Y" arguments made to
     * {@link #get(int, int)}/{@link #set(int, int, double)}/{@link #calculate(int, int, Function)}
     *
     * @return the "height" of this matrix
     */
    public int getHeight() {
        return matrix.length;
    }

    public double get(int x, int y) {
        return matrix[y][x];
    }

    public void set(int x, int y, double value) {
        matrix[y][x] = value;
    }

    /**
     * similar to {@link #set}, however takes a function instead of an updated value. The function argument is the current
     * value of {@link #getMatrix()}[{@code y}][{@code x}] and it should return the updated value.
     *
     * @param x         index
     * @param y         index
     * @param calculate Update function for {@link #getMatrix()}[{@code y}][{@code x}]
     */
    public void calculate(int x, int y, Function<Double, Double> calculate) {
        double value;
        value = get(x, y);

        set(x, y, calculate.apply(value));
    }

    /**
     * This assumes that multiplication with {@code by} is valid. In other words does <strong>NOT</strong>
     * enforce that {@code by} is a B by A matrix, assuming this is an A by B matrix.
     *
     * @param by          matrix to multiply bu
     * @param constructor constructor for resulting Matrix
     * @param <T>         The type of matrix to create
     * @return The product of this by {@code by}A matrix of type T
     */
    protected <T extends Matrix> T multiply(double[][] by, Function<double[][], T> constructor) {
        double[][] result = new double[getHeight()][by[0].length];

        for (int x = 0; x < result[0].length; x++) {
            for (int y = 0; y < result.length; y++) {
                for (int col = 0; col < matrix[0].length; col++) {
                    double toAdd = get(col, y) * by[col][x];
                    result[y][x] = result[y][x] + toAdd;
                }
            }
        }

        return constructor.apply(result);
    }

    /**
     * this assumes the passed matrix is a compatible matrix and performs no checks on dimensions
     *
     * @param by the matrix to multiply by
     * @return a {@link #getHeight()} X {@code by.getWidth} matrix
     */
    public Matrix multiply(Matrix by) {
        return multiply(by.matrix, Matrix::new);
    }

    /**
     * Multiplies this matrix by a constant value
     *
     * @param by multiplier
     * @return a new matrix equivalent to this * {@code by}
     */
    public Matrix multiply(double by) {
        Matrix result = new Matrix(getHeight(), getWidth());

        for (int x = 0; x < result.getWidth(); x++) {
            for (int y = 0; y < result.getHeight(); y++) {
                result.set(x, y, get(x, y) * by);
            }
        }

        return result;
    }

    /**
     * A copy of the internal 2-Dimensional array
     *
     * @return a 2 dimensional array of size new double[{@link #getHeight()}][{@link #getWidth()}]
     */
    public double[][] getMatrix() {
        double[][] result = new double[getHeight()][getWidth()];

        for (int x = 0; x < getWidth(); x++)
            for (int y = 0; y < getHeight(); y++) result[y][x] = get(x, y);

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix that = (Matrix) o;
        return Arrays.deepEquals(this.getMatrix(), that.getMatrix());
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(matrix);
    }

    @Override
    public String toString() {
        Object[] formatArgs = new Object[getHeight() * getWidth()];
        int longest = "0.00".length();
        StringBuilder output = new StringBuilder();
        String formatToLengthFormat = "%%%1$ds"; // %1$d will be the initial conversion for lengths which should produce %<longest>s

        for (int y = 0; y < getHeight(); y++) {
            output.append("| ");
            for (int x = 0; x < getWidth(); x++) {
                if (x > 0) output.append(", ");
                output.append(formatToLengthFormat);

                // the current longest vs the current value to 2 decimal places
                String value = String.format("%.2f", get(x, y));
                longest = Math.max(longest, value.length());
                formatArgs[(getWidth() * y) + x] = value;
            }
            output.append(" |%%n");
        }
        String dash = "-";
        String padding = dash + dash;
        StringBuilder line = new StringBuilder(padding);
        for (int i = 0; i < getWidth(); i++) {
            line.append(dash.repeat(longest))
                    .append(padding); // either the ", " or the " |"
        }

        // Output needs to be formatted twice.
        // This String.format creates a format string that makes all values the same length
        String format = String.format(output.toString(), longest);
        return line.toString() + '\n' + String.format(format, formatArgs) + line;
    }

    protected <T extends Matrix> T inverse3x3(Function<double[][], T> constructor) {
        double[][] m = matrix;
        double determinate
                = m[0][0] * m[1][1] * m[2][2]
                + m[0][1] * m[1][2] * m[2][0]
                + m[0][2] * m[1][0] * m[2][1]
                - m[2][0] * m[1][1] * m[0][2]
                - m[2][1] * m[1][2] * m[0][0]
                - m[2][2] * m[1][0] * m[0][1];
        double[][] inverse = {
                {
                        m[1][1] * m[2][2] - m[1][2] * m[2][1],
                        m[0][2] * m[2][1] - m[0][1] * m[2][2],
                        m[0][1] * m[1][2] - m[0][2] * m[1][1]
                },
                {
                        m[1][2] * m[2][0] - m[1][0] * m[2][2],
                        m[0][0] * m[2][2] - m[0][2] * m[2][0],
                        m[0][2] * m[1][0] - m[0][0] * m[1][2]
                },
                {
                        m[1][0] * m[2][1] - m[1][1] * m[2][0],
                        m[0][1] * m[2][0] - m[0][0] * m[2][1],
                        m[0][0] * m[1][1] - m[0][1] * m[1][0]
                }
        };
        if (determinate != 0) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    inverse[i][j] /= determinate;
                }
            }
        }

        return constructor.apply(inverse);
    }

    public Matrix inverse3x3() {
        return inverse3x3(Matrix::new);
    }
}
