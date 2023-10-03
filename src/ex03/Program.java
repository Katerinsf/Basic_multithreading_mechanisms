package ex03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Program {
    private static final String filePath = "./ex03/files_urls.txt";
    private static final List<String> listUrls = new ArrayList<>();
    private static int countThreads;
    private static final List<Thread> threadArray = new ArrayList<>();


    public static void main(String[] args) {
        checkArgs(args);
        getUrls();
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
    }


    private static void checkArgs(String[] args) {
        try {
            if (args.length != 1 || !args[0].startsWith("--threadsCount=")) {
                throw new RuntimeException("The argument must begin with \"--threadsCount=*count of threads*\"");
            }
            countThreads = Integer.parseInt(args[0].replace("--threadsCount=", ""));
            if (countThreads <= 0) {
                throw new RuntimeException("Enter countThreads > 0");
            }
        } catch (RuntimeException exception) {
            System.err.println(exception.getMessage());
            System.exit(-1);
        }
    }

    private static void getUrls() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                listUrls.add(line);
            }
        }  catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private static void createArrayThread() {
        for (int i = 0; i < countThreads; i++) {
            DownloadThread thread = new DownloadThread(listUrls);
            threadArray.add(thread);
        }
    }
}
