package HomeWork.Algoritms.lab2;

import java.io.*;
import java.util.ArrayDeque;
import java.util.LinkedList;

import static java.lang.Math.abs;

/**
 * Created by nikitos on 11.11.17.
 */
public class J_binary {
    public StreamTokenizer t;
    public long ans = 0;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public void start() throws IOException {
        t = new StreamTokenizer( new BufferedReader( new FileReader("bureaucracy.in")));
        PrintWriter writer = new PrintWriter("bureaucracy.out");
        //t = new StreamTokenizer(new BufferedReader(new FileReader("arrange.in.txt")));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");

        int n = nextInt(), m = nextInt();

        int[] inputs = new int[n];
        long sum = 0;

        for (int i = 0; i < n; i++) {
            inputs[i] = nextInt();
            sum += inputs[i];
        }

        if (m < sum) {
            if (m > n) {
                int answ = binarySearch(inputs, m);
                if (ans < m) {
                    m -= ans;

                    for (int i = 0; i < n; i++) {
                        inputs[i] -= answ;
                    }
                } else {
                    ans = 0;
                    for (int i = 0; i < inputs.length; i++) {
                        if (inputs[i] < (answ - 1)) {
                            ans += inputs[i];
                        } else {
                            ans += (answ - 1);
                        }
                    }
                    m -= ans;

                    for (int i = 0; i < n; i++) {
                        inputs[i] -= (answ - 1);
                    }
                }

            }

            ArrayDeque<Integer> aD = new ArrayDeque<>();
            for (int i = 0; i < n; i++) {
                if (inputs[i] > 0)
                    aD.add(inputs[i]);
            }

            while (aD.peek() != null && m > 0) {
                m--;
                int c = aD.pop();
                if (c - 1 > 0) aD.add(c - 1);
            }

            writer.write(aD.size() + "\n");
            while (aD.size() > 0) {
                writer.write(aD.pop() + " ");
            }
        } else {
            writer.write("-1");
        }

        writer.close();
    }

    public int binarySearch(int[] inputs, int m) {
        int l = -1;
        int r = m;
        while (l < r - 1) {
            ans = 0;
            int mid = (l + r) / 2;
            for (int i = 0; i < inputs.length; i++) {
                if (inputs[i] < mid) {
                    ans += inputs[i];
                } else {
                    ans += mid;
                }
            }
            if (ans < m) {
                l = mid;
            } else {
                r = mid;
            }
        }
        return l;
    }

    public int min(int a, int b) {
        return (a<b?a:b);
    }

    public static void main(String[] args) throws IOException {
        new J_binary().start();
    }
}
