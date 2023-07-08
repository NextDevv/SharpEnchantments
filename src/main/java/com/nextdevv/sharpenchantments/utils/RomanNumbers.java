package com.nextdevv.sharpenchantments.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RomanNumbers {
    public static int value(Character r) {
        if (r == 'I') return 1;
        if (r == 'V') return 5;
        if (r == 'X') return 10;
        if (r == 'L') return 50;
        if (r == 'C') return 100;
        if (r == 'D') return 500;
        if (r == 'M') return 1000;
        else
            return  -1;
    }

    public static int convertRomanToInteger(String r) {
        int total = 0;

        for(int i = 0; i < r.length(); i++) {
            int s1 = value(r.charAt(i));
            if(i + 1 < r.length()) {
                int s2 = value(r.charAt(i + 1));
                if(s1 >= s2) total += s1;
                else total -= s1;
            }else total += s1;
        }
        return total;
    }

    public static String intToRoman(int j) {
        int num = j;
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanLetters = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder roman = new StringBuilder();
        for(int i = 0; i < values.length; i++) {
            while(num >= values[i]) {
                num -= values[i];
                roman.append(romanLetters[i]);
            }
        }
        return roman.toString();
    }
}
