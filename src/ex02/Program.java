package ex02;

import java.util.ArrayList;
import java.util.List;

public class Program {
    private static int sizeArray;
    private static int countThreads;
    private static final List<Integer> array = new ArrayList<>();
    private static final List<CalculateThread> threadArray = new ArrayList<>();

    public static void main(String[] args) {
        checkArgs(args);

        createArray();
        printSum();

        createArrayThread();
        for (Thread thread : threadArray) {
            thread.start();
        }

        for (Thread thread : threadArray) {
            try {
                thread.join();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
        printThreadsSum();
    }


    private static void checkArgs(String[] args) {
        try {
            if (args.length != 2 || !args[0].startsWith("--arraySize=") || !args[1].startsWith("--threadsCount=")) {
                throw new RuntimeException("The argument must begin with \"--arraySize=*size of array* --threadsCount=*count of threads*\"");
            }
            sizeArray = Integer.parseInt(args[0].replace("--arraySize=", ""));
            countThreads = Integer.parseInt(args[1].replace("--threadsCount=", ""));
            if ((sizeArray <= 0 || countThreads <= 0) || (sizeArray > 2e6 || countThreads > sizeArray)) {
                throw new RuntimeException("Enter [0 < size <= 2000000] and [0 < count <= size]");
            }
        } catch (RuntimeException exception) {
            System.err.println(exception.getMessage());
            System.exit(-1);
        }
    }

    private static void createArray() {
        for (int i = 0; i < sizeArray; i++) {
            array.add(((int)(Math.random() * 2001) - 1000));
        }
    }

    private static void createArrayThread() {
        for (int i = 0; i < countThreads; i++) {
            CalculateThread thread = new CalculateThread();
            threadArray.add(thread);
        }
    }

    public static List<Integer> getArray() {
        return array;
    }

    private static void printSum() {
        int sum = 0;
        for (int i = 0; i < sizeArray; i++) {
            sum += array.get(i);
        }
        System.out.println("Sum: " + sum);
    }

    private static void printThreadsSum() {
        int sum = 0;
        for (CalculateThread thread : threadArray) {
            sum += thread.getSum();
        }
        System.out.println("Sum by threads: " + sum);
    }

}
