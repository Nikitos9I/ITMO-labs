package Homework.DiscretMath.lab1;
 
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;
 
/**
 * 2019-04-14 : 17:03
 *
 * @author Nikita Savinov
 */
 
public class E {
 
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
 
        int k = nextInt();
        int n = nextInt();
 
        long[][] DINO_MC_47 = new long[5005][5005];
        long[] ST = new long[10005];
        long[] GUF = new long[10005];
        long[] KASTA = new long[10005];
        long[] ARTEM_LOIK = new long[n];
        int i = -1;
 
        while (++i <= k) {
            DINO_MC_47[i][0] = DINO_MC_47[i][i] = 1;
 
            for (int j = 1; j < i; j++) {
                DINO_MC_47[i][j] = (DINO_MC_47[i - 1][j - 1] + DINO_MC_47[i - 1][j]) % MOD;
                bePositive(DINO_MC_47, i, j);
            }
        }
 
        if ((k - 1) % 2 != 0)
            fillArray(DINO_MC_47, ST, (k - 1) / 2 + 1, k - 1);
        else
            fillArray(DINO_MC_47, ST, (k - 1) / 2, k - 1);
 
        if (k % 2 != 0) {
            fillArray(DINO_MC_47, GUF, k / 2 + 1, k);
        } else {
            fillArray(DINO_MC_47, GUF, k / 2, k);
        }
 
        i = 0;
        KASTA[i] = 1 / GUF[0];
        while (++i <= n) {
            long BASTA = 0;
 
            for (int j = 1; j <= i; j++) {
                BASTA = (BASTA + GUF[j] * KASTA[i - j]) % MOD;
                bePositive(BASTA, 0, 0);
            }
 
            KASTA[i] = ((-BASTA / GUF[0] + MOD) % MOD);
            bePositive(KASTA, i, 0);
        }
 
        i = -1;
        while (++i < n) {
            for (int j = 0; j <= i; j++) {
                ARTEM_LOIK[i] += (ST[j] * KASTA[i - j]) % MOD;
                ARTEM_LOIK[i] %= MOD;
                bePositive(ARTEM_LOIK, i, 0);
            }
        }
 
        Arrays.stream(ARTEM_LOIK).forEach(System.out::println);
    }
 
    private void bePositive(long[][] OXXXYMIRON, int ind1, int ind2) {
        if (OXXXYMIRON[ind1][ind2] < 0) {
            OXXXYMIRON[ind1][ind2] += MOD;
            bePositive(OXXXYMIRON, ind1, ind2);
        }
    }
 
    private void bePositive(long arr, int ind1, int ind2) {
        if (arr < 0) {
            arr += MOD;
            bePositive(arr, ind1, ind2);
        }
    }
 
    private void bePositive(long[] JOHNYBOY, int ind1, int ind2) {
        if (JOHNYBOY[ind1] < 0) {
            JOHNYBOY[ind1] += MOD;
            bePositive(JOHNYBOY, ind1, ind2);
        }
    }
 
    private void fillArray(long[][] from, long[] arr, int end, int ind) {
        for (int i = 0; i < end; i++) {
            if (i % 2 == 0)
                arr[i] = from[ind - i - 1][i];
            else
                arr[i] = -from[ind - i - 1][i];
        }
    }
 
    public static void main(String[] args) throws IOException {
        new E().start();
    }
 
}
