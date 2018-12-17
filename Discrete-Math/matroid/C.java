package HomeWork.DiscretMath;

import java.io.*;
import java.util.*;

/**
 * HomeWork.DiscretMath
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class C {

    public StreamTokenizer t;

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

        t = new StreamTokenizer( new BufferedReader( new FileReader("matching.in")));
        PrintWriter writer = new PrintWriter("matching.out");

        int n = nextInt();

        int[][] weight = new int[n][2];
        List<List<Integer>> edges = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            weight[i][0] = nextInt();
            weight[i][1] = i;
            edges.add(new ArrayList<>());
        }

        for (int i = 0; i < n; i++) {
            int num = nextInt();

            for (int j = 0; j < num; j++) {
                edges.get(i).add(nextInt() - 1);
            }
        }

        Arrays.sort(weight, (o1, o2) -> Integer.compare(o2[0], o1[0]));

        int[] ans = new int[n];
        Arrays.fill(ans, -1);

        for (int i = 0; i < n; i++) {
            boolean[] used = new boolean[n];
            kuhn(used, weight[i][1], edges, ans);
        }

        for (int i = 0; i < n; i++) {
            boolean was = false;

            for (int j = 0; j < n; j++) {
                if (ans[j] == i) {
                    writer.write(j + 1 + " ");
                    was = true;
                }
            }

            if (!was)
                writer.write("0 ");
        }

        writer.close();
    }

    private boolean kuhn(boolean[] used, int v, List<List<Integer>> edges, int[] ans) {
        if (used[v])
            return false;

        used[v] = true;
        for (int i = 0; i < edges.get(v).size(); ++i) {
            int to = edges.get(v).get(i);

            if (ans[to] == -1 || kuhn(used, ans[to], edges, ans)) {
                ans[to] = v;
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        new C().start();
    }

}
