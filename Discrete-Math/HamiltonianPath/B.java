package HomeWork.DiscretMath;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * HomeWork.DiscretMath
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class B {

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

        BufferedReader buf = new BufferedReader( new FileReader("chvatal.in"));
        PrintWriter writer = new PrintWriter("chvatal.out");

        int n = Integer.parseInt(buf.readLine());
        int[][] arr = new int[n][n];

        for (int i = 0; i < n; i++) {
            String in = buf.readLine();
            for (int j = 0; j < i; j++) {
                arr[i][j] = Integer.parseInt(in.charAt(j) + "");
                arr[j][i] = arr[i][j];
            }
        }

        findHamiltonCycle(arr, n);

        for (int i = diff; i < cycle.size(); i++) {
            writer.write(cycle.get(i) + 1 + " ");
        }

        writer.close();
    }

    private List<Integer> cycle;
    private int diff;

    private void findHamiltonCycle(int[][] arr, int n) {
        cycle = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            cycle.add(i);
        }

        diff = 0;

        for (int i = 0; i < n * (n - 1); i++) {
            if (arr[cycle.get(diff)][cycle.get(1 + diff)] == 0) {
                int v = 2;
                while (v < n - 1 && (arr[cycle.get(diff)][cycle.get(diff + v)] == 0 || arr[cycle.get(1 + diff)][cycle.get(diff + v + 1)] == 0)) {
                    ++v;
                }

                if (v == n - 1) {
                    v = 2;
                    while (arr[cycle.get(diff)][cycle.get(diff + v)] == 0) {
                        v++;
                    }
                }

                reverse(cycle, diff + 1, diff + v + 1);
            }

            cycle.add(cycle.get(diff));
            ++diff;
        }
    }

    private void reverse(List<Integer> ara, int start, int end) {
        for(int i = start; i < start + (end - start) / 2; i++) {
            System.out.println(i);
            int temp = ara.get(i);
            ara.set(i, ara.get(end - (i - start) - 1));
            ara.set(end - (i - start) - 1, temp);
        }
    }

    public static void main(String[] args) throws IOException {
        new B().start();
    }

}
