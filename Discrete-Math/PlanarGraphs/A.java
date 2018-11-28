package HomeWork.DiscretMath;

import javafx.util.Pair;

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

public class A {

    public StreamTokenizer t;
    private List<Pair<Integer, Integer>> edges;
    private int[] xCoor;
    private int[] amount;
    private boolean[][] useless;

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
        t = new StreamTokenizer( new BufferedReader(new InputStreamReader(System.in)));

        int n = nextInt();
        int m = nextInt();

        int[] hamPath = new int[n];
        edges = new ArrayList<>();
        amount = new int[m];
        useless = new boolean[n][n];
        xCoor = new int[n];

        for (int i = 0; i < m; i++) {
            int from = nextInt() - 1;
            int to = nextInt() - 1;
            edges.add(new Pair<>(from, to));
        }

        for (int i = 0; i < n; i++) {
            int current = nextInt() - 1;
            hamPath[i] = current;
            xCoor[current] = i;
        }

        for (int i = 0; i < n; i++) {
            useless[hamPath[i == 0? i : i - 1]][hamPath[i == 0? n - 1 : i]] =
            useless[hamPath[i == 0? n - 1 : i]][hamPath[i == 0? i : i - 1]] = true;
        }

        boolean isAns = true;

        for (int i = 0; i < m; i++) {
            if (!useless[edges.get(i).getKey()][edges.get(i).getValue()]) {
                if (amount[i] == 0) {
                    amount[i] = 1;
                    isAns &= checkAmount(m, i);
                }
            }
        }

        if (!isAns) {
            System.out.println("NO");
        } else {
            System.out.println("YES");
            for (int i = 0; i < n; i++) {
                System.out.print(xCoor[i] + " " + 0 + " ");
            }
            System.out.println();

            for (int i = 0; i < m; i++) {
                int left = edges.get(i).getKey();
                int right = edges.get(i).getValue();
                double res = (xCoor[left] + xCoor[right]) * 1.0 / 2;

                if (amount[i] == 0) {
                    if ((left == hamPath[0] && right == hamPath[n - 1]) || (left == hamPath[n - 1] && right == hamPath[0])) {
                        System.out.println(res + " " + n * n);
                    } else {
                        System.out.println(res + " " + 0);
                    }
                    continue;
                }

                if (amount[i] == 1) {
                    System.out.println(res + " " + Math.abs(xCoor[left] - xCoor[right]) * 1.0 / 2);
                } else {
                    System.out.println(res + " " + Math.abs(xCoor[left] - xCoor[right]) * 1.0 / -2);
                }

            }
        }
    }

    private boolean checkAmount(int m, int v) {
        boolean isAns = true;

        for (int i = 0; i < m; i++) {
            if (!useless[edges.get(i).getKey()][edges.get(i).getValue()]) {
                if (cross(edges.get(v).getKey(), edges.get(v).getValue(), edges.get(i).getKey(), edges.get(i).getValue())) {
                    if (amount[i] == 0) {
                        amount[i] = 3 - amount[v];
                        checkAmount(m, i);
                        continue;
                    }

                    if (amount[i] == amount[v]) {
                        isAns = false;
                    }
                }
            }
        }

        return isAns;
    }

    private boolean cross(int u, int v, int a, int b) {
        return ((Integer.max(xCoor[a], xCoor[b]) > Integer.max(xCoor[u], xCoor[v]) && Integer.max(xCoor[u], xCoor[v]) > Math.min(xCoor[a], xCoor[b]) && Math.min(xCoor[a], xCoor[b]) > Math.min(xCoor[u], xCoor[v])) ||
                (Integer.max(xCoor[u], xCoor[v]) > Integer.max(xCoor[a], xCoor[b]) && Integer.max(xCoor[a], xCoor[b]) > Math.min(xCoor[u], xCoor[v]) && Math.min(xCoor[u], xCoor[v]) > Math.min(xCoor[a], xCoor[b])));
    }

    public static void main(String[] args) throws IOException {
        new A().start();
    }

}
