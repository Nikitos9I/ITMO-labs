package HomeWork.Discret_math.lab1.lab3;

import java.io.*;

/**
 * Created by nikitos on 22.11.17.
 */
public class task23 {

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
        BufferedReader buf = new BufferedReader( new FileReader("arrange.in.txt"));
        PrintWriter writer = new PrintWriter("arrange.out.txt");
        //BufferedReader buf = new BufferedReader( new FileReader("nextvector.in"));
        //PrintWriter writer = new PrintWriter("nextvector.out");

        String input = buf.readLine().trim();

        int count1 = 0, count0 = 0, lastIndex1 = 0, lastIndex0 = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '1') {
                count1++;
                lastIndex1 = i;
            } else {
                count0++;
                lastIndex0 = i;
            }
        }

        if (count0 < input.length()) {
            String copy = input;
            copy = replaceCharAt(copy,lastIndex1,'0');
            copy = replaceCharAfter1(copy,lastIndex1,copy.length() - lastIndex1);
            writer.write(copy + "\n");
        } else {
            writer.write("-\n");
        }

        if (count1 < input.length()) {
            input = replaceCharAt(input,lastIndex0,'1');
            input = replaceCharAfter0(input,lastIndex0,input.length() - lastIndex0);
            writer.write(input + "\n");
        } else {
            writer.write("-\n");
        }

        writer.close();
    }

    public String replaceCharAt(String s, int pos, char c) {
        return s.substring(0,pos) + c + s.substring(pos+1);
    }

    public String replaceCharAfter1(String input, int pos, int number) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < number - 1; i++) {
            sb.append(1);
        }
        return new StringBuilder(input.substring(0,pos + 1)).append(sb).toString();
    }

    public String replaceCharAfter0(String input, int pos, int number) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < number - 1; i++) {
            sb.append(0);
        }
        return new StringBuilder(input.substring(0,pos + 1)).append(sb).toString();
    }

    public static void main(String[] args) throws IOException {
        new task23().start();
    }

}
