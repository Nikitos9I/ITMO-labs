package HomeWork.Algoritms.lab2;

import java.io.*;
import java.util.Stack;

/**
 * Created by nikitos on 08.11.17.
 */
public class I {

    public void start() throws IOException {
        //BufferedReader buf = new BufferedReader( new FileReader("arrange.in.txt"));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");
        BufferedReader buf = new BufferedReader( new FileReader("hemoglobin.in"));
        PrintWriter writer = new PrintWriter("hemoglobin.out");

        int n = Integer.parseInt(buf.readLine());
        Stack<Integer> stack = new Stack<>();
        Stack<Long> sum = new Stack<>();
        stack.push(0);
        sum.push((long)0);

        int add = 0;
        for (int i = 0; i < n; i++) {
            String input = buf.readLine();
            if (input.charAt(0) == '+') {
                stack.push(Integer.parseInt(input.substring(1,input.length())));
                if (sum.empty()) sum.push((long)stack.peek()); else
                sum.push(stack.peek() + sum.peek());
                add++;
            } else if (input.charAt(0) == '-') {
                writer.write(stack.pop() + "\n");
                sum.pop();
                add--;
            } else if (input.charAt(0) == '?') {
                int count = Integer.parseInt(input.substring(1,input.length()));
                writer.println((sum.peek() - sum.get(add - count)));
            }
        }

        writer.close();
    }

    public static void main(String[] args) throws IOException {
        new I().start();
    }

}
