package ch.schmucki;

import ch.schmucki.utils.FileReaderUtil;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        double error_p = 0.01;

        // German words https://gist.githubusercontent.com/MarvinJWendt/2f4f4154b8ae218600eb091a5706b5f4/raw/36b70dd6be330aa61cd4d4cdfda6234dcb0b8784/wordlist-german.txt
        // Top 1000 english words https://gist.github.com/deekayen/4148741
        List<String> trainingWords = new ArrayList<>();
        FileReaderUtil.readLineToList(trainingWords, "words.txt");

        List<String> validationWords = new ArrayList<>();
        FileReaderUtil.readLineToList(validationWords, "common_words_ger.txt");

        BloomFilter filter = new BloomFilter(trainingWords.size(), error_p);
        for(String word : trainingWords) {
            filter.add(word);
        }

        int matches = 0;
        for(String word : validationWords) {
            if(filter.contains(word)) matches++;
        }

        double percentageMatch = (double) matches / validationWords.size() * 100;
        System.out.println(String.format("Number of words added to the filter: %s", trainingWords.size()));
        System.out.println(String.format("Number of words validated by the filter: %s", validationWords.size()));
        System.out.println(String.format("Allowed error margin: %s", error_p));
        System.out.println(String.format("Matches in percent: %s", percentageMatch));
        System.out.println(filter.toString());
    }
}
