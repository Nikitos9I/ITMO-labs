package Homework.DiscretMath.lab1;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;
 
/**
 * 2019-04-14 : 13:00
 *
 * @author Nikita Savinov
 */
 
public class D {
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
 
    private class Node {
        private char value;
 
        private Node leftChild = null;
        private Node rightChild = null;
 
        private Node(char value) {
            this.value = value;
        }
    }
 
    private void start() throws IOException {
        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
 
        String input = buf.readLine();
        Node root = makeGraph(input);
 
        Arrays.stream(dfs(root)).forEach(e -> System.out.print(e + " "));
    }
 
    private long[] dfs(Node current) {
        if (current == null)
            return null;
 
        if (current.value == 'L') {
            int i = 0;
            long[] res = new long[7];
            long[] fromChild = dfs(current.leftChild);
            res[0] = 1;
 
            while (++i < 7) {
                for (int j = 1; j <= i; j++) {
                    res[i] += fromChild[j] * res[i - j];
                }
            }
 
            return res;
        }
 
        if (current.value == 'B')
            return new long[]{0, 1, 0, 0, 0, 0, 0};
 
        if (current.value == 'P') {
            int i = -1;
            long[] c1 = dfs(current.leftChild);
            long[] c2 = dfs(current.rightChild);
            long[] res = new long[7];
 
            while (++i < 7) {
                for (int j = 0; j <= i; j++) {
                    res[i] += c1[j] * c2[i - j];
                }
            }
 
            return res;
        }
 
        int i = 0;
        long[] res = new long[7];
        long[] fromChild = dfs(current.leftChild);
        long[][] arr = fillForS(new long[7][7], fromChild);
        res[0] = 1;
 
        while (++i < 7) {
            for (int k = 1; k < 7; k++) {
                for (int j = 0; j <= i / k ; j++) {
                    arr[i][k] += get(Long.max(fromChild[k] + j - 1, 0), j) * arr[i - j * k][k - 1];
                }
            }
 
            res[i] = arr[i][i];
        }
 
        return res;
    }
 
    private int index = 0;
 
    private Node makeGraph(String input) {
        while (!Character.isLetter(input.charAt(index))) {
            ++index;
        }
 
        Node parent = new Node(input.charAt(index++));
 
        if (parent.value == 'B')
            return parent;
 
        if (parent.value == 'P') {
            parent.leftChild = makeGraph(input);
            parent.rightChild = makeGraph(input);
        } else {
            parent.leftChild = makeGraph(input);
        }
 
        return parent;
    }
 
    private long[][] fillForS(long[][] arr, long[] fromChild) {
        arr[0][0] = 1;
 
        for (int i = 1; i < 7; i++) {
            arr[0][i] = 1;
            arr[i][0] = 0;
        }
 
        return arr;
    }
 
    private long get(long n, long k) {
        long res = 1;
        long i = (n - k + 1) - 1;
 
        while (++i <= n)
            res *= i;
 
        i = 1;
        while (++i <= k)
            res /= i;
 
        return res;
    }
 
 
    public static void main(String[] args) throws IOException {
        new D().start();
    }
}
