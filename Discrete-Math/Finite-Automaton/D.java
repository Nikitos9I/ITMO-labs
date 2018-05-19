package HomeWork.Discret_math.lab5;

import java.io.*;
import java.util.Stack;

/**
 * HomeWork.Discret_math.lab1.lab5
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class D {

    public StreamTokenizer t;
    public int[][] reverseTransitions;
    public int n;

    public State[] states;
    public int[][] dp;
    public boolean[][] visited;

    public int count;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public class State {
        boolean ending;

        public State(boolean ending) {
            this.ending = ending;
        }
    }

    public void start() throws IOException {
//        t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");

        t = new StreamTokenizer( new BufferedReader( new FileReader("problem4.in")));
        PrintWriter writer = new PrintWriter("problem4.out");

        n = nextInt();
        int m = nextInt();
        int k = nextInt();
        int l = nextInt();

        states = new State[n];

        for (int i = 0; i < n; i++) {
            states[i] = new State(false);
        }

        for (int i = 0; i < k; i++) {
            states[nextInt() - 1].ending = true;
        }

        reverseTransitions = new int[n][n];
        dp = new int[n][l + 3];
        visited = new boolean[n][l + 3];

        for (int i = 0; i < m; i++) {
            int from = nextInt() - 1;
            int to = nextInt() - 1;
            nextString();

            reverseTransitions[to][from]++;
        }

        visited[0][0] = true;
        dp[0][0] = 1;

        for (int i = 0; i < n; i++) {
            if (states[i].ending) {
                count = (count + calculateDp(i, l)) % 1_000_000_007;
            }
        }

        writer.print(count % 1_000_000_007);
        writer.close();
    }

    public int calculateDp(int v, int len) {
        if (len < 0) {
            return 0;
        }

        if (visited[v][len]) {
            return dp[v][len];
        }

        visited[v][len] = true;

        for (int i = 0; i < n; i++) {
            if (reverseTransitions[v][i] != 0) {
                for (int j = 0; j < reverseTransitions[v][i]; j++) {
                    dp[v][len] = (dp[v][len] + calculateDp(i, len - 1)) % 1_000_000_007;
                }
            }
        }

        return dp[v][len];
    }


    public static void main(String[] args) throws IOException {
        new D().start();
    }

}
