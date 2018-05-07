package HomeWork.Discret_math.lab1.lab2;

import java.io.*;

/**
 * Created by nikitos on 10.11.17.
 */
public class B {

    public StreamTokenizer t;

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public void start() throws IOException {
        t = new StreamTokenizer( new BufferedReader( new FileReader("bwt.in")));
        PrintWriter writer = new PrintWriter("bwt.out");
        //t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");

        String input = nextString();

        String[] strArr = new String[input.length()];
        for (int i = 0; i < input.length(); i++) {
            strArr[i] = new StringBuilder(input.substring(i,input.length())).append(input.substring(0,i)).toString();
        }

        int[] intArr = new int[26];
        int[] position = new int[26];
        String[] newStrArr = new String[input.length()];

        int pos = input.length() - 1;
        for (int i = 0; i < input.length(); i++) {
            for (int j = 0; j < input.length(); j++) {
                intArr[strArr[j].charAt(pos) - 'a']++;
            }
            for (int j = 1; j < 26; j++) {
                position[j] = position[j-1] + intArr[j-1];
            }
            for (int j = 0; j < input.length(); j++) {
                newStrArr[position[strArr[j].charAt(pos) - 'a']++] = strArr[j];
            }
            pos--;
            for (int j = 0; j < input.length(); j++) {
                strArr[j] = newStrArr[j];
            }
            for (int j = 0; j < 26; j++) {
                intArr[j] = 0;
                position[j] = 0;
            }
        }

        for (int i = 0; i < input.length(); i++) {
            writer.write(newStrArr[i].charAt(input.length() - 1));
        }

        writer.close();

    }

    public static void main(String[] args) throws IOException {
        new B().start();
    }

}
