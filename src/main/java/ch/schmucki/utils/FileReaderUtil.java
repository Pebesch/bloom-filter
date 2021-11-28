package ch.schmucki.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileReaderUtil {

    // Adapted from https://mkyong.com/java/java-read-a-file-from-resources-folder/
    public static void readLineToList(List<String> list, String fileName) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(fileName);
        try {
            if(is == null) throw  new IOException("Is not loaded");
            try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
                     BufferedReader reader = new BufferedReader(streamReader)) {

                String line;
                while ((line = reader.readLine()) != null) {
                    list.add(line);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
