package HomeWork.Algoritms.lab4;

import java.io.*;

/**
 * HomeWork.Algoritms.lab4
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class K {

    public StreamTokenizer t;
    public Node[] input;
    public long[] sum;
    public long[] hashSum;
    public int k;
    public final int TWO_T = (1 << 20) - 1;

    public class Node {

        int max;
        long sum;
        long hashSum;
        int left;
        int right;

        public Node() {}

        public Node(int max, long sum, long hashSum, int left, int right) {
            this.left = left;
            this.right = right;
            this.max = max;
            this.sum = sum;
            this.hashSum = hashSum;
        }

    }

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public void start() throws IOException {
//        t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
//        PrintWriter writer = new PrintWriter("OUTPUT.TXT");

        t = new StreamTokenizer( new BufferedReader( new FileReader("permutation.in")));
        PrintWriter writer = new PrintWriter("permutation.out");

        int n = nextInt();
        k = do2power(n);
        input = new Node[2 * k - 1];

        /* -----===== PREPARE =====----- */

        sum = new long[n + 1];
        sum[1] = 1;
        hashSum = new long[n + 1];
        hashSum[1] = getHash(1);

        for (int i = 2; i < n + 1; i++) {
            sum[i] = i + sum[i - 1];
            hashSum[i] = getHash(i) + hashSum[i - 1];
        }

        /* -----===== MAIN =====----- */

        for (int i = 0; i < 2 * k - 1; i++) {
            input[i] = new Node(0, 0, 0, i, i);
        }

        for (int i = k - 1; i < k - 1 + n; i++) {
            input[i].sum = input[i].max = nextInt();
            input[i].hashSum = getHash(input[i].sum);
        }

        for (int i = k - 2; i >= 0; --i) {
            input[i].max = Integer.max(input[2 * i + 1].max, input[2 * i + 2].max);
            input[i].sum = input[2 * i + 1].sum + input[2 * i + 2].sum;
            input[i].hashSum = input[2 * i + 1].hashSum + input[2 * i + 2].hashSum;
            input[i].left = input[i * 2 + 1].left;
            input[i].right = input[i * 2 + 2].right;
        }

        int m = nextInt();

        for (int i = 0; i < m; i++) {
            int t = nextInt();
            if (t == 1) {
                int x = nextInt() + k - 2;
                int y = nextInt();

                input[x].sum = input[x].max = y;
                input[x].hashSum = getHash(y);
                reneval(x);

            } else {
                int x = nextInt() + k - 2;
                int y = nextInt() + k - 2;

                writer.write(checkAns(x, y)? "YES": "NO");
                writer.write("\n");
            }
        }

        writer.close();
    }

    public void reneval(int i) {
        while (i > 0) {
            i = (i - 1) / 2;

            input[i].max = Integer.max(input[2 * i + 1].max, input[2 * i + 2].max);
            input[i].sum = input[2 * i + 1].sum + input[2 * i + 2].sum;
            input[i].hashSum = input[2 * i + 1].hashSum + input[2 * i + 2].hashSum;
        }
    }

    public boolean checkAns(int left, int right) {
        long sum = 0;
        long hashSum = 0;
        int max = 0;

        while (left <= right) {
            if (left % 2 == 0) {
                sum += input[left].sum;
                hashSum += input[left].hashSum;
                max = Integer.max(max, input[left].max);
            }
            left /= 2;

            if (right % 2 == 1) {
                sum += input[right].sum;
                hashSum += input[right].hashSum;
                max = Integer.max(max, input[right].max);
            }
            right = right / 2 - 1;
        }

        if (this.sum[max] == sum && this.hashSum[max] == hashSum) {
            return true;
        }
        return false;
    }

    public long getHash(long a) {
        long key;

        key = a;

        key += ~(key << 16);
        key ^=  (key >>  5);
        key +=  (key <<  3);
        key ^=  (key >> 13);
        key += ~(key <<  9);
        key ^=  (key >> 17);

        return key;
    }

    public int power(long x) {
        int ans = 17;
        for (int i = 0; i < x; i++) {
            ans *= 17;
            ans &= TWO_T;
        }

        return ans;
    }

    public int binaryPower(int x) {
        int ans = 17;

        if (x == 1) {
            return ans;
        }

        if (x % 2 == 0) {
            ans = binaryPower(x / 2) * binaryPower(x / 2);
        } else {
            ans = binaryPower(x / 2) * binaryPower(x / 2) * ans;
        }

        return ans;
    }

    public int do2power(int n) {
        int k = 1;
        while (k < n) {
            k *= 2;
        }

        return k;
    }

    public static void main(String[] args) throws IOException {
        new K().start();
    }

}
