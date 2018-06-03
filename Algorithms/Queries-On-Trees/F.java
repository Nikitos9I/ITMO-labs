package HomeWork.Algoritms.lab6;
 
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
 
/**
 * HomeWork.Algoritms.lab6
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */
 
public class F {
 
    public StreamTokenizer t;
 
    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }
 
    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }
 
    private class DSU {
        private int parent[], rang[];
        private boolean isDead[];
        int nearLive[];
 
        private DSU(int size) {
            parent = new int[size];
            rang = new int[size];
            nearLive = new int[size];
            isDead = new boolean[size];
 
            for (int i = 0; i < size; i++) {
                rang[i] = 0;
                parent[i] = i;
                nearLive[i] = i;
            }
        }
 
        private int get(int x) {
            if (parent[x] != x)
                parent[x] = get(parent[x]);
            return parent[x];
        }
 
        private void union(int x, int y) {
            int oldX = x, oldY = y;
            x = get(x);
            y = get(y);
 
            int nearL = depth[oldX] < depth[oldY] ? nearLive[x] : nearLive[y];
 
            if (x == y) return;
 
            if (rang[x] == rang[y])
                rang[x]++;
            if (rang[x] < rang[y]) {
                parent[x] = y;
                nearLive[y] = nearL;
            } else {
                parent[y] = x;
                nearLive[x] = nearL;
            }
        }
 
        private int getNearLive(int x) {
            return nearLive[get(x)];
        }
 
        private boolean isDead(int x) {
            return isDead[x];
        }
    }
 
    private int logN;
    private int number;
    private int parent[], depth[], degreeTwo[];
    private int dp[][];
    private DSU DSU_tree;
    private ArrayList<Integer> edges[];
 
    public void start() throws IOException {
//        BufferedReader buf = new BufferedReader( new FileReader("arrange.in.txt"));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");
 
        BufferedReader buf =  new BufferedReader(new InputStreamReader(System.in));
 
        int m = Integer.parseInt(buf.readLine());
        int n = 200_000;
 
        number = 0;
        parent = new int[n];
        DSU_tree = new DSU(n);
        edges = new ArrayList[n];
        depth = new int[n];
        logN = (int) (Math.log(n) / Math.log(2)) + 1;
        dp = new int[n][logN + 1];
        degreeTwo = new int[logN + 1];
 
        for (int i = 0; i < n; i++) {
            edges[i] = new ArrayList<>();
        }
 
        preProcess();
 
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(buf.readLine());
 
            String in = st.nextToken();
            switch (in) {
                case "+":
                    addEdge(Integer.parseInt(st.nextToken()) - 1);
                    break;
                case "?":
                    System.out.println(LCA(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1) + 1);
                    break;
                default:
                    deleteVertex(Integer.parseInt(st.nextToken()) - 1);
                    break;
            }
        }
 
//        writer.close();
    }
 
    private void addEdge(int v) {
        number++;
        parent[number] = v;
        edges[v].add(number);
        depth[number] = depth[v] + 1;
 
        dp[number][0] = v;
 
        for (int j = 1; j < logN; j++) {
            dp[number][j] = dp[dp[number][j - 1]][j - 1];
        }
    }
 
    private void deleteVertex(int v) {
        DSU_tree.isDead[v] = true;
        DSU_tree.nearLive[v] = DSU_tree.nearLive[parent[v]];
 
        if (DSU_tree.isDead(parent[v])) {
            DSU_tree.union(v, parent[v]);
        }
 
        for (int u : edges[v]) {
            if (DSU_tree.isDead(u)) {
                DSU_tree.union(u, v);
            }
        }
    }
 
    private int LCA(int v, int u) {
        if (depth[v] > depth[u]) {
            int tmp = v;
            v = u;
            u = tmp;
        }
 
        for (int i = logN - 1; i >= 0; i--) {
            if (depth[u] - depth[v] >= degreeTwo[i]) {
                u = dp[u][i];
            }
        }
 
        if (v == u) return v;
 
        for (int i = logN - 1; i >= 0; i--) {
            if (dp[v][i] != dp[u][i]) {
                v = dp[v][i];
                u = dp[u][i];
            }
        }
 
        return DSU_tree.getNearLive(parent[v]);
    }
 
    private void preProcess() {
        degreeTwo[0] = 1;
        for (int i = 1; i < logN + 1; i++) {
            degreeTwo[i] = degreeTwo[i - 1] * 2;
        }
    }
 
    public static void main(String[] args) throws IOException {
        new F().start();
    }
 
}
