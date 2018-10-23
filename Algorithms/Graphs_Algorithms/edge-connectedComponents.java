package HomeWork.Algoritms.lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;

/**
 * HomeWork.Algoritms.lab1
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class D {
    public StreamTokenizer t;
    private int[] visited;
    private int timer;
    private int[] timeIn;
    private int[] checker;
    private List<Integer>[] graph;
    private List<Integer>[] pairEdgesGraph;
    private int[] colors;
    private int maxColor;

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
        colors = new int[n];
        graph = new ArrayList[n];
        pairEdgesGraph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
            pairEdgesGraph[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            int from = nextInt() - 1;
            int to = nextInt() - 1;

            if (graph[from].contains(to)) {
                pairEdgesGraph[from].add(to);
                pairEdgesGraph[to].add(from);
            }

            graph[from].add(to);
            graph[to].add(from);
        }

        getCSS(n);

        System.out.println(maxColor);
        for (int i = 0; i < n; i++) {
            System.out.print(colors[i] + " ");
        }
    }

    private void getCSS(int n) {
        for (int i = 0; i < n; i++) {
            if (visited[i] == 0) {
                dfs(i, -1);
            }
        }

        for (int i = 0; i < n; i++) {
            if (colors[i] == 0) {
                ++maxColor;
                paint(i, maxColor);
            }
        }
    }

    private void paint(int current, int color) {
        colors[current] = color;

        for (int i = 0; i < graph[current].size(); i++) {
            int to = graph[current].get(i);
            if (colors[to] == 0) {
                if (checker[to] > timeIn[current] && !pairEdgesGraph[current].contains(to)) {
                    maxColor++;
                    paint(to, maxColor);
                } else {
                    paint(to, color);
                }
            }
        }
    }

    private void dfs(int current, int p) {
        visited[current] = 1;
        timeIn[current] = checker[current] = timer++;

        for (int i = 0; i < graph[current].size(); ++i) {
            int to = graph[current].get(i);

            if (to == p)
                continue;

            if (visited[to] == 1) {
                checker[current] = Integer.min(checker[current], timeIn[to]);
            } else {
                dfs(to, current);
                checker[current] = Integer.min(checker[current], checker[to]);
            }
        }
    }



    public static void main(String[] args) throws IOException {
        new D().start();
    }
}
