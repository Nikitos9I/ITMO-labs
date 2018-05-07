package HomeWork.Discret_math.lab1.lab3;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by nikitos on 22.11.17.
 */
public class task03 {

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
        //t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");
        t = new StreamTokenizer( new BufferedReader( new FileReader("antigray.in")));
        PrintWriter writer = new PrintWriter("antigray.out");

        int n = nextInt();

        ArrayList<String> aList = new ArrayList<>();
        aList.add("0");

        for (int j = 0; j < n; ++j) {
            for (int i = 0; i < aList.size(); ++i) {
                StringBuilder nowPlusOne = new StringBuilder();
                StringBuilder nowPlusTwo = new StringBuilder();
                for (int k = 0; k < aList.get(i).length(); k++) {
                    nowPlusOne.append((Integer.parseInt(aList.get(i).charAt(k) + "") + 1) % 3);
                    nowPlusTwo.append((Integer.parseInt(aList.get(i).charAt(k) + "") + 2) % 3);
                }
                aList.add(i + 1,nowPlusOne.toString());
                aList.add(i + 2,nowPlusTwo.toString());
                i += 2;
            }
            if (j == n - 1) {
                for (int i = 0; i < aList.size(); i++) {
                    writer.write(aList.get(i) + "\n");
                }
            }
            for (int i = 0; i < aList.size(); i++) {
                aList.set(i, new StringBuilder("0").append(aList.get(i)).toString());
            }
            Collections.sort(aList);
        }

        writer.close();
    }

    public int exp(int n) {
        int a = 1;
        for (int i = 0; i < n; i++) {
            a *= 3;
        }
        return a;
    }

    public static void main(String[] args) throws IOException {
        new task03().start();
    }

}
