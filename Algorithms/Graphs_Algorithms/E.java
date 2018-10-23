package HomeWork.Algoritms.lab1;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.*;

/**
 * HomeWork.Algoritms.lab1
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class E {

    public StreamTokenizer t;

    private boolean was[];
    private int timer, timeIn[], checker[];
    private ArrayList<ArrayList<Pair<Integer, Integer>>> edges;
    private ArrayList<ArrayList<Integer>> pairEdgesGraph;
    private Stack<Edge> stack;
    private int colors[];
    private int maxColor = 0;

    private class Edge {
        int v;
        int u;
        int w;

        private Edge(int v, int u, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

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

        stack = new Stack<>();
        edges = new ArrayList<>();
        pairEdgesGraph = new ArrayList<>();
        was = new boolean[n];
        timeIn = new int[n];
        checker = new int[n];
        colors = new int[m];

        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            pairEdgesGraph.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            int u = nextInt() - 1;
            int v = nextInt() - 1;

            boolean isEdge = false;
            for (Pair<Integer, Integer> current : edges.get(u)) {
                if (current.getKey() == v) {
                    pairEdgesGraph.get(i).add(current.getValue());
                    isEdge = true;
                    break;
                }
            }

            if (!isEdge) {
                edges.get(u).add(new Pair<>(v, i));
                edges.get(v).add(new Pair<>(u, i));
            }

        }

        for (int i = 0; i < n; i++) {
            if (!was[i]) {
                dfs(i, -1);

                if (!stack.empty()) {
                    int first = stack.get(0).v;

                    for (Edge aStack : stack) {
                        if (aStack.v == first) {
                            maxColor++;
                        }
                        colors[aStack.w] = maxColor;
                    }

                    stack.removeAllElements();
                }
            }
        }

        for (int i = 0; i < m; i++) {
            if (colors[i] == 0) {
                colors[i] = colors[pairEdgesGraph.get(i).get(0)];
            }
        }

        System.out.println(maxColor);

        for (int i = 0; i < m; i++) {
            System.out.print(colors[i] + " ");
        }
    }

    private void dfs(int v, int p) {
        was[v] = true;
        timeIn[v] = checker[v] = timer++;

        for (int i = 0; i < edges.get(v).size(); i++) {
            int to = edges.get(v).get(i).getKey();
            int vu = edges.get(v).get(i).getValue();
            int size = stack.size();

            if (to == p)
                continue;

            if (colors[vu] == 0) {
                stack.push(new Edge(v, to, vu));
                colors[vu] = -1;
            }

            if (was[to]) {
                checker[v] = Math.min(checker[v], timeIn[to]);
            } else {
                dfs(to, v);
                checker[v] = Math.min(checker[v], checker[to]);
                if (checker[to] >= timeIn[v] && p != -1) {
                    maxColor++;
                    while (stack.size() > size) {
                        colors[stack.get(stack.size() - 1).w] = maxColor;
                        stack.pop();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new E().start();
    }

}