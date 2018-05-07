package HomeWork.Discret_math.lab1.lab4;

import java.io.*;
import java.util.StringTokenizer;

/**
 * HomeWork.Discret_math.lab1.lab4
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class D {

    public StreamTokenizer t;
    double[][] matrix;

    public final double EPS = 0.00001d;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public void start() throws IOException {
        BufferedReader buf = new BufferedReader( new FileReader( "markchain.in"));
        PrintWriter writer = new PrintWriter("markchain.out");

//        BufferedReader buf = new BufferedReader( new FileReader( "arrange.in.txt"));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");

        int n = Integer.parseInt(buf.readLine());
        matrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(buf.readLine());
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Double.parseDouble(st.nextToken());
            }
        }

        double[][] ansMatrix = matrix;
        boolean f = false;
        while (!f) {
            double[][] lastMatrix = ansMatrix;
            ansMatrix = matrixMultiply(ansMatrix);

            boolean con = false;
            for (int i = 0; i < n; i++) {
                if (Math.abs(ansMatrix[i][i] - lastMatrix[i][i]) > EPS) {
                    con = true;
                    break;
                }
            }

            if (con) continue;

            f = true;
        }

        for (int i = 0; i < n; i++) {
            writer.printf("%.5f\n", ansMatrix[i][i]);
        }

        writer.close();
    }

    public double[][] matrixMultiply(double[][] matrix) {
        int length = matrix.length;
        double[][] ansMatrix = new double[length][length];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                double nowRes = 0;
                for (int k = 0; k < length; k++) {
                    nowRes += matrix[i][k] * matrix[k][j];
                }
                ansMatrix[i][j] = nowRes;
            }
        }

        return ansMatrix;
    }

    public static void main(String[] args) throws IOException {
        new D().start();
    }

}
