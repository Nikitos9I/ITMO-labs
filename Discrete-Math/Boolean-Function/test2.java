package HomeWork.Discret_math.lab1.lab2;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
public final class test2 {

    private String firstMin, secondMin;
    private int firstMinValue, secondMinValue, trueCount, codeLength = 0;
    private Map<String,Integer> frequence;
    private Map<String,Boolean> free;
    private Map<String,String> parent;

    test2(Map fq, Map f, Map p) throws FileNotFoundException, IOException {
        frequence = fq;
        free = f;
        parent = p;
        System.out.println(parent);
        trueCount = frequence.size();
        buildTree(fq, f);
        showTree();
    }

    public void showTree() throws FileNotFoundException, IOException {
        for (String key : parent.keySet()) {
            codeLength += parent.get(key).length() * frequence.get(key);
        }
        FileOutputStream output = new FileOutputStream("arrange.out.txt");
        output.write((codeLength+"").getBytes());
        output.close();
    }

    public void buildTree(Map fq, Map f) {
        while (trueCount != 1) {
            min(fq, f);
            fq.put(firstMin + secondMin, firstMinValue + secondMinValue);
            f.put(firstMin + secondMin, true);
            f.put(firstMin, false);
            f.put(secondMin, false);
            trueCount--;
            for (String key : parent.keySet()) {
                if (firstMin.contains(key)) {
                    parent.put(key, "0" + parent.get(key));
                } else if ((secondMin != null) && (secondMin.contains(key))) {
                    parent.put(key, "1" + parent.get(key));
                }
            }
        }
    }
    private void min(Map<String,Integer> fq, Map<String,Boolean> f) {
        firstMin = null;
        secondMin = null;
        int min = 1000000001;
        for (String key : fq.keySet()) {
            if (fq.get(key) < min && f.get(key)) {
                min = fq.get(key);
                firstMin = key;
                firstMinValue = min;
            }
        }
        min = 1000000001;
        for (String key : fq.keySet()) {
            if (fq.get(key) < min && !key.equals(firstMin) && f.get(key)) {
                min = fq.get(key);
                secondMin = key;
                secondMinValue = min;
            }
        }
    }
}

