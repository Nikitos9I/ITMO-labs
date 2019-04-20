package Homework.DiscretMath.lab1;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;
 
/**
 * 2019-04-09 : 14:13
 *
 * @author Nikita Savinov
 */
 
public class F {
 
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
 
    private int k;
    private static final Integer MOD = 104857601;
 
    private void start() throws IOException {
        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
 
        String[] literals = buf.readLine().split(" ");
        k = Integer.parseInt(literals[0]);
        long n = Long.parseLong(literals[1]);
 
        Long[] a = new Long[2000];
        Long[] q = new Long[1005];
        q[0] = 1L;
 
        literals = buf.readLine().split(" ");
        for (int i = 0; i < k; i++) {
            a[i] = Long.parseLong(literals[i]);
        }
 
//        for (int i = 0; i < 2000; i++) {
//            a.add(0L);
//        }
 
        literals = buf.readLine().split(" ");
        for (int i = 0; i < k; i++) {
            q[i + 1] = (MOD - Long.parseLong(literals[i])) % MOD;
        }
 
        System.out.println(getN(n - 1, a, q));
    }
 
    private Long getN(long n, Long[] a, Long[] q) {
        long[] r = new long[1005];
        long[] minus = new long[1005];
 
        while (n >= k) {
            sum(a, q);
 
            for (int i = 0; i <= k; i++) {
                minus[i] = (i % 2 == 0 ? q[i] : (MOD - q[i]) % MOD);
            }
 
            for (int i = 0; i <= 2 * k; i += 2) {
                long root = 0;
 
                for (int j = 0; j <= i; j++) {
                    root = (root + (j > k ? 0 : q[j]) * (i - j > k ? 0 : minus[i - j]) + MOD) % MOD;
                }
 
                r[i / 2] = root;
            }
 
            for (int i = 0; i <= k; i++) {
                q[i] = r[i];
            }
 
            filter(a, n, 0);
            n /= 2;
        }
 
        return a[(int) n];
    }
 
    private void sum(Long[] a, Long[] q) {
        int i = k - 1;
 
        while (++i < 2 * k) {
            a[i] = 0L;
 
            for (int j = 1; j <= k; j++) {
                a[i] = (a[(i)] - q[(j)] * a[(i - j)]) % MOD;
 
                while (a[(i)] < 0)
                    a[i] = a[i] + MOD;
            }
 
        }
    }
 
    private void filter(Long[] a, long n, int isAvailable) {
        for (int i = 0; i < 2 * k; i++) {
            if (n % 2 == i % 2) {
                a[isAvailable++] = a[i];
            }
        }
    }
 
    public static void main(String[] args) throws IOException {
        new F().start();
    }
}
