package HomeWork.Algoritms.lab1;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * HomeWork.Algoritms.lab1
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class K {

    public StreamTokenizer t;
    private static final long INF = Long.MAX_VALUE - 1000000000;

    private class Edge implements Comparable<Edge> {
        int from;
        int to;
        long weight;

        private Edge(int from, int to, long weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }


        @Override
        public int compareTo(Edge other) {
            return Long.compare(this.weight, other.weight);
        }

        private Edge min(Edge other) {
            return this.weight < other.weight? this : other;
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
        t = new StreamTokenizer( new BufferedReader(new InputStreamReader(System.in)));

        int n = nextInt();
        int m = nextInt();

        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            int from = nextInt() - 1;
            int to = nextInt() - 1;
            int weight = nextInt();

            if (from == to)
                continue;

            Edge current = new Edge(from, to, weight);

            boolean is = false;
            for (Edge temp : edges) {
                if (current.from == temp.from && current.to == temp.to) {
                    temp.weight = Long.min(temp.weight, current.weight);
                    is = true;
                }
            }

            if (!is)
                edges.add(new Edge(from, to, weight));
        }

        boolean[] visited = new boolean[n];
        checkConnectedness(edges, 0, visited);

        boolean isAns = true;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                isAns = false;
            }
        }

        long result = 0;
        if (isAns)
            result = doMST(edges, n, 0);

        if (result >= INF || !isAns || result == -1) {
            System.out.println("NO");
        } else {
            System.out.println("YES");
            System.out.println(result);
        }
    }

    private long doMST(List<Edge> edges, int n, int root) {
        boolean[] visited = new boolean[n];
        boolean[] isCycleGroup = new boolean[n];
        int[] group = new int[n];
        int count = 0;
        Edge[] minInEdge = new Edge[n];
        Arrays.fill(minInEdge, new Edge(-1, -1, INF));

        for (Edge temp : edges) {
            minInEdge[temp.to] = minInEdge[temp.to].min(temp);
        }

        minInEdge[root] = new Edge(-1, root, 0);

        for (int i = 0; i < n; i++) {
            if (visited[i])
                continue;

            int node = i;
            List<Integer> path = new ArrayList<>();
            while (node != -1 && !visited[node]) {
                visited[node] = true;
                path.add(node);
                node = minInEdge[node].from;
            }

            boolean isCycle = false;
            for (int v : path) {
                group[v] = count;
                if (v == node)
                    isCycleGroup[count] = isCycle = true;
                if (!isCycle)
                    ++count;
            }

            if (isCycle)
                ++count;
        }

        long result = 0;

        if (count == n) {
            for (Edge e : minInEdge)
                result += e.weight;
            return result;
        }

        for (Edge e : minInEdge)
            if (isCycleGroup[group[e.to]])
                result += e.weight;

        List<Edge> newEdgeList = new ArrayList<>();

        for (Edge e : edges) {
            int u = group[e.from], v = group[e.to];
            long w = e.weight;
            if (u == v)
                continue;
            else if (isCycleGroup[v])
                newEdgeList.add(new Edge(u, v, w - minInEdge[e.to].weight));
            else
                newEdgeList.add(new Edge(u, v, w));
        }

        return result + doMST(newEdgeList, count, group[root]);
    }

    private void checkConnectedness(List<Edge> edges, int current, boolean[] visited) {
        visited[current] = true;

        for (Edge cur : edges) {
            if (cur.from == current && !visited[cur.to]) {
                checkConnectedness(edges, cur.to, visited);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new K().start();
    }

}
