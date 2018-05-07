package HomeWork.Algoritms.lab2;

import java.io.*;
import java.util.Stack;

/**
 * Created by nikitos on 07.11.17.
 */
public class C {

    public StreamTokenizer t;

    public int nextInt() throws IOException {
        t.nextToken();
        return (int) t.nval;
    }

    public void start() throws IOException {
        //t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");
        t = new StreamTokenizer( new BufferedReader( new FileReader("stack-min.in")));
        PrintWriter writer = new PrintWriter("stack-min.out");

        int n = nextInt();
        Stack<Integer> stack = new Stack<>();
        Stack<Integer> min = new Stack<>();

        for (int i = 0; i < n; i++) {
            int type = nextInt();
            if (type == 1) {
                int input = nextInt();
                stack.push(input);
                if (min.empty()) min.push(input); else {
                    if (input < min.peek()) min.push(input);
                    else min.push(min.peek());
                }
            }
            if (type == 2) {
                stack.pop();
                min.pop();
            }
            if (type == 3) {
                writer.write(min.peek() + "\n");
            }
        }

        writer.close();

    }

    public static void main(String[] args) throws IOException {
        new C().start();
    }

}
