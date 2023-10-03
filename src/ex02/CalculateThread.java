package ex02;

import java.util.List;

public class CalculateThread extends Thread {
    private final int numberThread;
    private static int countThreads = 0;

    private int sum;
    public CalculateThread() {
        countThreads++;
        numberThread = countThreads;
    }
    @Override
    public void run() {
        calcSum();
    }


    private void calcSum() {
        List<Integer> array = Program.getArray();
        int countElem = array.size() / countThreads;
        if (countElem * countThreads < array.size()) {
            countElem++;
        }
        int begin = countElem * (numberThread - 1);
        int end = countElem * numberThread;
        if (end > array.size()) {
            end = array.size();
        }
        for (int i = begin; i < end; i++) {
            sum += array.get(i);
        }
        System.out.printf("Thread %d: from %d to %d sum is %d\n", numberThread, begin, end - 1, sum);
    }

    public int getSum() {
        return sum;
    }
}
