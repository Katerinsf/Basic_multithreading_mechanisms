package ex03;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class DownloadThread extends Thread {
    private final int numberThread;
    private List<String> listUrls;
    private static int countThreads = 0;
    public DownloadThread (List<String> listUrls) {
        this.listUrls = listUrls;
        countThreads++;
        numberThread = countThreads;
    }


    @Override
    public void run() {
        String[] strArray;
        String url, strFile;
        int numberUrl;
        while (!listUrls.isEmpty()) {
            synchronized (this) {
                strFile = listUrls.remove(0);
            }
            if (strFile != null) {
                strArray = strFile.split(" ");
                numberUrl = Integer.parseInt(strArray[0]);
                url = strArray[1];
                System.out.println("Thread-" + numberThread + " start download file number " + numberUrl);
                downloadFile(url, numberUrl);
                System.out.println("Thread-" + numberThread + " finish download file number " + numberUrl);
            }
        }

    }

    private void downloadFile(String urlPath, int numberUrl) {
        try {
            URL url = new URL(urlPath);
            String fileName = createFileName(url, numberUrl);
            try (InputStream input = url.openStream(); FileOutputStream resultFile = new FileOutputStream(fileName)) {
                byte[] data = new byte[1024];
                int count;
                while ((count = input.read(data, 0, 1024)) != -1) {
                    resultFile.write(data, 0, count);
                }
            }
        } catch (IOException error) {
//            error.printStackTrace();
            System.err.println("Invalid URL: file " + numberUrl + ", thread-" + numberThread);
        }
    }

    private String createFileName(URL url, int numberUrl) {
        String[] fileName = url.toString().split("/");
        File directory = new File("./ex03/Files");
        if (!directory.exists()) {
            directory.mkdir();
        }
        return directory.getPath() + "/" + numberUrl + "_" + fileName[fileName.length - 1];
    }
}
