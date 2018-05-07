package HomeWork.Discret_math.lab1.lab3;

import java.io.*;

/**
 * Created by nikitos on 25.11.17.
 */
public class task29 {

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
        BufferedReader buf =  new BufferedReader( new FileReader("nextpartition.in"));
        PrintWriter writer = new PrintWriter("nextpartition.out");
        //t = new StreamTokenizer( new BufferedReader( new FileReader("arrange.in.txt")));
        //PrintWriter writer = new PrintWriter("arrange.out.txt");

        String input = buf.readLine();

        String lastNumber = "";
        StringBuilder output = new StringBuilder();
        boolean exit = false;
        int count = 1;

        int[] number = new int[2];
        for (int i = input.length() - 1; i >= 0; --i) {
            if (i < input.length() - 1 && input.charAt(i + 1) == '=') {
                exit = true;
                break;
            }
            if (Character.isDigit(input.charAt(i))) {
                lastNumber = new StringBuilder(input.charAt(i) + "").append(lastNumber).toString();
            } else {
                number[count] = Integer.parseInt(lastNumber);
                count--;
                lastNumber = "";
            }
            if (count < 0) {
                output.append(input.substring(0,i + 1));
                break;
            }
        }

        if (exit) {
            writer.write("No solution");
        } else {
            writer.write(output.toString());
            number[0]++;
            number[1]--;
            if (number[0] > number[1]) {
                writer.write((number[0] + number[1]) + "");
            } else if (number[0] * 2 <= number[1]) {
                while(number[0]*2 <= number[1]) {
                    writer.write(number[0] + "+");
                    number[1] -= number[0];
                }
                writer.write(number[0] + "+" + number[1]);
            } else if (number[0] <= number[1]) {
                writer.write(number[0] + "+" + number[1]);
            }
        }

        writer.close();

    }

    public static void main(String[] args) throws IOException {
        new task29().start();
    }

}
