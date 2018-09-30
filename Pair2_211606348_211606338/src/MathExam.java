package com.it666.com;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

public class MathExam {
	/* ���������ܣ�
	 * 1.check()�����������ĸ���bug
	 * 2.if�жϵ�ǰ������꼶�������꼶���벻ͬ����Ŀ������
	 * 3.outPutFile()��������Ŀ�ʹ������out.txt
	 * */
	public static void main(String[] args) throws Exception {
		// �����������ĸ���bug
		if (!check(args))return;
		//�ж��꼶
		else if (args[0].equals("-grade") && args[1].equals("3") || args[2].equals("-grade") && args[3].equals("3")) {
			StringBuffer strbuf2 = init3(args);
			outPutFile(strbuf2);
		} else {
			//��4�������ĳ�2��������������1��2�꼶�ĳ���
			String[] strArgs = new String[2];
			strArgs[0] = args[args[0].equals("-n") ? 1 : 3];
			strArgs[1] = args[args[0].equals("-grade") ? 1 : 3];
			// ����һ���ɱ��ַ�������ƴ����Ŀ�ʹ�
			StringBuffer stringBuffer = new StringBuffer();
			// ��ʼ����ȡ��Ŀ�ʹ�
			init1_2(stringBuffer, strArgs);
			outPutFile(stringBuffer);
		}
	}

	public static StringBuffer init3(String[] args) throws Exception {
		int m = 1;
		int l = 1;
		String str = null;
		// ����2���ַ�������ƴ����Ŀ�ʹ�
		StringBuffer strbuf = new StringBuffer();
		StringBuffer strbuf1 = new StringBuffer();
		StringBuffer strbuf2 = new StringBuffer();
		// random�������������Ŀ�еĲ�����
		int random = 0;
		// int flag=5;
		// �������������������
		String[] strop = { "+", "-", "x", "��" };
		// ��������ɵ�2-4�����������ostr����
		String[] ostr = new String[4];
		int w = Integer.valueOf(args[args[0].equals("-n") ? 1 : 3]);
		for (int i = 0; i < w; i++) {
			// ������ɵ�2-3�������
			int operator = (int) (Math.random() * 3) + 2;
			int k = 0;
			// �������������ţ�������2�ֲ�ͬ������ţ�
			for (int j = 0; j < operator; j++) {
				k = (int) (Math.random() * 4);
				ostr[j] = strop[k];
				if (operator == 2 && j == 1) {
					// ������ֻ��2��������������2���������һ����ͬ
					while (ostr[0] == ostr[1]) {
						k = (int) (Math.random() * 4);
						ostr[0] = strop[k];
					}
				}
			}
			int flag1 = 1;
			int flag = 1;
			for (int j = 0; j < operator + 1; j++) {
				if (operator == 4)
					// ����������+-���Զ��������������
					if (j < operator && (ostr[j].equals("+") || ostr[j].equals("-")) && flag == 1) {
						strbuf.append("( ");
						flag = 0;// �ж��Ƿ����������0�����ѽ�����
						flag1 = 0;// �ж��Ƿ���Լ���������
					}
				random = (int) (Math.random() * 101);
				if (j == operator) {
					// ���һ���������ĩβ���ӿո�
					if (flag == 0 && flag1 == 1) {
						strbuf.append(random + " )");
						flag1 = 0;
						flag = 100;
					} else {
						strbuf.append(random);
						if (flag == 0) {
							// �����һ�����������������
							flag1 = 1;
						}
					}
 				} else {
					if (flag == 0 && flag1 == 1) {
						strbuf.append(random + " )" + " ");
						flag1 = 0;
						flag = 100;
					} else {
						strbuf.append(random + " ");
						if (flag == 0) {
							// ����һ��ѭ�����п��Լ���������
							flag1 = 1;
						}
					}
				}
				if (j < operator)
					strbuf.append(ostr[j] + " ");
			}
			l = 1;
			// ���³�ʼ������
			ostr = new String[4];
			//
			MathExam mathExam = new MathExam();
			mathExam.Init();
			str = strbuf.toString();

			char[] charArray = str.toCharArray();
			double shuntYardAlgo = mathExam.ShuntYardAlgo(charArray);
			String vf = String.valueOf(shuntYardAlgo);
			boolean matches = vf.matches("\\d+.[0]?");
			if (matches) {

				// strbuf2����ƴ����Ŀ
				strbuf2.append("(" + m + ")" + " " + strbuf + "\r\n");
				// strbuf1����ƴ�Ӵ�
				strbuf1.append("(" + m + ")" + " " + strbuf + " " + "=" + " " + (int) shuntYardAlgo + "\r\n");
				m++;
				l = 0;
			}
			strbuf = new StringBuffer();
			if (l != 0)
				i--;
		}
		strbuf2.append("\r\n");
		strbuf2.append(strbuf1);
		return strbuf2;
	}
	/*
	 * init����������Ŀ�ʹ�
	 */
	public static void init1_2(StringBuffer strbuf, String[] s) {

		int result = 0; // ��ʾ��Ŀ��
		int remainder = 0; // ��ʾ����
		String operator = null; // ��ʾ��������������
		String[] mark_code = { "+", "-", "*", "/" }; // Сѧ1�꼶��2�꼶���е�����
		StringBuffer strbuf1 = new StringBuffer(); // ���ڴ𰸵�ƴ��
		// forѭ��ƴ����Ŀ�ʹ�
		for (int i = 1; i <= Integer.valueOf(s[0]); i++) {
			// �ж��꼶 �жϸ��꼶��Ӧ������
			if (s.length == 1) {
				operator = mark_code[(int) (Math.random() * 2)];
			} else {
				if (Integer.valueOf(s[1]) == 1) {
					operator = mark_code[(int) (Math.random() * 2)];
				} else if (Integer.valueOf(s[1]) == 2) {
					operator = mark_code[(int) (Math.random() * 4)];
				} else {
					System.out.println("�����꼶���󣡣�");
					return;
				}
			}
			// ��Ŀ���
			String number = "(" + i + ")" + " ";
			// ����2�������
			int first = (int) (Math.random() * 100);
			int second = (int) (Math.random() * 100);
			// �����꼶��������ָ������Ŀ
			if (operator.equals("+")) {
				if (s.length == 2 && s[1].equals("2")) {
					// ���꼶�ļӷ���Ŀ�𰸲�����100
					while (first + second > 100) {
						first = (int) (Math.random() * 100);
						second = (int) (Math.random() * 100);
					}
				} else {
					while (first + second > 100) {
						// 1�꼶�ļӷ���
						// 1. �𰸲�����100
						// 2. ������2λ��+��λ��
						// 3. ������2λ��+����10λ��
						if (first % 2 == 0) {
							first = (int) (Math.random() * 100);
							second = (int) (Math.random() * 100);
							if (second > 10)
								second = second / 10;
						} else {
							first = (int) (Math.random() * 100);
							if (first > 10)
								second = second / 10 * 10;
							second = (int) (Math.random() * 100);
						}
					}
				}
				result = first + second;
			} else if (operator.equals("-")) {
				// ������ߵ�����������ұߵ�����
				if (first < second) {
					int t;
					t = first;
					first = second;
					second = t;
				}
				if (!(s.length == 2 && s[1].equals("2"))) {
					// 2�꼶�ļ�����
					// 1. �𰸲���Ϊ����
					// 2. ������2λ��-��λ��
					// 3. ������2λ��-����10λ��
					if (second > 10)
						second = second / 10 * 10;
					result = first - second;
				} else {
					result = first - second;
				}
			} else if (operator.equals("*")) {
				// ���Ƴ˷�ֻ����99�˷��������
				while (first > 10 || second > 10) {
					first = (int) (Math.random() * 10);
					second = (int) (Math.random() * 10);
				}
				result = first * second;
			} else {
				// �������Ϊ��ʱ�� ��������Ϊ0
				while (second == 0) {
					second = (int) (Math.random() * 100);
				}
				// ���Ƴ���ֻ����С�ڵ���10
				while (second > 10) {
					second /= 10;
				}
				if (first % second == 0) {
					result = first / second;
				} else { // ���Ʋ����������𰸺���������ʾ����
					if (first > second) {
						result = (int) (first / second);
						remainder = first - (int) (first / second) * second;
					} else {
						result = (int) (first / second);
						remainder = second;
					}
				}
			}
			// ���Ϊ����Ҫ�����Ƿ�������ƴ���ڴ𰸺���
			if (operator.equals("/")) {
				strbuf1.append(number + first + " " + operator + " " + second + " " + "=" + " " + result + "..."
						+ remainder + "\r\n");
			} else {
				strbuf1.append(number + first + " " + operator + " " + second + " " + "=" + " " + result + "\r\n");
			}
			strbuf.append(number + first + " " + operator + " " + second + "\r\n");
		}
		// ����Ŀ�ʹ�ƴ����strbuf�ɱ��ַ�������
		strbuf = strbuf.append("\r\n" + strbuf1);
	}

	public static boolean check(String[] args) {
		// �жϲ����ĳ����Ƿ�Ϊ4
		if (args.length > 4 || args.length < 4) {
			System.out.println("�����������󣡣�");
			return false;
		}
		// �ж��Ƿ���-n ��-grade�ı�ʶ��
		if (!(("-n").equals(args[0]) && "-grade".equals(args[2])
				|| ("-n").equals(args[2]) && "-grade".equals(args[0]))) {
			System.out.println("-grade��-n��ʶ�����󣡣�");
			return false;
		}
		// �ų�-n������000001�������Ƶ����
		char[] allChar = args[1].toCharArray();
		char[] allChar1 = args[3].toCharArray();
		int k = 0;
		while (allChar[k++] == '0');
		args[1] = new String(allChar, k - 1, allChar.length - k + 1);
		k = 0;
		while (allChar1[k++] == '0');
		args[3] = new String(allChar1, k - 1, allChar1.length - k + 1);
		// �����Ŀ��������
		boolean matches;
		if (args[0].equals("-n")) {
			matches = args[1].matches("[0-9]*");
		} else {
			matches = args[3].matches("[0-9]*");
		}

		boolean matches1;
		
		// ����꼶�����Ƿ���1-3
		if (args[0].equals("-grade")) {
			matches1 = args[1].matches("[1-3]?");
		} else {
			matches1 = args[3].matches("[1-3]?");
		}
		if (!matches1) {
			System.out.println("�꼶��������ֻ����[1~3]����");
			return false;
		}
			
		//�ж���Ŀ�������Ƿ�Ϊ������
		if (matches && (args[1].length() > 4 || args[3].length() > 4)) {
			System.out.println("��Ŀ����̫���ˣ��������룡��");
			return false;
		} else if (!matches) {
			System.out.println("������������");
			return false;
		}
		return true;
	}
	/*
	 * �����ǵ��ȳ��㷨���沨�����ʽ��ʵ��
	 */
	class Node {

		double val;
		char opt;

		Node(Double val, Character opt) {
			this.val = val;
			this.opt = opt;
		}
	}

	Node[] node = new Node[10005];
	Character[] str = new Character[10005];
	HashMap<Character, Integer> mp = new HashMap<>();

	void Init() {
		mp.put('(', 0);
		mp.put('-', 1);
		mp.put('+', 1);
		mp.put('x', 2);
		mp.put('��', 2);
		mp.put('^', 3);
	}
	// �沨�����ʽ�ļ���
	double CalPoland(Node node[], int n) throws Exception {
		Stack<Double> s = new Stack<>();
		for (int i = 0; i < n; i++) {
			if (node[i].opt == ' ')
				s.push(node[i].val);
			else {
				double a = s.peek();
				s.pop();
				double b = s.peek();
				s.pop();
				switch (node[i].opt) {
				case '+':
					s.push(b + a);
					break;
				case '-':
					s.push(b - a);
					break;
				case 'x':
					s.push(b * a);
					break;
				case '��':
					// if (Math.abs(a) < 1e-9)
					// throw new Exception();
					s.push(b / a);
					break;
				case '^':
					s.push(Math.pow(b, a));
					break;
				}
			}
		}
		return s.peek();
	}
	// ���ȳ��㷨
	double ShuntYardAlgo(char str[]) throws Exception {
		Stack<Character> oper = new Stack<>();
		// inNum��ǵ�ǰ�Ƿ������������
		boolean inNum = true;
		// hasDot����Ƿ��Ѿ�����С����
		boolean hasDot = true;
		int p = 0; // ɨ��ָ��λ��
		int cnt = 0; // ���Ż������ּ�����
		int sign = 1; // ��ʾ��������

		while (p < str.length) {
			if (Character.isDigit(str[p]) || str[p] == '.') {
				if (inNum) {
					double val = 0;
					double w = 1;
					if (str[p] == '.') {
						hasDot = true;
						val = 0;
					} else {
						hasDot = false;
						val = str[p] - '0';
					}
					p++;
					while (p < str.length && (Character.isDigit(str[p]) || str[p] == '.')) {
						if (str[p] == '.') {
							if (hasDot)
								throw new Exception();
							hasDot = true;
						} else {
							if (hasDot) {
								w *= 0.1;
								val += (str[p] - '0') * w;
							} else
								val = val * 10 + str[p] - '0';
						}
						p++;
					}
					p--;
					node[cnt++] = new Node(val * sign, ' ');
					sign = 1;
					inNum = false;
				} else
					throw new Exception();
			} else {
				switch (str[p]) {
				case '(':
					oper.push(str[p]);
					break;
				case ')':
					while (!oper.empty() && oper.peek() != '(') {
						node[cnt++] = new Node(0.0, oper.peek());
						oper.pop();
					}
					if (oper.empty())
						throw new Exception();
					oper.pop();
					break;
				case '+':
				case '-':
				case 'x':
				case '��':
				case '^':
					if (inNum) {
						if (str[p] != '+' && str[p] != '-')
							while (str[p] == '+' || str[p] == '-') {
								if (str[p] == '-')
									sign *= -1;
								p++;
							}
						p--;
					} else {
						while (!oper.empty() && mp.get(str[p]) <= mp.get(oper.peek())) {
							node[cnt++] = new Node(0.0, oper.peek());
							oper.pop();
						}
						oper.push(str[p]);
						inNum = true;
					}
					break;
				}
			}
			p++;
		}
		while (!oper.empty()) {
			node[cnt++] = new Node(0.0, oper.peek());
			oper.pop();
		}
		return CalPoland(node, cnt);
	}
	public static void outPutFile(StringBuffer strbuf) {
		// ����Ŀ�ʹ𰸵Ŀɱ��ַ���ת�ɶ�����
		byte[] bytes = new String(strbuf).getBytes();
		// �������ƴ����ļ�����������out.txt
		try {
			FileOutputStream fos = new FileOutputStream("out.txt");
			try {
				fos.write(bytes);
			} catch (IOException e) {
				System.out.println("�ļ�д������");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// �������ִ�еĽ��
		System.out.println("��Ŀ�Ѿ����ɣ��������out.txt");
	}
}
