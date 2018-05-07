package HomeWork.Algoritms.lab2;

import java.io.*;
import java.util.Stack;

/**
 * Created by nikitos on 06.11.17.
 */
public class A {

    public StreamTokenizer t;

    public String nextString() throws IOException {
        t.nextToken();
        return t.sval;
    }

    public void start() throws IOException {
        t = new StreamTokenizer( new BufferedReader( new FileReader("decode.in")));
        PrintWriter writer = new PrintWriter("decode.out");
        //t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");

        String input = nextString();

        Stack<Character> stack = new Stack<>();
        stack.push(input.charAt(0));
        for (int i = 1; i < input.length(); i++) {
            if (!stack.empty() && stack.peek() == input.charAt(i)) {
                stack.pop();
            } else {
                stack.push(input.charAt(i));
            }
        }

        Stack<Character> stack2 = new Stack<>();
        while(!stack.empty()) {
            stack2.push(stack.pop());
        }
        while(!stack2.empty()) {
            writer.write(stack2.pop());
        }
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        //long startTime = System.currentTimeMillis();
        new A().start();
        //long timeSpent = System.currentTimeMillis() - startTime;
        //System.out.println("программа выполнялась " + (timeSpent/1000) + " секунд");
    }

}
