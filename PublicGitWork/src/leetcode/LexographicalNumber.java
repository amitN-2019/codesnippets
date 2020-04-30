package leetcode;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


//386. Lexicographical Numbers
//Experimented LL logic for getting Lexo orders . Developed my LL for storing multiples of 10 numbers as LL with new values & counters. 
public class LexographicalNumber {

	static List<Integer> lexoData = new ArrayList<Integer>();
	static int num;

	public static List<Integer> lexicalOrder(int n) {

		num = n;
		for (int i = 1; i <= 9; i++) {

			if (i <= num)
				lexoData.add(i);

			MyLinkedList list = new MyLinkedList();
			list = check(i, num, list); // get series for number
			int len = list.getSize();
			if (len >= 1) {
				int j = i * 10;
				list.getFirstNode().setValue(j);
				list.getFirstNode().setNewValue(j);
				int flag = 0;
				boolean lock = false;
				int changeSeries = 0;

				int pp = callBack(list.getFirstNode(), j, flag, lock, changeSeries, list.getFirstNode(),
						list.getLastNode());
				
				list = null; //GC help
			}

		}

		return lexoData;

	}

	private static int callBack(MyLinkedList.Node cur, int j, int flag, boolean lock, int changeSeries,
			final MyLinkedList.Node firstNode, MyLinkedList.Node lastNode) {

		MyLinkedList.Node lstNode = lastNode;
		int rt = 0;

		if (rt == -1)
			return -1;

		if (lock) {

			if (cur.getIncr() <= 9 && cur != firstNode) {
				lock = false;
			}

			if (cur.getIncr() > 9 && cur != firstNode) {
				lock = false;
			}

			if (cur == firstNode & lock) {
				cur = firstNode;

				int l = cur.getIncr();
				int n = cur.getNewValue();

				for (; l < (j + 9); l++) {

					n = n + 1;
					if (n > num || n > (j + 9)) {
						cur.setIncr(l);

						lock = true;
						rt = -1;
						return rt;
					} else {
						cur.setNewValue(n);
						lexoData.add(n);
					}
				}
			}
		}

		if (!lock) {

			if (cur.getNext() == null && cur.getPrev() == null) {

				int n = cur.getNewValue();
				int limit = n + 9;
				lexoData.add(n);

				for (int l = 0; l < limit; l++) {
					n = n + 1;
					if (n > num || n > limit) {
						cur.setIncr(l);
						lock = true;
						rt = -1;
						return rt;
					} else {

						cur.setNewValue(n);
						lexoData.add(n);

					}
				}

			}

			if (cur == firstNode) {

				if (firstNode.getNext().getIncr() == 0 && changeSeries == 0) {

					lexoData.add(cur.getNewValue());
					cur = cur.getNext();
					rt = callBack(cur, j, flag, lock, changeSeries, firstNode, lstNode);// traverse below
					if (rt == -1)
						return -1;
				}

				if (firstNode.getIncr() > 9) {

					lock = true;

					rt = -1;
					return rt;
				}

				if (firstNode.getIncr() <= 9 && firstNode.getNext().getIncr() >= 9) {

					firstNode.setIncr(firstNode.getIncr() + 1);// incr iterations (0-9)

					if (firstNode.getIncr() > 9)
						return -1;

					changeSeries = 1;
					flag = 1;
					lock = false;

					firstNode.setNewValue(firstNode.getNewValue() + changeSeries);
					lexoData.add(firstNode.getNewValue());
					cur = firstNode.getNext();
					for (MyLinkedList.Node n = cur; n != null; n = n.next) {
						n.setIncr(0);

					}

					rt = callBack(cur, j, flag, lock, changeSeries, firstNode, lstNode);
					if (rt == -1)
						return rt;
				}

			} // firstnode
			else {

				if (cur.getIncr() <= 9 && firstNode.getIncr() <= 9) {

					if (!lexoData.contains(cur.getNewValue())) {
						cur.setNewValue(cur.getNewValue());
						lexoData.add(cur.getNewValue());
					} else {
						if (!lexoData.contains(cur.getNewValue() + 1)) {
							cur.setNewValue(cur.getNewValue() + 1);
							lexoData.add(cur.getNewValue());
						}
					}

					cur.setIncr(cur.getIncr() + 1);// incr iterations (0-9)
					cur = cur.getNext();// now go next

					if (cur == null) {
						cur = lstNode;
					} else {
						cur.setIncr(0);
						rt = callBack(cur, j, flag, lock, changeSeries, firstNode, lstNode);
						if (rt == -1)
							return rt;

					}

					if (rt == -1)
						return rt;

				} else {

					cur = cur.getPrev();
					rt = callBack(cur, j, flag, lock, changeSeries, firstNode, lstNode);// traverse back

					if (rt == -1)
						return rt;

				}

				if (cur == lstNode && firstNode.getIncr() <= 9) {

					flag = 0;
					int n = cur.getNewValue();
					int l = cur.getIncr();

					for (; l <= 9; l++) {
						n = n + 1;
						if (n > num) {

							lock = true;
							break;
						} else {
							cur.setNewValue(n);
							cur.setIncr(l);
							lexoData.add(n);

							if (n >= num) {
								lock = true;
								break;
							}

						}
					}

					if (lock) {

						if (cur.getPrev() == firstNode) {
							firstNode.next = null;
							lastNode.prev = null;
							cur = firstNode;
							lstNode = firstNode;
						} else {

							MyLinkedList.Node prev = cur.getPrev();
							prev.next = null;
							cur = prev;
							lstNode = prev;
						}

					} else {

						if (cur.getPrev().getIncr() <= 9) {

							if (cur.getPrev() == firstNode) {

								cur = firstNode;
							} else
								cur = cur.getPrev();
						} else {
							cur = cur.getPrev();

						}
					}

					rt = callBack(cur, j, flag, lock, changeSeries, firstNode, lstNode);// traverse back
					if (rt == -1)
						return rt;

				} // last node , loop
				else {
					if (rt == -1)
						return rt;
				}

			} // else
		} // !LOCK

		return rt;
	}

	private static MyLinkedList check(int orgNumbr, int input, MyLinkedList list) {

		int rt = 10;

		int[] store = {};
		int[] series = new int[input];

		int i = 0;

		for (;; rt = rt * 10) {
			int tmp = orgNumbr;

			if ((tmp * rt) > input) {
				break;
			} else {
				series[i] = rt;
				list.add(orgNumbr * rt);
				i++;
			}
		}

		if (i > 0)// series exist
		{
			store = new int[i];
			System.arraycopy(series, 0, store, 0, store.length);

		}

		return list;
	}

	static class MyLinkedList {
		private int size = 0;

		public int getSize() {
			return size;
		}

		// pointer to the first
		private Node first;

		// pointer to the last
		private Node last;

		// adds any type of object to LL to last node.
		public void add(int o) {

			if (last != null) {
				Node new_one = new Node(last, null, o, 9, 0, o);
				last.next = new_one;
				last = new_one;
				size++;
			} else {
				Node new_one = new Node(null, null, o, 9, 0, o);
				last = first = new_one;
				size++;
			}
		}

		public int listCount() {
			return size;
		}

		public Object getNext() {

			Node e = first;

			if (e == null)
				return null;

			return e.value;
		}

		public Node getFirstNode() {

			Node e = first;

			if (e == null)
				return null;

			return e;
		}

		public Node getLastNode() {

			Node e = last;

			if (e == null)
				return null;

			return e;
		}

		public Object getFirst() {

			Node e = first;

			if (e == null)
				return null;

			return e.value;
		}

		public Object getLast() {

			Node e = last;

			if (e == null)
				return null;

			return e.value;
		}

		public void print() {

			for (Node n = first; n != null; n = n.next) {

				System.out.println("---" + n.value);

			}

		}

		static class Node {

			public Node(Node prev, Node next, int constant, int limit, int incr, int newValue) {
				this.prev = prev;
				this.next = next;
				this.value = constant;
				this.limit = limit;
				this.incr = incr;
				this.newValue = newValue;
			}

			public int getIncr() {
				return incr;
			}

			public void setIncr(int incr) {
				this.incr = incr;
			}

			public int getNewValue() {
				return newValue;
			}

			public void setNewValue(int newValue) {
				this.newValue = newValue;
			}

			public int getValue() {
				return value;
			}

			public void setValue(int value) {
				this.value = value;
			}

			public int getLimit() {
				return limit;
			}

			public Node getPrev() {
				return prev;
			}

			public Node getNext() {
				return next;
			}

			private int incr = 0; // (0-9)
			private int newValue = 0;// new value, intially set this value to old constant value when creating Node
			Node prev = null;
			Node next = null;
			private int value; // constant
			private int limit = 9; // 9 of (0-9)
		}
	}// LL class ends

	public static void main(String[] args) {
		int num = 120;

		lexoData = lexicalOrder(num);

		FileWriter fw = null;
		try {
			fw = new FileWriter("./output.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try (BufferedWriter bw = new BufferedWriter(fw)) {

			for (int i = 0; i < lexoData.size(); i++) {
				String s = lexoData.get(i).toString();
				bw.write(s);
				String s1 = "\n";
				bw.write(s1);
				bw.flush();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}