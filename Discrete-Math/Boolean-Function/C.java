package HomeWork.Discret_math.lab1.lab2;

import java.io.*;
import java.util.LinkedList;

//ГОТОВА К ОТПРАВКЕ. ПРОШЛА ТОЛЬКО СЭМПЛ

/**
 * Created by nikitos on 10.11.17.
 */
public class C {

    public StreamTokenizer t;

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public void start() throws IOException {
        //t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");
        t = new StreamTokenizer( new BufferedReader( new FileReader("mtf.in")));
        PrintWriter writer = new PrintWriter("mtf.out");

        String input = nextString();
        LinkedList<Character> aDec = new LinkedList<>();

        for (int i = 0; i < 26; i++) {
            aDec.addLast((char) ('a' + i));
        }

        for (int i = 0; i < input.length(); i++) {
            int forFind = input.charAt(i);
            for (int j = 0; j < 26; j++) {
                int byteCode = aDec.get(j);
                if (forFind == byteCode) {
                    writer.write((j + 1) + " ");
                    int c = byteCode;
                    aDec.remove(j);
                    aDec.addFirst((char) c);
                }
            }
        }
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        new C().start();
    }

}
