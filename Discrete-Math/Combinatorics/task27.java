package HomeWork.Discret_math.lab1.lab3;

import java.io.*;

/**
 * Created by nikitos on 26.11.17.
 */
public class task27 {

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
        //BufferedReader buf = new BufferedReader( new FileReader("arrange.in.txt"));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");
        BufferedReader buf = new BufferedReader( new FileReader("nextbrackets.in"));
        PrintWriter writer = new PrintWriter("nextbrackets.out");

        String input = buf.readLine();

        int indexOpen = -1;
        int open = 0;
        int close = 0;
        int p = input.length() - 1;
        while (p >= 0) {
            if (input.charAt(p) == ')') {
                close++;
            } else {
                indexOpen = p;
                open++;
                if (close > open) {
                    break;
                }
            }
            --p;
        }

        input = input.substring(0,input.length() - open - close);
        if (input.length() != 0) {
            input = new StringBuilder(input).append(")").toString();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < open; i++) {
                sb.append("(");
            }
            for (int i = 0; i < close - 1; i++) {
                sb.append(")");
            }
            input = new StringBuilder(input).append(sb).toString();
            writer.write(input);
        } else {
            writer.write("-");
        }

        writer.close();
    }

    public static void main(String[] args) throws IOException {
        new task27().start();
    }

}
