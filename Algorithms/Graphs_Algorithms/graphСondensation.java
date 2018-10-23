package HomeWork.Algoritms.lab1;

import javafx.util.Pair;

import java.io.*;
import java.util.*;

/**
 * HomeWork.Algoritms.lab1
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class F {

    public StreamTokenizer t;
    private List<Integer>[] graph;
    private List<Integer>[] reverseGraph;
    private boolean[] visited;
    private List<Integer> top;
    private int[] color;

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

        graph = new ArrayList[n + 1];
        reverseGraph = new ArrayList[n + 1];

        for (int i = 0; i <= n; i++) {
            graph[i] = new ArrayList<>();
            reverseGraph[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            int from = nextInt();
            int to = nextInt();

            graph[from].add(to);
            reverseGraph[to].add(from);
        }

        visited = new boolean[n + 1];
        color = new int[n + 1];
        Arrays.fill(color, -1);
        top = new ArrayList<>();

        for (int i = 1; i <= n; i++)
            if (!visited[i])
                dfs(i);

        for (int c = 0, i = 1; i <= n; i++) {
            int v = top.get(n - i);

            if (color[v] == -1)
                setColor(v, c++);
        }

        Set<Pair<Integer, Integer>> ans = new HashSet<>();

        for (int i = 1; i < n + 1; i++)
            for (int j = 0; j < graph[i].size(); j++) {
                int to = graph[i].get(j);

                if (color[i] != color[to]) {
                    ans.add(new Pair<>(color[i], color[to]));
                }
            }

        System.out.println(ans.size());
    }

    private void dfs(int v) {
        visited[v] = true;

        for (int i = 0; i < graph[v].size(); i++) {
            int to = graph[v].get(i);

            if (!visited[to])
                dfs(to);
        }

        top.add(v);
    }

    private void setColor(int v, int c) {
        color[v] = c;

        for (int i = 0; i < reverseGraph[v].size(); i++) {
            int to = reverseGraph[v].get(i);

            if (color[to] == -1)
                setColor(to, c);
        }
    }

    public static void main(String[] args) throws IOException {
        new F().start();
    }

}
