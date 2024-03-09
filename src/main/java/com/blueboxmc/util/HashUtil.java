package com.blueboxmc.util;

public class HashUtil {

    public static int getHash(String str) {
        int sum = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            sum += c; // Adds ASCII value of each character
        }
        return sum;
    }
}
