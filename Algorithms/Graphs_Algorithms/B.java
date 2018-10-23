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

public class B {

    public StreamTokenizer t;
    private int[] visited;
    private int timer;
    private int[] timeIn;
    private int[] checker;
    private List<Pair<Integer, Integer>>[] graph;
    private List<Integer> order;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public void start() throws IOException {
        t = new StreamTokenizer( new BufferedReader(new InputStreamReader(System.in)));

        int n = nextInt();
        int m = nextInt();

        visited = new int[n];
        timeIn = new int[n];
        checker = new int[n];
        graph = new ArrayList[n];
        order = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            int from = nextInt() - 1;
            int to = nextInt() - 1;
            graph[from].add(new Pair<>(to, i + 1));
            graph[to].add(new Pair<>(from, i + 1));
        }

        getBridges(n);

        if (order.isEmpty()) {
            System.out.println(0);
        } else {
            System.out.println(order.size());
            Collections.sort(order);

            for (Integer current : order) {
                System.out.println(current);
            }
        }
    }

    private void getBridges(int n) {
        for (int i = 0; i < n; i++) {
            if (visited[i] == 0) {
                dfs(i, -1);
            }
        }
    }

    private void dfs(int current, int p) {
        visited[current] = 1;
        timeIn[current] = checker[current] = timer++;

        for (int i = 0; i < graph[current].size(); ++i) {
            int to = graph[current].get(i).getKey();

            if (to == p)
                continue;

            if (visited[to] == 1) {
                checker[current] = Integer.min(checker[current], timeIn[to]);
            } else {
                dfs(to, current);
                checker[current] = Integer.min(checker[current], checker[to]);

                if (checker[to] > timeIn[current]) {
                    order.add(graph[current].get(i).getValue());
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new B().start();
    }

}
