package HomeWork.DiscretMath;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    private class Edge implements Comparable<Edge> {
        int from;
        int to;
        long weight;
        int index;
        boolean inMST;

        private Edge(int from, int to, long weight, int index) {
            this.from = from;
            this.to = to;
            this.weight = weight;
            this.index = index;
        }

        @Override
        public int compareTo(Edge o) {
            return Long.compare(this.weight, o.weight);
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
//        t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");

        BufferedReader buf = new BufferedReader( new FileReader("destroy.in"));
        PrintWriter writer = new PrintWriter("destroy.out");

        String[] literals = buf.readLine().split(" ");
        int n = Integer.parseInt(literals[0]);
        int m = Integer.parseInt(literals[1]);
        long s = Long.parseLong(literals[2]);
        
        Edge[] edges = new Edge[m];

        for (int i = 0; i < m; i++) {
            literals = buf.readLine().split(" ");
            int from = Integer.parseInt(literals[0]) - 1;
            int to = Integer.parseInt(literals[1]) - 1;
            long weight = Long.parseLong(literals[2]);

            edges[i] = new Edge(from, to, weight, i + 1);
        }
        
        Arrays.sort(edges, Collections.reverseOrder());
        mstKruskal(edges, n);
        List<Integer> ans = new ArrayList<>();

        long sum = 0;
        for (int i = m - 1; i >= 0; --i) {
            if (!edges[i].inMST) {
                if (sum + edges[i].weight <= s) {
                    sum += edges[i].weight;
                    ans.add(edges[i].index);
                } else {
                    break;
                }
            }
        }

        writer.println(ans.size());
        Collections.sort(ans);

        for (Integer current : ans) {
            writer.write(current + " ");
        }

        writer.close();
    }

    private void mstKruskal(Edge[] edges, int n) {
        DSF dsf = new DSF(n);

        for (Edge e : edges)
            if (dsf.union(e.from, e.to))
                e.inMST = true;
    }

    public static void main(String[] args) throws IOException {
        new B().start();
    }

}
