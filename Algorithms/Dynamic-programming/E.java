package HomeWork.Algoritms.lab3;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by nikitos on 06.12.17.
 */
public class E {

    public StreamTokenizer t;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public void start() throws IOException {
        t = new StreamTokenizer( new BufferedReader( new InputStreamReader(System.in)));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");
        //t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        //PrintWriter writer = new PrintWriter("OUTPUT.TXT");
        //t = new StreamTokenizer( new BufferedReader( new FileReader("INPUT.TXT")));

        int n = nextInt();
        if (n == 0) {
            System.out.println(0);
            System.out.println("0 0");
        } else if (n == 1) {
            int a = nextInt();
            System.out.println(a + "\n");
            if (a > 100) {
                System.out.println("1 0");
            } else {
                System.out.println("0 0");
            }
        } else {
            int[] inputs = new int[n + 1];

            for (int i = 1; i <= n; i++) {
                inputs[i] = nextInt();
            }

            int[][] spending = new int[n + 1][n];

            spending[0][0] = 0;
            for (int i = 1; i < n; i++) {
                spending[0][i] = 1000000;
            }

            for (int i = 1; i < n + 1; i++) {
                for (int j = 0; j < n; j++) {

                    if (inputs[i] > 100) {
                        spending[i][j] = min(j > 0 ? spending[i - 1][j - 1] + inputs[i] : 1000000, j < n - 1 ? spending[i - 1][j + 1] : 1000000);
                    } else {
                        spending[i][j] = min(spending[i - 1][j] + inputs[i], j < n - 1 ? spending[i - 1][j + 1] : 1000000);
                    }
                }
            }

            int minimum = spending[n][0];
            int kupon1 = 0;
            for (int i = 0; i < n; i++) {
                kupon1 = (spending[n][i] <= minimum && spending[n][i] < 1000000 ? i : kupon1);
                minimum = (spending[n][i] <= minimum && spending[n][i] < 1000000 ? spending[n][i] : minimum);
            }

            System.out.println(minimum);
            System.out.print(kupon1 + " ");

            int kupon2 = 0;
            ArrayList<Integer> aList = new ArrayList<>();
            int i = n + 1;
            while (--i > 0) {
                if (inputs[i] <= 100) {
                    if (minimum == spending[i - 1][kupon1] + inputs[i]) {
                        minimum -= inputs[i];
                    } else {
                        aList.add(i);
                        kupon1++;
                        kupon2++;
                    }
                } else {
                    if (kupon1 == 0) {
                        aList.add(i);
                        kupon1++;
                        kupon2++;
                    } else {
                        if (minimum == spending[i - 1][kupon1 - 1] + inputs[i]) {
                            kupon1--;
                            minimum -= inputs[i];
                        } else {
                            aList.add(i);
                            kupon1++;
                            kupon2++;
                        }
                    }
                }
            }

            System.out.println(kupon2);
            Collections.reverse(aList);
            for (int j = 0; j < aList.size(); j++) {
                System.out.println(aList.get(j));
            }
        }
    }

    public int min(int a, int b) {
        return (a < b? a: b);
    }

    public static void main(String[] args) throws IOException {
        new E().start();
    }

}
