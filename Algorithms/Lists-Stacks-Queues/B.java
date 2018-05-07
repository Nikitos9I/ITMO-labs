package HomeWork.Algoritms.lab2;

import java.io.*;
import java.util.Stack;

/**
 * Created by nikitos on 06.11.17.
 */
public class B {

    public void start() throws IOException {
        BufferedReader buf = new BufferedReader( new FileReader("brackets.in"));
        PrintWriter writer = new PrintWriter("brackets.out");

        Stack<Character> stack = new Stack<>();

        String input = buf.readLine();

        stack.push(input.charAt(0));
        for (int i = 1; i < input.length(); i++) {
            if (!stack.empty() && (stack.peek() == '(' && input.charAt(i) == ')' || stack.peek() == '[' && input.charAt(i) == ']' || stack.peek() == '{' && input.charAt(i) == '}')) {
                stack.pop();
            } else {
                stack.push(input.charAt(i));
            }
        }

        writer.write(stack.empty()? "YES" : "NO");

        writer.close();
    }

    public static void main(String[] args) throws IOException {
        new B().start();
    }

}
