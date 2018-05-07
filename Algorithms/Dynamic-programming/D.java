package HomeWork.Algoritms.lab3;

import java.io.*;

/**
 * Created by nikitos on 05.12.17.
 */
public class D {

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
        t = new StreamTokenizer( new BufferedReader( new FileReader("input.txt")));
        PrintWriter writer = new PrintWriter("output.txt");
        //t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");

        String input1 = nextString();
        String input2 = nextString();

        writer.println(space(input1,input2));
        writer.close();

    }

    public int space(String S1, String S2) {
        int[] D1, D2 = new int[S2.length() + 1];

        int i = -1;
        while ( ++i <= S2.length()) {
            D2[i] = i;
        }

        i = 0;
        while (++i <= S1.length()) {
            D1 = D2;
            D2 = new int[S2.length() + 1];
            int j = -1;
            while (++j <= S2.length()) {
                if(j == 0) D2[j] = i;
                else {
                    int howMuch;
                    if (S1.charAt(i - 1) != S2.charAt(j - 1)) {
                        howMuch = 1;
                    } else {
                        howMuch = 0;
                    }
                    if(D2[j - 1] < D1[j] && D2[j - 1] < D1[j - 1] + howMuch)
                        D2[j] = D2[j - 1] + 1;
                    else if(D1[j] < D1[j - 1] + howMuch)
                        D2[j] = D1[j] + 1;
                    else
                        D2[j] = D1[j - 1] + howMuch;
                }
            }
        }

        return D2[S2.length()];
    }

    public static void main(String[] args) throws IOException {
        new D().start();
    }

}
