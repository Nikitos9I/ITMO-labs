package HomeWork.Algoritms.lab1;

import java.io.*;

import static java.util.Arrays.sort;

/**
 * HomeWork.Algoritms.lab1
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class J {

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
        int m = nextInt();

        Edge[] edges = new Edge[m];
        for (int i = 0; i < m; i++) {
            edges[i] = new Edge(nextInt() - 1, nextInt() - 1, nextInt());
        }

        System.out.println(mstKruskal(edges, n));
    }

    private long mstKruskal(Edge[] edges, int n) {
        DSF dsf = new DSF(n);
        sort(edges);
        long ans = 0;

        for (Edge e : edges)
            if (dsf.union(e.u, e.v))
                ans += e.w;

        return ans;
    }

    private class Edge implements Comparable<Edge> {
        int u;
        int v;
        int w;

        private Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }

        @Override
        public int compareTo(Edge edge) {
            if (w != edge.w) return w < edge.w ? -1 : 1;
            return 0;
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
        new J().start();
    }

}
