package com.problems;

import java.util.Arrays;

/*
 * 
 * Problem : 560. Subarray Sum Equals K

    Other inputs/outputs: 
     
 	Input : arr[] = {10, 2, -2, -20, 10}, 
	        k = -10
	Output : 3
	Subarrays: arr[0...3], arr[1...4], arr[3..4]
	have sum exactly equal to -10.
	
	Input : arr[] = {9, 4, 20, 3, 10, 5},
	            k = 33
	Output : 2
	Subarrays : arr[0...2], arr[2...4] have sum
	exactly equal to 33.
	
 * */
public class SumContinuousSubArray {

	public static void main(String[] args) {

		int[] nums = { 1, 2, 3 };
		int sum = 3;

		int output = subarraySum(nums, sum);

		System.out.println(output);

	}

	public static int subarraySum(int[] nums, int k) {

		int len = nums.length;
		int count = 0;

		if (len < 1 || len > 20000)
			return 0;

		for (int i = 0; i < len; i++) {

			int add = nums[i];

			if ((i == len - 1 && add == k) || (i < len - 1 && add == k))
				count++;

			for (int j = i + 1; j < len; j++) {
				add = add + nums[j];

				if (add == k)
					count++;

			}

		}

		return count;
	}
}
