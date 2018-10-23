package HomeWork.Algoritms.lab1;

import javafx.util.Pair;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;

import static java.util.Collections.sort;

/**
 * HomeWork.Algoritms.lab1
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class I {

    public StreamTokenizer t;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public void start() throws IOException {
        t = new StreamTokenizer(new InputStreamReader(System.in));

        int n = nextInt();

        Pair[] vertex = new Pair[n];
        for (int i = 0; i < n; i++) {
            vertex[i] = new Pair<>(nextInt() - 1, nextInt() - 1);
        }

        ArrayList<Edge> edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    edges.add(new Edge(i, j, (int) vertex[i].getKey(), (int) vertex[i].getValue(), (int) vertex[j].getKey(), (int) vertex[j].getValue()));
                }
            }
        }

        System.out.println(mstKruskal(edges, n));
    }

    private double mstKruskal(ArrayList<Edge> edges, int n) {
        DSF dsf = new DSF(n);
        sort(edges);
        double ret = 0;

        for (Edge e : edges)
            if (dsf.union(e.u, e.v))
                ret += e.w;

        return ret;
    }

    private class Edge implements Comparable<Edge> {
        int u;
        int v;
        double w;

        private Edge(int u, int v, int x1, int y1, int x2, int y2) {
            this.u = u;
            this.v = v;
            this.w = getW(x1, y1, x2, y2);
        }

        @Override
        public int compareTo(Edge edge) {
            if (w != edge.w) return w < edge.w ? -1 : 1;
            return 0;
        }

        private double getW(int x1, int y1, int x2, int y2) {
            return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
        }
    }


    private class DSF {
        int[] set;
        int[] rnk;

        private DSF(int size) {
            set = new int [size];
            rnk = new int [size];

            for (int i = 0; i < size; i++)
                set[i] = i;
        }

        int set(int x) {
            return x == set[x] ? x : (set[x] = set(set[x]));
        }

        boolean union(int u, int v) {
            if ((u = set(u)) == (v = set(v)))
                return false;

            if (rnk[u] < rnk[v]) {
                set[u] = v;
            } else {
                set[v] = u;

                if (rnk[u] == rnk[v])
                    rnk[u]++;
            }

            return true;
        }
    }

    public static void main(String[] args) throws IOException {
        new I().start();
    }

}
