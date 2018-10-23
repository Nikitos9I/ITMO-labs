package HomeWork.Algoritms.lab1;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * HomeWork.Algoritms.lab1
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class H {
    public StreamTokenizer t;
    private boolean visited[][];
    private int n, k, index = 0;
    private int newEdge[][];
    private int revNewEdge[][];
    private ArrayList<Integer> w;

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
        t = new StreamTokenizer( new BufferedReader( new FileReader("avia.in")));
        PrintWriter writer = new PrintWriter("avia.out");

        n = nextInt();

        newEdge = new int[n][n];
        revNewEdge = new int[n][n];
        w = new ArrayList<>();

        visited = new boolean[1010][1010];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int d = nextInt();
                newEdge[i][j] = d;
                revNewEdge[j][i] = d;
                w.add(d);
            }
        }

        Collections.sort(w);
        int l = binSearh();

        writer.write(w.get(l) + "");
        writer.close();
    }

    private int binSearh() {
        int l = -1;
        int r = w.size();
        while (l < r - 1) {
            int m = (l + r) >> 1;
            if (!comp(w.get(m))) {
                l = m;
            } else {
                r = m;
            }
        }
        return r;
    }

    private boolean comp(Object value) {
        k = 0;
        dfs(0, value, index++, newEdge);

        if (k < n) {
            return false;
        }

        k = 0;
        dfs(0, value, index++, revNewEdge);

        return k >= n;
    }


    private void dfs(int v, Object value, int index, int Edge[][]) {
        visited[index][v] = true;
        ++k;

        for (int i = 0; i < n; i++)
            if (!visited[index][i] && Edge[v][i] <= (int) value)
                dfs(i, value, index, Edge);
    }

    public static void main(String[] args) throws IOException {
        new H().start();
    }
}
