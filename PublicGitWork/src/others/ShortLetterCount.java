package com.problems;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


/*
 * Write a function that takes an array of words and returns their shortened 
   letter-count version, i.e. ["google", "word"] => ["g4e", "w2d"]. If the shortened version
  is not unique, expand out 1 letter at a time
* */
public class ShortLetterCount {

	public static void main(String[] args) {

		String[] s = { "google", "word", "google", "google" };

		String[] str = shortLetterCount(s);

		Arrays.stream(str).forEach(a -> System.out.println(a));

	}

	public static String[] shortLetterCount(String s[]) {

		HashMap<String, int[]> mp = new HashMap<String, int[]>();

		for (int i = 0; i < s.length; i++) {

			int left = 0;
			int len = s[i].length() - 1;

			if (mp.containsKey(s[i])) {

				int[] dim = mp.get(s[i]);

				int popcount = dim[4] + 1;
				int midrange = dim[1] + 1;
				int[] tmp = { dim[0], midrange, len - popcount, len, popcount };

				if (tmp[2] < 0)// popcount is > len then it goes to (-)
					continue;

				mp.remove(s[i]);
				mp.put(s[i], tmp);// 0th and 5th element and 1st char
			} else {
				int popCount = 1;// decr count for mid letter
				int midrange = 1;
				int[] dim = { left, midrange, len - popCount, len, popCount };
				mp.put(s[i], dim);// 0th and 5th element and 1st char
			}

		}

		String[] str = new String[mp.size()];
		int c = 0;

		for (Map.Entry word : mp.entrySet()) {
			String key = (String) word.getKey();

			int[] dim = (int[]) word.getValue();

			if (dim[2] > 0)
				str[c] = key.substring(dim[0], dim[1]).concat(String.valueOf(dim[2]))
						.concat(String.valueOf(key.charAt(dim[3])));
			else
				str[c] = key.substring(dim[0], dim[1]).concat(String.valueOf(key.charAt(dim[3])));

			c++;
		}

		return str;
	}

}
