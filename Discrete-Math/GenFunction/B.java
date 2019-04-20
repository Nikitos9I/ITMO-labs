package Homework.DiscretMath.lab1;
 
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;
 
/**
 * 2019-04-09 : 11:25
 *
 * @author Nikita Savinov
 */
 
public class B {
 
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
 
    private static final Integer MOD = 998_244_353;
 
    private void start() throws IOException {
        t = new StreamTokenizer(new InputStreamReader(System.in));
 
        int n = nextInt();
        int m = nextInt();
 
        long[] frac = new long[Integer.max(n + 1, m)];
        for (int i = 0; i < n + 1; i++) {
            frac[i] = nextInt();
        }
 
        if (m == 1) {
            System.out.println(1);
            System.out.println(1);
            System.out.println(0);
            return;
        }
 
//        try {
            Arrays.stream(subTask1(frac, m)).forEach(e -> System.out.print(e + " "));
            System.out.println();
            Arrays.stream(subTask2(frac, m)).forEach(e -> System.out.print(e + " "));
            System.out.println();
            Arrays.stream(subTask3(frac, m)).forEach(e -> System.out.print(e + " "));
//        } catch (Exception e) {
//            System.exit(0);
//        }
    }
 
    private long[] subTask1(long[] frac, int m) {
        long[] ans = new long[m];
        long[] time = new long[m];
        ans[0] = 1;
        long[] newFrac = frac.clone();
        long factor = 1;
        long two = 1;
        long qqe = 1;
        int minusCount = 0;
        int topper = 3;
 
        for (int j = 1; j < m; ++j) {
            topper -= 2;
            if (topper < 0) ++minusCount;
 
            two *= 2;
            two %= MOD;
            qqe *= Math.abs(topper);
            qqe %= MOD;
            factor *= j % MOD;
            factor %= MOD;
            long rev;
            if (minusCount % 2 != 0)
                rev = longInverse(MOD - ((factor * two) % MOD));
            else
                rev = longInverse((factor * two) % MOD);
 
            for (int i = 1; i < m; i++) {
                time[i] = (rev * newFrac[i]) % MOD;
                time[i] %= MOD;
                time[i] = (time[i] * qqe) % MOD;
                if (time[i] < 0)
                    time[i] = (time[i] + MOD) % MOD;
                time[i] %= MOD;
            }
 
            for (int i = 1; i < m; i++) {
                ans[i] += time[i];
                if (ans[i] < 0)
                    ans[i] = (ans[i] + MOD);
                ans[i] %= MOD;
            }
 
            newFrac = getMultiFactor(newFrac, frac, m);
        }
 
        return ans;
    }
 
    private long[] subTask2(long[] frac, int m) {
        long[] ans = new long[m];
        long[] time = new long[m];
        ans[0] = 1;
        long[] newFrac = frac.clone();
        long factor = 1;
 
        for (int j = 1; j < m; ++j) {
            factor *= j % MOD;
            factor %= MOD;
            long rev = longInverse(factor);
 
            for (int i = 1; i < m; i++) {
                time[i] = (rev * newFrac[i]) % MOD;
                if (time[i] < 0)
                    time[i] = (time[i] + MOD) % MOD;
                time[i] %= MOD;
            }
 
            for (int i = 1; i < m; i++) {
                ans[i] += time[i];
                if (ans[i] < 0)
                    ans[i] = (ans[i] + MOD);
                ans[i] %= MOD;
            }
 
            newFrac = getMultiFactor(newFrac, frac, m);
        }
 
        return ans;
    }
 
    private long[] subTask3(long[] frac, int m) {
        long[] ans = new long[m];
        long[] time = new long[m];
        ans[0] = 0;
        long[] newFrac = frac.clone();
 
        for (int j = 1; j < m; ++j) {
            long rev;
            if (j % 2 == 0)
                rev = longInverse(MOD - j);
            else
                rev = longInverse(j);
 
            for (int i = 1; i < m; i++) {
                time[i] = (rev * newFrac[i]) % MOD;
                if (time[i] < 0)
                    time[i] = (time[i] + MOD) % MOD;
            }
 
            for (int i = 1; i < m; i++) {
                ans[i] += time[i];
                if (ans[i] < 0)
                    ans[i] = (ans[i] + MOD);
                ans[i] %= MOD;
            }
 
            newFrac = getMultiFactor(newFrac, frac, m);
        }
 
        return ans;
    }
 
    private long longInverse(long x) {
        long power = MOD;
 
        long a = Math.abs(x);
        long b = power;
        long sign = (x < 0) ? -1 : 1;
 
        long c1 = 1;
        long d1 = 0;
        long c2 = 0;
        long d2 = 1;
 
        while (b > 0) {
            long q = a / b;
            long r = a % b;
 
            long c3 = c1 - q*c2;
            long d3 = d1 - q*d2;
 
            c1 = c2;
            d1 = d2;
            c2 = c3;
            d2 = d3;
            a = b;
            b = r;
        }
 
        if (a != 1) { throw new RuntimeException("gcd not 1 !"); }
 
        for (int i = 0; i < 4; ++i) {
            long possinv = sign * (c1 + (i * power));
            if (possinv * x % MOD == 1L) { return possinv; }
        }
 
        throw new RuntimeException("failed");
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
 
    public static void main(String[] args) throws IOException {
        new B().start();
    }
 
}
