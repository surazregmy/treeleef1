package com.example.treeleef1.question;

import java.util.*;

public class QualificationTaskFromOneTwoThree {

    /*Q1. Replace each character in a given string with the next character in the
        alphabet. Example 𝐻𝑒𝑙𝑙𝑜 −> 𝐼𝑓𝑚𝑚𝑝
        The method should accept the following parameters
        1. inputString input string
        The method should return a result after doing an alphabetic shift.
    */
    public static String replaceStringWithNextChar(String string) {
        if (string == null) {
            return "Provided String is null! Can't proceed!";
        }
        StringBuilder test = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            int replacingCharAscII = 0;
            if (Character.isLowerCase(string.charAt(i)))
                replacingCharAscII = ((string.charAt(i) - 'a' + 1) % 26) + 'a';
            else
                replacingCharAscII = ((string.charAt(i) - 'A' + 1) % 26) + 'A';
            test.append((char) replacingCharAscII);
        }
        return test.toString();
    }


    /*
    Q2. Given an array 𝑎𝑟𝑟, find 𝑘 such that the sum of
    𝑎𝑏𝑠(𝑎𝑟𝑟[0], 𝑘) + 𝑎𝑏𝑠(𝑎𝑟𝑟[1], 𝑘) + … + 𝑎𝑏𝑠(𝑎𝑟𝑟[𝑛], 𝑘) is the minimum where
    𝑎𝑏𝑠(𝑥, 𝑦) returns the absolute difference of 𝑥and 𝑦.
    The method should accept the following parameters.
    1. a list of numbers
    The method should return k.
    * */
    public static Number getElementWithLeastSum(List<Number> numbersList) {
        //Accepts list of all numbers: int,flot, double && Sorts the Number List array in ascending Order
        List<Number> numbers = new ArrayList<>(numbersList);
        for (int i = 0; i < numbers.size() - 1; i++) {
            int minimumIndex = i;
            for (int j = i + 1; j < numbers.size(); j++) {
                if (numbers.get(j).doubleValue() < numbers.get(minimumIndex).doubleValue())
                    minimumIndex = j;
            }
            Number temp = numbers.get(minimumIndex);
            numbers.remove(minimumIndex);
            numbers.add(minimumIndex, numbers.get(i));
            numbers.remove(i);
            numbers.add(i, temp);
        }

        //Return the median element as sum of above condition is minimum when the element is median
        int medianIndex = numbers.size() / 2;
        return numbersList.get(medianIndex);
    }

    /*
    Q3. Given a number k and array, find the number of pairs in the array whose
    difference is equal to k.
    1. Throws timeout if a nested for loop is used.
    2. The method should accept the following parameters:
    I. k number
    II. an array of integers
    3. And return 𝑛 where n is the number of pairs in a whose difference is equal to k.
    */

    public static int countPairsWithDifferenceK(int k, int[] arr) {
        int count = 0;

        long start = System.currentTimeMillis();
        long end = start + 30000; // Timeout in thirty seconds
        for (int i = 0; i < arr.length; i++) {
            if (System.currentTimeMillis() > end) {
                System.out.println("TimeOut! Perhaps try with smaller sized Array...");
                count = 0;
                break;
            }
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] - arr[j] == k || arr[j] - arr[i] == k) {
                    count++;
                }
            }

        }
        return count;
    }
}
