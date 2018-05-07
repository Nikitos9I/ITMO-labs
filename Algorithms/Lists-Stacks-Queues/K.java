package HomeWork.Algoritms.lab2;

import java.io.*;
import java.util.ArrayDeque;

/**
 * Created by nikitos on 08.11.17.
 */
public class K {

    public void start() throws IOException {
        //BufferedReader buf = new BufferedReader( new FileReader("arrange.in.txt"));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");
        BufferedReader buf = new BufferedReader( new FileReader("kenobi.in"));
        PrintWriter writer = new PrintWriter("kenobi.out");

        int n = Integer.parseInt(buf.readLine());
        ArrayDeque<Integer> aDecLeft = new ArrayDeque<>();
        ArrayDeque<Integer> aDecRight = new ArrayDeque<>();
        boolean f = false;


        for (int i = 0; i < n; i++) {
            String input = buf.readLine();
            if (input.charAt(0) == 'a') {
                if (!f) {
                    aDecRight.addLast(Integer.parseInt(input.substring(4, input.length())));
                    if (aDecLeft.size() + 1 < aDecRight.size() && aDecRight.size() > 0) {
                        aDecLeft.addLast(aDecRight.pollFirst());
                    }
                } else {
                    aDecLeft.addLast(Integer.parseInt(input.substring(4, input.length())));
                    if (aDecRight.size() + 1 < aDecLeft.size() && aDecLeft.size() > 0) {
                        aDecRight.addLast(aDecLeft.pollFirst());
                    }
                }
            } else if (input.charAt(0) == 't') {
                if (!f) {
                    if (aDecRight.size() > 0) {
                        aDecRight.pollLast();
                        if (aDecLeft.size() > aDecRight.size() && aDecLeft.size() > 0)
                            aDecRight.addFirst(aDecLeft.pollLast());
                    } else {
                        aDecLeft.pollLast();
                    }
                } else {
                    if (aDecLeft.size() > 0) {
                        aDecLeft.pollLast();
                        if (aDecRight.size() > aDecLeft.size() && aDecRight.size() > 0)
                            aDecLeft.addFirst(aDecRight.pollLast());
                    } else {
                        aDecRight.pollLast();
                    }
                }
            } else if (input.charAt(0) == 'm') {
                if (!f) {
                    f = true;
                    if (aDecLeft.size() < aDecRight.size() && aDecRight.size() > 0) {
                        aDecLeft.addFirst(aDecRight.pollLast());
                    }
                } else {
                    f = false;
                    if (aDecRight.size() < aDecLeft.size() && aDecLeft.size() > 0) {
                        aDecRight.addFirst(aDecLeft.pollLast());
                    }
                }
            }
        }
        writer.write((aDecLeft.size() + aDecRight.size()) + "\n");
        if (!f) {
            while (aDecLeft.size() > 0) {
                writer.write( aDecLeft.pollFirst() + " ");
            }
            while (aDecRight.size() > 0) {
                writer.write( aDecRight.pollFirst() + " ");
            }
        } else {
            while (aDecRight.size() > 0) {
                writer.write( aDecRight.pollFirst() + " ");
            }
            while (aDecLeft.size() > 0) {
                writer.write( aDecLeft.pollFirst() + " ");
            }
        }

        writer.close();
    }

    public static void main(String[] args) throws IOException {
        new K().start();
    }

}
