package HomeWork.Algoritms.lab2;

import java.io.*;
import java.util.ArrayDeque;

/**
 * Created by nikitos on 08.11.17.
 */
public class G {

    public void start() throws IOException {
        //BufferedReader buf = new BufferedReader( new FileReader("arrange.in.txt"));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");
        BufferedReader buf = new BufferedReader( new FileReader("hospital.in"));
        PrintWriter writer = new PrintWriter("hospital.out");

        int n = Integer.parseInt(buf.readLine());
        ArrayDeque<Integer> aDecLeft = new ArrayDeque<>();
        ArrayDeque<Integer> aDecRight = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            String input = buf.readLine();
            if (input.charAt(0) == '+') {
                aDecLeft.addFirst(Integer.parseInt(input.substring(2,input.length())));
                if (aDecLeft.size() > aDecRight.size() && aDecLeft.size() > 0) {
                    aDecRight.addFirst(aDecLeft.pollLast());
                }
            } else if (input.charAt(0) == '*') {
                if (aDecLeft.size() >= aDecRight.size()) {
                    aDecRight.addFirst(Integer.parseInt(input.substring(2, input.length())));
                } else {
                    aDecLeft.addLast(Integer.parseInt(input.substring(2, input.length())));
                }
            } else if (input.charAt(0) == '-') {
                if (aDecRight.size() == 0) aDecRight.addFirst(aDecLeft.pollLast());
                writer.write(aDecRight.pollLast() + "\n");
                if (aDecLeft.size() > aDecRight.size() && aDecLeft.size() > 0) {
                    aDecRight.addFirst(aDecLeft.pollLast());
                }
            }
        }
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        new G().start();
    }

}
