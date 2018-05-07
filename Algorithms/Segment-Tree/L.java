package HomeWork.Algoritms.lab4;

import java.io.*;
import java.util.StringTokenizer;

/**
 * HomeWork.Algoritms.lab4
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class L {

    public StreamTokenizer t;
    public Object[] input;
    public int k;

    private class Node {

        int value;
        Node leftChild;
        Node rightChild;

        private Node() {}

        private Node(int value, Node leftChild, Node rightChild) {
            this.value = value;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
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
//        BufferedReader buf = new BufferedReader( new FileReader("arrange.in.txt"));
//        PrintWriter writer = new PrintWriter("arrange.out.txt");

        BufferedReader buf = new BufferedReader( new FileReader("parking.in"));
        PrintWriter writer = new PrintWriter("parking.out");

        StringTokenizer st = new StringTokenizer(buf.readLine());
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        k = do2power(n);

        input = new Object[2*k - 1];
//        Node root = new Node();
//        Node[] input = new Node[n];

        for (int i = k - 1; i < k - 1 + n; i++) {
            input[i] = 1;
        }

        for (int i = k - 2; i >= 0; --i) {
            if (input[i * 2 + 1] != null && input[i * 2 + 2] != null) {
                input[i] = (int) input[i * 2 + 1] + (int) input[i * 2 + 2];
            } else {
                if (input[i * 2 + 1] != null) {
                    input[i] = input[i * 2 + 1];
                } else {
                    input[i] = input[i * 2 + 2];
                }
            }
        }

//        for (int i = 0; i < 2 * k - 1; i++) {
//            System.out.print(input[i] + " ");
//        }
//        System.out.println();

        for (;q > 0; q--) {
            st = new StringTokenizer(buf.readLine());
            if (st.nextToken().equals("exit")) {
                int i = Integer.parseInt(st.nextToken()) + k - 2;
                input[i] = 1;

                while (i != 0) {
                    input[(i - 1) / 2] = (int) input[(i - 1) / 2] + 1;
                    i = (i - 1) / 2;
                }

//                for (int p = 0; p < 2 * k - 1; p++) {
//                    System.out.print(input[p] + " ");
//                }
//                System.out.println();
            } else {
                int i = Integer.parseInt(st.nextToken()) + k - 2;
                if ((int) input[i] == 1) {
                    writer.println(i - k + 2);
                    fill(i);
                } else {
                    int ans = findRight(i);
                    writer.println(ans - k + 2);
                    fill(ans);
                }

//                for (int p = 0; p < 2 * k - 1; p++) {
//                    System.out.print(input[p] + " ");
//                }
//                System.out.println();
            }
        }

        writer.close();
    }

    public void fill(int i) {
        input[i] = 0;
        while (i != 0) {
            input[(i - 1) / 2] = (int) input[(i - 1) / 2] - 1;
            i = (i - 1) / 2;
        }
    }

    public int findRight(int i) {
        while (i != 0) {
            if (i % 2 == 1) {
                if ((int) input[(i - 1) / 2] > 0) {
                    if ((int) input[(i - 1) / 2] != (int) input[i]) {
                        return findLeft(i + 1);
                    } else {
                        i = (i - 1) / 2;
                    }
                } else {
                    i = (i - 1) / 2;
                }
            } else {
                i = (i - 1) / 2;
            }
        }

        return findLeft(0);
    }

    public int findLeft(int pos) {
        while (pos * 2 + 2 < 2 * k - 1) {
            if ((int) input[pos * 2 + 1] > 0) {
                pos = pos * 2 + 1;
            } else {
                pos = pos * 2 + 2;
            }
        }

        return pos;
    }

    public int do2power(int n) {
        int k = 1;
        while (k < n) {
            k *= 2;
        }

        return k;
    }

    public static void main(String[] args) throws IOException {
        new L().start();
    }

}
