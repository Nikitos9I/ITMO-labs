//package HomeWork.Algoritms.lab2;

import java.io.*;
import java.util.Stack;

/**
 * Created by nikitos on 07.11.17.
 */
public class D {

    public void start() throws IOException {
        //BufferedReader bud = new BufferedReader( new FileReader("arrange.in.txt"));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");
        BufferedReader bud =  new BufferedReader( new FileReader("postfix.in"));
        PrintWriter writer = new PrintWriter("postfix.out");

        Stack<Integer> stackInt = new Stack<>();
        Stack<Character> stackSign = new Stack<>();

        String input = bud.readLine();
        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i))) {
                stackInt.push(Integer.parseInt(input.charAt(i) + ""));
            } else if (input.charAt(i) != ' ') {
                int a = stackInt.pop();
                int b = stackInt.pop();
                if (input.charAt(i) == '*') {
                    int c = a * b;
                    stackInt.push(c);
                } else if (input.charAt(i) == '-') {
                    int c = b - a;
                    stackInt.push(c);
                } else if (input.charAt(i) == '+') {
                    int c = a + b;
                    stackInt.push(c);
                }
            }
        }

        writer.write(stackInt.pop() + "");
        writer.close();

    }

    public static void main(String[] args) throws IOException {
        new D().start();
    }

}
