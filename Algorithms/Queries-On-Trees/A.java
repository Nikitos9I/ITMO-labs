package HomeWork.Algoritms.lab6;
 
import java.io.*;
import java.util.ArrayList;
import java.util.List;
 
/**
 * HomeWork.Algoritms.lab6
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */
 
public class A {
 
    public static StreamTokenizer t;
 
    public static int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }
 
    public static String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }
 
    private int len;
    private int[][] up;
    private int[] tin;
    private int[] tout;
    private int time;
 
    private void dfs(List<Integer>[] tree, int u, int p) {
        tin[u] = time++;
        up[0][u] = p;
        for (int i = 1; i < len; i++)
            up[i][u] = up[i - 1][up[i - 1][u]];
        for (int v : tree[u])
            if (v != p)
                dfs(tree, v, u);
        tout[u] = time++;
    }
 
    public A(List<Integer>[] tree, int root) {
        int n = tree.length;
        len = 1;
        while ((1 << len) <= n) ++len;
        up = new int[len][n];
        tin = new int[n];
        tout = new int[n];
        dfs(tree, root, root);
    }
 
    private boolean isParent(int parent, int child) {
        return tin[parent] <= tin[child] && tout[child] <= tout[parent];
    }
 
    private int lca(int a, int b) {
        if (isParent(a, b))
            return a;
        if (isParent(b, a))
            return b;
        for (int i = len - 1; i >= 0; i--)
            if (!isParent(up[i][a], b))
                a = up[i][a];
        return up[0][a];
    }
 
    public static void main(String[] args) throws IOException {
        t = new StreamTokenizer( new BufferedReader(new InputStreamReader(System.in)));
 
        int n = nextInt();
        List<Integer>[] tree = new List[n];
 
        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }
 
        for (int i = 1; i < n; i++) {
            int current = nextInt() - 1;
            tree[current].add(i);
            tree[i].add(current);
        }
 
        A get = new A(tree, 0);
 
        int m = nextInt();
        for (int i = 0; i < m; i++) {
            System.out.println(get.lca(nextInt() - 1, nextInt() - 1) + 1);
        }
    }
 
}
