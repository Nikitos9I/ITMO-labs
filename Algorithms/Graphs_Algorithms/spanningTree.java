package HomeWork.Algoritms.lab1;

import javafx.util.Pair;

import java.io.*;
import java.util.Arrays;

/**
 * HomeWork.Algoritms.lab1
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class I_prim {
    
    // SOLVE WITH PRIM ALGORITHM IMPLEMENTATION

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
        t = new StreamTokenizer( new BufferedReader(new InputStreamReader(System.in)));

        int n = nextInt();

        boolean[] visited = new boolean[n];
        double[] minEdge = new double[n];
        Arrays.fill(minEdge, Integer.MAX_VALUE);
        int[] endEdge = new int[n];
        Arrays.fill(endEdge, -1);

        Pair[] vertex = new Pair[n];
        for (int i = 0; i < n; i++) {
            vertex[i] = new Pair<>(nextInt() - 1, nextInt() - 1);
        }

        minEdge[0] = 0;
        double ans = 0;

        for (int i = 0; i < n; ++i) {
            int v = -1;
            for (int j = 0; j < n; ++j)
                if (!visited[j] && (v == -1 || minEdge[j] < minEdge[v]))
                    v = j;

            visited[v] = true;

            if (endEdge[v] != -1)
                ans += getW((int) vertex[v].getKey(), (int) vertex[v].getValue(), (int) vertex[endEdge[v]].getKey(), (int) vertex[endEdge[v]].getValue());

            for (int to = 0; to < n; ++to) {
                double w = getW((int) vertex[v].getKey(), (int) vertex[v].getValue(), (int) vertex[to].getKey(), (int) vertex[to].getValue());
                if (w < minEdge[to]) {
                    minEdge[to] = w;
                    endEdge[to] = v;
                }
            }
        }

        System.out.println(ans);
    }

    private double getW(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    public static void main(String[] args) throws IOException {
        new I_prim().start();
    }

}
