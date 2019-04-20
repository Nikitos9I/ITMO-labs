package Homework.DiscretMath.lab1;
 
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
 
/**
 * 2019-04-09 : 10:13
 *
 * @author Nikita Savinov
 */
 
public class A {
 
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
 
    private static final long MOD = 998_244_353;
 
    private void start() throws IOException {
        t = new StreamTokenizer(new InputStreamReader(System.in));
 
        int n = nextInt();
        int m = nextInt();
 
        long[] power_n = new long[6005];
        long[] power_m = new long[6005];
 
        for (int i = 0; i < n + 1; i++) {
            power_n[i] = nextInt();
        }
 
        for (int i = 0; i < m + 1; i++) {
            power_m[i] = nextInt();
        }
 
        System.out.println(Integer.max(n, m));
        for (int i = 0; i < Integer.max(n, m) + 1; i++) {
            System.out.print((power_n[i] + power_m[i]) % MOD + " ");
        }
        System.out.println();
 
        System.out.println(m + n);
        long[] pow = getMultiFactor(power_n, power_m, n + m + 1);
        for (int i = 0; i < n + m + 1; i++) {
            System.out.print(pow[i] + " ");
        }
 
        System.out.println();
        pow = getDivFactor(power_n, power_m);
        for (int i = 0; i < 1000; i++) {
            System.out.print(pow[i] + " ");
        }
    }
 
    private long[] getMultiFactor(long[] a, long[] b, int max) {
        long[] c = new long[max];
 
        for (int i = 0; i < max; i++) {
            for (int j = 0; j <= i; j++) {
                c[i] += a[j] * b[i - j];
                c[i] %= MOD;
            }
        }
 
        return c;
    }
 
    private long[] getDivFactor(long[] a, long[] b) {
        long[] c = new long[1001];
 
        for (int i = 0; i < 1000; i++) {
            long acc = 0;
 
            for (int j = 0; j < i; j++) {
                acc += (c[j] * b[i - j] + MOD);
                acc %= MOD;
            }
 
            c[i] = ((a[i] - acc) / b[0] + MOD) % MOD;
        }
 
        return c;
    }
 
    public static void main(String[] args) throws IOException {
        new A().start();
    }
 
}
