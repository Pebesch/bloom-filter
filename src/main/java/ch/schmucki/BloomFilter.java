package ch.schmucki;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;
import java.util.Arrays;

public class BloomFilter {
    // https://en.wikipedia.org/wiki/Bloom_filter
    // An empty Bloom filter is a bit array of m bits
    private boolean[] bit_array_m;
    // There must also be k different hash functions defined
    private HashFunction[] hash_functions_k;

    public BloomFilter(int words_n, double p_error_rate) {
        int filterSize_m = calculateOptimalFilterSize(words_n, p_error_rate) ;
        bit_array_m = new boolean[filterSize_m];
        hash_functions_k = new HashFunction[calculateOptimalNumberOfHashfunctions(filterSize_m, words_n)];
        fillHashFunctions();
    }

    public void add(String s) {
        for(int i = 0; i < hash_functions_k.length; i++) {
            int hash = hash_functions_k[i].hashString(s, Charset.defaultCharset()).asInt();
            int pos = hash % bit_array_m.length;
            bit_array_m[Math.abs(pos)] = true;
        }
    }

    public boolean contains(String s) {
        // Default no collision
        boolean contains = false;
        for(int i = 0; i < hash_functions_k.length; i++) {
            int hash = hash_functions_k[i].hashString(s, Charset.defaultCharset()).asInt();
            int pos = hash % bit_array_m.length;
            // If one of the hashes collides we have a match
            if(bit_array_m[Math.abs(pos)]) contains = true;
        }
        return contains;
    }

    // https://en.wikipedia.org/wiki/Bloom_filter # Optimal number of hash functions
    private int calculateOptimalFilterSize(int n, double p) {
        int m;
        m = (int) ( - (n * Math.log(p)) / (Math.pow(Math.log(2), 2)));
        return m;
    }

    private int calculateOptimalNumberOfHashfunctions(int m, int n) {
        int k;
        k = (int) (m / n * Math.log(2));
        return k;
    }

    // Als Hash-Funktionen soll murmur3 128 verwendet werden mit jeweils einem anderen Seed.
    private void fillHashFunctions() {
        for(int i = 0; i < hash_functions_k.length; i++) {
            hash_functions_k[i] = Hashing.murmur3_128(i);
        }
    }

    @Override
    public String toString() {
        return "BloomFilter{" +
                "bit_array_m=" + bit_array_m.length +
                ", hash_functions_k=" + hash_functions_k.length +
                '}';
    }
}
