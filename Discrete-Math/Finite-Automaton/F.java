package HomeWork.Discret_math.lab5;

import java.io.*;

/**
 * HomeWork.Discret_math.lab1.lab5
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class F {

    public StreamTokenizer t;
    private boolean[] visited;

    private State[] statesFirst;
    private State[] statesSecond;

    private Transition[] transitionsFirst;
    private Transition[] transitionsSecond;

    private String alpha;

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

    public class Transition {
        int to;

        char symbol;

        public Transition(int to, char symbol) {
            this.to = to;
            this.symbol = symbol;
        }
    }

    public void start() throws IOException {
//        t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");

        t = new StreamTokenizer( new BufferedReader( new FileReader("isomorphism.in")));
        PrintWriter writer = new PrintWriter("isomorphism.out");

        int n = nextInt();
        int m = nextInt();
        int k = nextInt();

        statesFirst = new State[n];
        for (int i = 0; i < n; i++) {
            statesFirst[i] = new State(false);
        }

        for (int i = 0; i < k; i++) {
            statesFirst[nextInt() - 1].ending = true;
        }

        transitionsFirst = new Transition[m];
        for (int i = 0; i < m; i++) {
            transitionsFirst[nextInt() - 1] = new Transition( nextInt() - 1, nextString().charAt(0));
        }

        n = nextInt();
        m = nextInt();
        k = nextInt();

        statesSecond = new State[n];
        for (int i = 0; i < n; i++) {
            statesSecond[i] = new State(false);
        }

        for (int i = 0; i < k; i++) {
            statesSecond[nextInt() - 1].ending = true;
        }

        transitionsSecond = new Transition[m];
        for (int i = 0; i < m; i++) {
            transitionsSecond[nextInt() - 1] = new Transition(nextInt() - 1, nextString().charAt(0));
        }

        visited = new boolean[n];
        alpha = "abcdefghijklmnopqrstuvwxyz";

        writer.print(dfs(0, 0)? "YES": "NO");
        writer.close();
    }

    private short checkCurrent(Transition a, Transition b) {
        if (a == null && b == null) {
            return 0;
        }

        if (a == null) {
            return 1;
        }

        if (b == null) {
            return 2;
        }

        return 3;
    }

    private boolean dfs(int u, int v) {
        visited[u] = true;

        if (statesFirst[u].ending != statesSecond[v].ending) {
            return false;
        }

        boolean ans = true;

        for (char c : alpha.toCharArray()) {
            switch (checkCurrent(transitionsFirst[u], transitionsSecond[v])) {
                case 0:
                    return ans;
                case 1:
                case 2:
                    return false;
                case 3:
                    try {
                        if ((transitionsFirst[u].symbol == c) != (transitionsSecond[v].symbol == c)) {
                            return false;
                        } else if (transitionsFirst[u].symbol == c && transitionsSecond[v].symbol == c && !visited[transitionsFirst[u].to]) {
                            ans &= dfs(transitionsFirst[u].to, transitionsSecond[v].to);
                        }
                    } catch (Exception e) {
                        return false;
                    }
            }
        }

        return ans;
    }

    public static void main(String[] args) throws IOException {
        new F().start();
    }
}
