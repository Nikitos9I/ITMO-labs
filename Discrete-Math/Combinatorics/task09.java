package HomeWork.Discret_math.lab1.lab3;

import java.io.*;

/**
 * Created by nikitos on 23.11.17.
 */
public class task09 {

    public StreamTokenizer t;
    public PrintWriter writer;
    public int[] input;
    public int countZero = 0;
    public int countOne = 0;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public void start() throws IOException {
        t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        writer = new PrintWriter("arrange.out.txt");
        //t = new StreamTokenizer( new BufferedReader( new FileReader("brackets.in")));
        //writer = new PrintWriter("brackets.out");

        int n = nextInt();
        input = new int[2*n];
        for (int i = 0; i < 2*n; i++) {
            input[i] = -1;
        }

        req(0);

        writer.close();
    }

    public void req(int pos) {
        if (pos >= input.length) {
            if (countZero == countOne) {
                boolean f = true;
                int c0 = 0, c1 = 0;
                for (int i = 0; i < input.length; i++) {
                    if (c0 < c1) {
                        f = false;
                    }
                    if (input[i] == 0) c0++; else c1++;
                }
                if (f) {
                    for (int k = 0; k < input.length; ++k) {
                        if (input[k] == 0) {
                            writer.write("(");

                        } else {
                            writer.write(")");
                            c1++;
                        }
                    }
                    writer.write("\n");
                }
            }
            return;
        }

        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                if (countZero < input.length / 2 && pos != input.length - 1) {
                    if (input[pos] == 1) countOne--;
                    if (input[pos] == 0) countZero--;
                    input[pos] = i;
                    countZero++;
                } else {
                    continue;
                }
            } else {
                if (pos != 0) {
                    if (input[pos] == 0) countZero--;
                    if (input[pos] == 1) countOne--;
                    input[pos] = i;
                    countOne++;
                } else {
                    continue;
                }
            }
            /*if (countZero < input.length / 2 && i == 0) {
                input[pos] = i;
                countZero++;
            } else {
                input[pos] = 1;
                countOne++;
            }*/
            //if (input[pos] == 0) countZero++; else countOne++;

            req(pos + 1);
        }
    }

    public static void main(String[] args) throws IOException {
        new task09().start();
    }

}
