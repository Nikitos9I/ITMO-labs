package HomeWork.Algoritms.lab1;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * HomeWork.Algoritms.lab1
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class A {

    public StreamTokenizer t;
    private ArrayList<Integer>[] graph;
    public List<Integer> ans;
    private int[] visited;
    private boolean wasCycle = false;

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

        t = new StreamTokenizer( new BufferedReader( new InputStreamReader(System.in)));
//        PrintWriter writer = new PrintWriter("topsort.out");

        int n = nextInt();
        int m = nextInt();

        visited = new int[n];
        Arrays.fill(visited, 1);
        graph = new ArrayList[n];
        ans = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            graph[nextInt() - 1].add(nextInt() - 1);
        }

        topSort(n);

        if (!wasCycle) {
            for (Integer cur : ans) {
                System.out.print((cur + 1) + " ");
            }
        } else {
            System.out.println(-1);
        }
    }

    private void topSort(int n) {
        for (int i = 0; i < n; i++) {
            if (visited[i] == 1) {
                dfs(i);
            }
        }

        Collections.reverse(ans);
    }

    private void dfs(int current) {
        visited[current] = 2;

        for (Integer i : graph[current]) {
            if (visited[i] == 1) {
                dfs(i);
            } else if (visited[i] == 2) {
                wasCycle = true;
            }
        }

        ans.add(current);
        visited[current] = 3;
    }

    public static void main(String[] args) throws IOException {
        new A().start();
    }
}
