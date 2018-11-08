package HomeWork.DiscretMath;

import java.io.*;
import java.util.*;

/**
 * HomeWork.DiscretMath
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class D_ex {

    public StreamTokenizer t;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public long nextLong() throws IOException {
        t.nextToken();
        return (long) t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public void start() throws IOException {
//        BufferedReader buf = new BufferedReader( new FileReader("arrange.in.txt"));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");

        BufferedReader buf = new BufferedReader( new FileReader("guyaury.in"));
        PrintWriter writer = new PrintWriter("guyaury.out");

        int n = Integer.parseInt(buf.readLine());
        int[][] arr = new int[n][n];

        for (int i = 0; i < n; i++) {
            String in = buf.readLine();
            for (int j = 0; j < i; j++) {
                arr[i][j] = Integer.parseInt(in.charAt(j) + "");
                arr[j][i] = 1 - arr[i][j];
            }
        }

        cycleInTour(arr, n);

        int i = -1;
        while (++i < n)
            writer.write(cycle[i] + 1 + " ");

        writer.close();
    }

    private int[] cycle;

    private void cycleInTour(int[][] arr, int n) {
        cycle = new int[n];

        for (int i = 0; i < n; i++) {
            cycle[i] = i;
        }

        do {
            mix(cycle);
            sout1();
            sort(arr, cycle, 0, n - 1);
            sout1();
        } while (arr[cycle[n - 1]][cycle[0]] == 0);
    }

    private void merge(int[][] matrix, int[] array, int l, int m, int r) {
        int n1 = m - l + 1, n2 = r - m;

        int left[] = new int[n1];
        int right[] = new int[n2];

        System.out.println("left = ");
        for (int i = 0; i < n1; ++i) {
            left[i] = array[l + i];
            System.out.print(left[i] + " ");
        }

        System.out.println();
        System.out.println("right = ");
        for (int i = 0; i < n2; ++i) {
            right[i] = array[m + 1 + i];
            System.out.print(right[i] + " ");
        }
        System.out.println();

        int i = 0, j = 0;
        while (i < n1 || j < n2) {
            if (j == n2 || i < n1 && matrix[left[i]][right[j]] != 0) {
                array[l + i + j] = left[i];
                i++;
            } else {
                array[l + i + j] = right[j];
                j++;
            }
        }
    }

    private void sort(int[][] matrix, int[] arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;

            sort(matrix, arr, l, m);
            sort(matrix, arr, m + 1, r);

            merge(matrix, arr, l, m, r);
        }
    }

    private void mix(int[] cycle) {
        Random rand = new Random();

        for (int i = 0; i < cycle.length; i++) {
            int randNum = rand.nextInt(cycle.length);
            int c = cycle[i];
            cycle[i] = cycle[randNum];
            cycle[randNum] = c;
        }
    }

    private void sout1() {
        for (int aCycle : cycle) {
            System.out.print(aCycle + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        new D_ex().start();
    }
}
