package ch.schmucki;

import ch.schmucki.utils.FileReaderUtil;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        double error_p = 0.05;

        // Top 1000 english words https://gist.github.com/deekayen/4148741
        List<String> trainingWords = new ArrayList<>();
        FileReaderUtil.readLineToList(trainingWords, "words.txt");

        List<String> validationWords = new ArrayList<>();
        FileReaderUtil.readLineToList(validationWords, "common_words.txt");

        BloomFilter filter = new BloomFilter(trainingWords.size(), error_p);
        for(String word : trainingWords) {
            filter.add(word);
        }

        int matches = 0;
        for(String word : validationWords) {
            if(filter.contains(word)) matches++;
        }

        System.out.println(String.format("Number of words added to the filter: %s", trainingWords.size()));
        System.out.println(String.format("Number of words validated by the filter: %s", validationWords.size()));
        System.out.println(String.format("Allowed error margin: %s", error_p));
        System.out.println(String.format("Matches in percent: %s", (matches / validationWords.size() * 100)));
    }
}
