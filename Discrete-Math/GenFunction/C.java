package Homework.DiscretMath.lab1;
 
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
 
/**
 * 2019-04-09 : 14:30
 *
 * @author Nikita Savinov
 */
 
public class C {
    private StreamTokenizer t;
 
    private int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }
 
    private long nextLong() throws IOException {
        t.nextToken();
        return (long) t.nval;
    }
 
    private String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }
 
    private static final Integer MOD = 1000000007;
 
    private void start() throws IOException {
        t = new StreamTokenizer(new InputStreamReader(System.in));
 
        int k = nextInt();
        int m = nextInt();
 
        int[] c = new int[m + 1];
        for (int i = 0; i < k; i++) {
            ++c[nextInt()];
        }
 
        long[] ans = getCount(c, m);
        for (int i = 1; i <= m; i++) {
            System.out.print(ans[i] + " ");
        }
    }
 
    private long[] getCount(int[] c, int m) {
        long[] num = new long[m + 1];
        long[] pref = new long[m + 1];
        pref[0] = num[0] = 1;
 
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= i; j++) {
                if (c[j] != 0) {
                    num[i] += pref[i - j] % MOD;
                    num[i] %= MOD;
                }
            }
 
            for (int j = 0; j <= i; j++)
                pref[i] += (num[j] * num[i - j] % MOD);
                pref[i] %= MOD;
        }
 
        return num;
    }
 
    public static void main(String[] args) throws IOException {
        new C().start();
    }
}
