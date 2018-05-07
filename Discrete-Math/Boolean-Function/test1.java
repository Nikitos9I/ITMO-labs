package HomeWork.Discret_math.lab1.lab2;

import java.io.*;
import java.util.*;

public class test1 {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        int n;
        Map frequenceMap = new HashMap(); // символ-частота
        Map freeMap = new HashMap(); // символ-свобод./занят
        Map parentMap = new HashMap(); // символ-код
        Scanner input = new Scanner(new File("arrange.in.txt"));
        n = input.nextInt();
        for (int i = 0; i < n; i++) {
            frequenceMap.put("c" + (i + 1), input.nextInt());
            freeMap.put("c" + (i + 1), true);
            parentMap.put("c" + (i + 1), "");
        }
        System.out.println(parentMap);
        input.close();
        test2 t = new test2(frequenceMap, freeMap, parentMap);
    }
}
