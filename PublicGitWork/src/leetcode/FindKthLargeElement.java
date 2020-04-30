package com.problems;

import java.util.Arrays;

/*
 Problem : 215. Kth Largest Element in an Array
 */
public class FindKthLargeElement {

	public static void main(String[] args) {

		int k = 2;
		int[] input = { 0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15 };

		int ouput = findKthLargest(input, k);

		System.out.println(output);

	}

	public static int findKthLargest(int[] nums, int k) {

		for (int i = 0; i < nums.length; i++) {

			for (int j = i + 1; j < nums.length; j++) {
				if (nums[i] > nums[j]) {

					int tmp = nums[j];
					nums[j] = nums[i];
					nums[i] = tmp;
				}

			}
		}

		int rt = nums.length - k;

		return nums[rt];

	}

}
