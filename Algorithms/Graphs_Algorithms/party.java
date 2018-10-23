package HomeWork.Algoritms.lab1;

import java.io.*;
import java.util.*;

/**
 * HomeWork.Algoritms.lab1
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class G {
    public StreamTokenizer t;
    private List<List<Integer>> graph;
    private List<List<Integer>> reverseGraph;
    private boolean[] visited;
    private List<Integer> order;
    private int[] comp;

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
        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));

        String nm = buf.readLine();

        int n = Integer.parseInt(nm.split("[ ]")[0]);
        int m = Integer.parseInt(nm.split("[ ]")[1]);

        String[] names = new String[2 * n];
        graph = new ArrayList<>();
        reverseGraph = new ArrayList<>();
        for (int i = 0; i < 2 * n; i++) {
            graph.add(new ArrayList<>());
            reverseGraph.add(new ArrayList<>());
        }

        for (int i = 0; i < n; i++) {
            names[i] = "+" + buf.readLine();
        }

        for (int i = n; i < 2 * n; i++) {
            names[i] = "-" + names[i - n].substring(1);
        }

        for (int i = 0; i < m; i++) {
            String item = buf.readLine();

            String from = item.split("[ ]")[0];
            String to = item.split("[ ]")[2];

            int fromInt = -1;
            int toInt = -1;

            int secondFromInt = -1;
            int secondToInt = -1;
            for (int j = 0; j < 2 * n; j++) {
                if (from.equals(names[j])) {
                    fromInt = j;
                }

                if (to.equals(names[j])) {
                    toInt = j;
                }
            }

            for (int j = 0; j < 2 * n; j++) {
                if (from.substring(1).equals(names[j].substring(1)) && j != fromInt) {
                    secondFromInt = j;
                }

                if (to.substring(1).equals(names[j].substring(1)) && j != toInt) {
                    secondToInt = j;
                }
            }

            graph.get(fromInt).add(toInt);
            reverseGraph.get(toInt).add(fromInt);
            graph.get(secondToInt).add(secondFromInt);
            reverseGraph.get(secondFromInt).add(secondToInt);
        }

//        System.out.println(graph);
//        System.out.println(reverseGraph);

        visited = new boolean[2 * n];
        comp = new int[2 * n];
        order = new ArrayList<>();
        Arrays.fill(comp, -1);

        for (int i = 0; i < 2 * n; ++i) {
            if (!visited[i])
                dfs1(i);
        }

        for (int i = 0, j = 0; i < 2 * n; ++i) {
            int v = order.get(2 * n - i - 1);
            if (comp[v] == -1)
                dfs2(v, ++j);
        }

        for (int i = 0; i < n; ++i) {
//            System.out.println(i + " " + comp[i] + " " + (i + n) + " " + comp[i + n]);
            if (comp[i] == comp[i + n]) {
                System.out.println(-1);
                return;
            }
        }

        Set<Integer> result = new TreeSet<>();
        for (int i = 0; i < n; ++i) {
            if (comp[i] > comp[i + n]) {
                result.add(i);
            }
        }

        System.out.println(result.size());
        for (Integer current : result) {
            System.out.println(names[current].substring(1));
        }
    }

    private void dfs1(int v) {
        visited[v] = true;

        for (int i = 0; i < graph.get(v).size(); ++i) {
            int to = graph.get(v).get(i);
            if (!visited[to])
                dfs1(to);
        }

        order.add(v);
    }

    private void dfs2(int v, int color) {
        comp[v] = color;
        for (int i = 0; i < reverseGraph.get(v).size(); ++i) {
            int to = reverseGraph.get(v).get(i);
            if (comp[to] == -1)
                dfs2(to, color);
        }
    }

    public static void main(String[] args) throws IOException {
        new G().start();
    }
}
