package HomeWork.Discret_math.lab1.lab2;

import java.io.*;
import java.util.*;

/**
 * Created by nikitos on 10.11.17.
 */
public class D {

    public StreamTokenizer t;

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public void start() throws IOException {
        //t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");
        t = new StreamTokenizer( new BufferedReader( new FileReader("lzw.in")));
        PrintWriter writer = new PrintWriter("lzw.out");

        String input = nextString();
        ArrayList<String> dict = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            dict.add((char)('a' + i) + "");
        }

        for (int i = 0; i < input.length(); i++) {
            boolean f = true;
            boolean neW = false;
            int k = 1;
            int out = 0;
            String buf = "";
            while (f) {
                f = false;
                for (int j = 0; j < dict.size(); j++) {
                    if (i + k <= input.length() && input.substring(i, i + k).equals(dict.get(j))) {
                        k++;
                        f = true;
                        out = j;
                        neW = true;
                        break;
                    }
                    if (i + k > input.length()) {
                        break;
                    }
                }
            }
            if (i + k <= input.length()) {
                buf = input.substring(i, i + k);
            } else buf = "";

            if ((k - 1) > 1) i += (k - 2);

            writer.write(out + " ");
            if (!buf.equals("") && neW) {
                dict.add(buf);
                neW = false;
            }
        }
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        new D().start();
    }

}
