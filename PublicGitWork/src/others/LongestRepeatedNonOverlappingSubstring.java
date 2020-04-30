package com.problems.string.LongestRepeatedSubstring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/*
 * Problem : Longest repeating and non-overlapping substring

Test cases:  
Input: str = “geekeekeeg”
Output: Yes
 

Input: str = “geekeeg”
Output: No
 

Input : str = "geeksforgeeks"
Output : geeks

Input : str = "aab"
Output : a

Input : str = "aabaabaaba"
Output : aaba

Input : str = "aaaaaaaaaaa"
Output : aaaaa

Input : str = "banana"
Output : an 
         or na
         
Input:- largelargerlargest
Output:- large


Input:ABADZEDGBADEZ
Output: "BAD"

Input:
ABBBB

output:
BB

input: = ababababab
longest :abababab
short :abab 
*/

public class LongestRepeatedNonOverlappingSubstring {

	public static void main(String[] args) {

		String str = "aabaabaaba";

		str = longestNonOverlappingSubstr(str);

		System.out.println(str);

	}

	public static String longestNonOverlappingSubstr(String str) {

		char[] c = str.toCharArray();// char by char matching
		int len = str.length();
		List<String> patternList = new ArrayList<String>();

		char[] cleft = c;
		char[] cRight = c;
		int i = 0;
		int tillPos = 0;

		for (; i < len; i++) {
			char movingRight = cRight[i];

			for (int j = 0; j < len; j++) {

				if (i == 0 && j == 0)
					break;

				char leftCompare = cleft[j];

				if (leftCompare == movingRight) {

					tillPos = i;
					int startPos = j;
					char[] left = new char[tillPos];
					char[] cpy = c;

					for (int h = 0; startPos < tillPos; startPos++, h++) {
						left[h] = cpy[startPos];
					}

					String storePattern = new String();

					for (int k = 0; k < left.length && (tillPos < len); k++, tillPos++) {

						char right = cRight[tillPos];

						if (left[k] == right) {
							storePattern += left[k] + "";
						} else
							break;

					}

					patternList.add(storePattern);
					break;
				}

			}

		}

		str = patternList.stream().max((Comparator.comparing(String::length))).get();

		return str;
	}
}
