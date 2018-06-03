package HomeWork.Algoritms.lab6;
 
import java.io.*;
import java.util.ArrayList;
 
/**
 * HomeWork.Algoritms.lab6
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */
 
public class B {
 
    public StreamTokenizer t;
 
    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }
 
    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }
 
    private int n;
    private int logN;
    private int parent[], depth[], degreeTwo[];
    private int dp[][], dpWeight[][];
    private ArrayList<Integer> edges[];
 
    private void setDepth(int v, int d) {
        depth[v] = d;
 
        for (int u : edges[v]) {
            setDepth(u, d + 1);
        }
    }
 
    private void preProcess() {
        setDepth(0, 0);
 
        degreeTwo[0] = 1;
 
        for (int i = 1; i < logN + 1; i++) {
            degreeTwo[i] = degreeTwo[i - 1] * 2;
        }
 
        for (int i = 0; i < n; i++) {
            dp[i][0] = parent[i];
        }
 
        for (int j = 1; j < logN; j++) {
            for (int i = 0; i < n; i++) {
                dp[i][j] = dp[dp[i][j - 1]][j - 1];
            }
 
            for (int i = 0; i < n; i++) {
                dpWeight[i][j] = Math.min(dpWeight[i][j - 1], dpWeight[dp[i][j - 1]][j - 1]);
            }
        }
 
    }
 
    private int minOnWay(int v, int u) {
        int min = Integer.MAX_VALUE;
 
        if (depth[v] > depth[u]) {
            int tmp = v;
            v = u;
            u = tmp;
        }
 
        for (int i = logN - 1; i >= 0; i--) {
            if (depth[u] - depth[v] >= degreeTwo[i]) {
                min = Math.min(dpWeight[u][i], min);
                u = dp[u][i];
            }
        }
 
        if (v == u) return min;
 
        for (int i = logN - 1; i >= 0; i--) {
            if (dp[v][i] != dp[u][i]) {
                min = Math.min(dpWeight[u][i], min);
                min = Math.min(dpWeight[v][i], min);
                v = dp[v][i];
                u = dp[u][i];
            }
        }
 
        return Math.min(Math.min(dpWeight[v][0], min), Math.min(dpWeight[u][0], min));
    }
 
    public void start() throws IOException {
//        t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");
 
        t = new StreamTokenizer( new BufferedReader( new FileReader("minonpath.in")));
        PrintWriter writer = new PrintWriter("minonpath.out");
 
        n = nextInt();
 
        parent = new int[n];
        edges = new ArrayList[n];
        depth = new int[n];
        logN = (int) (Math.log(n) / Math.log(2)) + 1;
        dp = new int[n][logN + 1];
        dpWeight = new int[n][logN + 1];
        degreeTwo = new int[logN + 1];
 
        for (int i = 0; i < n; i++) {
            edges[i] = new ArrayList<>();
        }
 
        for (int i = 1; i < n; i++) {
            int v = nextInt() - 1;
            int w = nextInt();
            parent[i] = v;
            dpWeight[i][0] = w;
            edges[v].add(i);
        }
 
        preProcess();
 
        int m = nextInt();
 
        for (int i = 0; i < m; i++) {
            writer.println(minOnWay(nextInt() - 1, nextInt() - 1));
        }
 
        writer.close();
    }
 
    public static void main(String[] arg) throws IOException {
        new B().start();
    }
}
