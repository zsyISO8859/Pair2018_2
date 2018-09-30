package com.it666.com;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

public class MathExam {
	/* 主函数功能：
	 * 1.check()检查输入参数的各种bug
	 * 2.if判断当前输入的年级、根据年级进入不同的题目生成器
	 * 3.outPutFile()函数将题目和答案输出成out.txt
	 * */
	public static void main(String[] args) throws Exception {
		// 检测输入参数的各种bug
		if (!check(args))return;
		//判断年级
		else if (args[0].equals("-grade") && args[1].equals("3") || args[2].equals("-grade") && args[3].equals("3")) {
			StringBuffer strbuf2 = init3(args);
			outPutFile(strbuf2);
		} else {
			//将4个参数改成2个参数传给运算1、2年级的程序
			String[] strArgs = new String[2];
			strArgs[0] = args[args[0].equals("-n") ? 1 : 3];
			strArgs[1] = args[args[0].equals("-grade") ? 1 : 3];
			// 创建一个可变字符串用来拼接题目和答案
			StringBuffer stringBuffer = new StringBuffer();
			// 初始化获取题目和答案
			init1_2(stringBuffer, strArgs);
			outPutFile(stringBuffer);
		}
	}

	public static StringBuffer init3(String[] args) throws Exception {
		int m = 1;
		int l = 1;
		String str = null;
		// 生成2个字符串用于拼接题目和答案
		StringBuffer strbuf = new StringBuffer();
		StringBuffer strbuf1 = new StringBuffer();
		StringBuffer strbuf2 = new StringBuffer();
		// random用于随机生成题目中的操作数
		int random = 0;
		// int flag=5;
		// 将四种运算符放入数组
		String[] strop = { "+", "-", "x", "÷" };
		// 将随机生成的2-4个运算符存入ostr数组
		String[] ostr = new String[4];
		int w = Integer.valueOf(args[args[0].equals("-n") ? 1 : 3]);
		for (int i = 0; i < w; i++) {
			// 随机生成的2-3个运算符
			int operator = (int) (Math.random() * 3) + 2;
			int k = 0;
			// 随机生成运算符号（至少有2种不同运算符号）
			for (int j = 0; j < operator; j++) {
				k = (int) (Math.random() * 4);
				ostr[j] = strop[k];
				if (operator == 2 && j == 1) {
					// 控制在只有2个运算符的情况下2个运算符号一定不同
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
					// 如果运算符有+-就自动生成括号运算符
					if (j < operator && (ostr[j].equals("+") || ostr[j].equals("-")) && flag == 1) {
						strbuf.append("( ");
						flag = 0;// 判断是否加了左括号0代表已近加了
						flag1 = 0;// 判断是否可以加右括号了
					}
				random = (int) (Math.random() * 101);
				if (j == operator) {
					// 最后一个随机数的末尾不加空格
					if (flag == 0 && flag1 == 1) {
						strbuf.append(random + " )");
						flag1 = 0;
						flag = 100;
					} else {
						strbuf.append(random);
						if (flag == 0) {
							// 在最后一个数后面加上右括号
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
							// 在下一次循环当中可以加入右括号
							flag1 = 1;
						}
					}
				}
				if (j < operator)
					strbuf.append(ostr[j] + " ");
			}
			l = 1;
			// 重新初始化数组
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

				// strbuf2用于拼接题目
				strbuf2.append("(" + m + ")" + " " + strbuf + "\r\n");
				// strbuf1用于拼接答案
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
	 * init函数产生题目和答案
	 */
	public static void init1_2(StringBuffer strbuf, String[] s) {

		int result = 0; // 表示题目答案
		int remainder = 0; // 表示余数
		String operator = null; // 表示随机出来的运算符
		String[] mark_code = { "+", "-", "*", "/" }; // 小学1年级和2年级所有的运算
		StringBuffer strbuf1 = new StringBuffer(); // 用于答案的拼接
		// for循环拼接题目和答案
		for (int i = 1; i <= Integer.valueOf(s[0]); i++) {
			// 判断年级 判断该年级对应的运算
			if (s.length == 1) {
				operator = mark_code[(int) (Math.random() * 2)];
			} else {
				if (Integer.valueOf(s[1]) == 1) {
					operator = mark_code[(int) (Math.random() * 2)];
				} else if (Integer.valueOf(s[1]) == 2) {
					operator = mark_code[(int) (Math.random() * 4)];
				} else {
					System.out.println("输入年级有误！！");
					return;
				}
			}
			// 题目序号
			String number = "(" + i + ")" + " ";
			// 生成2个随机数
			int first = (int) (Math.random() * 100);
			int second = (int) (Math.random() * 100);
			// 按照年级需求生成指定的题目
			if (operator.equals("+")) {
				if (s.length == 2 && s[1].equals("2")) {
					// 二年级的加法题目答案不超过100
					while (first + second > 100) {
						first = (int) (Math.random() * 100);
						second = (int) (Math.random() * 100);
					}
				} else {
					while (first + second > 100) {
						// 1年级的加法：
						// 1. 答案不超过100
						// 2. 可以是2位数+个位数
						// 3. 可以是2位数+整的10位数
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
				// 控制左边的数必须大于右边的数。
				if (first < second) {
					int t;
					t = first;
					first = second;
					second = t;
				}
				if (!(s.length == 2 && s[1].equals("2"))) {
					// 2年级的减法：
					// 1. 答案不能为负数
					// 2. 可以是2位数-个位数
					// 3. 可以是2位数-整的10位数
					if (second > 10)
						second = second / 10 * 10;
					result = first - second;
				} else {
					result = first - second;
				}
			} else if (operator.equals("*")) {
				// 控制乘法只能是99乘法表上面的
				while (first > 10 || second > 10) {
					first = (int) (Math.random() * 10);
					second = (int) (Math.random() * 10);
				}
				result = first * second;
			} else {
				// 当运算符为除时候 除数不能为0
				while (second == 0) {
					second = (int) (Math.random() * 100);
				}
				// 控制除数只能是小于等于10
				while (second > 10) {
					second /= 10;
				}
				if (first % second == 0) {
					result = first / second;
				} else { // 控制不能整除将答案和余数都显示出来
					if (first > second) {
						result = (int) (first / second);
						remainder = first - (int) (first / second) * second;
					} else {
						result = (int) (first / second);
						remainder = second;
					}
				}
			}
			// 如果为除法要考虑是否有余数拼接在答案后面
			if (operator.equals("/")) {
				strbuf1.append(number + first + " " + operator + " " + second + " " + "=" + " " + result + "..."
						+ remainder + "\r\n");
			} else {
				strbuf1.append(number + first + " " + operator + " " + second + " " + "=" + " " + result + "\r\n");
			}
			strbuf.append(number + first + " " + operator + " " + second + "\r\n");
		}
		// 将题目和答案拼接在strbuf可变字符串当中
		strbuf = strbuf.append("\r\n" + strbuf1);
	}

	public static boolean check(String[] args) {
		// 判断参数的长度是否为4
		if (args.length > 4 || args.length < 4) {
			System.out.println("参数数量有误！！");
			return false;
		}
		// 判断是否用-n 和-grade的标识符
		if (!(("-n").equals(args[0]) && "-grade".equals(args[2])
				|| ("-n").equals(args[2]) && "-grade".equals(args[0]))) {
			System.out.println("-grade和-n标识符错误！！");
			return false;
		}
		// 排除-n参数的000001这种类似的情况
		char[] allChar = args[1].toCharArray();
		char[] allChar1 = args[3].toCharArray();
		int k = 0;
		while (allChar[k++] == '0');
		args[1] = new String(allChar, k - 1, allChar.length - k + 1);
		k = 0;
		while (allChar1[k++] == '0');
		args[3] = new String(allChar1, k - 1, allChar1.length - k + 1);
		// 检查题目都是数字
		boolean matches;
		if (args[0].equals("-n")) {
			matches = args[1].matches("[0-9]*");
		} else {
			matches = args[3].matches("[0-9]*");
		}

		boolean matches1;
		
		// 检测年级参数是否是1-3
		if (args[0].equals("-grade")) {
			matches1 = args[1].matches("[1-3]?");
		} else {
			matches1 = args[3].matches("[1-3]?");
		}
		if (!matches1) {
			System.out.println("年级参数错误，只能在[1~3]以内");
			return false;
		}
			
		//判断题目的数量是否为正整数
		if (matches && (args[1].length() > 4 || args[3].length() > 4)) {
			System.out.println("题目数量太多了，重新输入！！");
			return false;
		} else if (!matches) {
			System.out.println("请输入正整数");
			return false;
		}
		return true;
	}
	/*
	 * 以下是调度场算法和逆波兰表达式的实现
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
		mp.put('÷', 2);
		mp.put('^', 3);
	}
	// 逆波兰表达式的计算
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
				case '÷':
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
	// 调度场算法
	double ShuntYardAlgo(char str[]) throws Exception {
		Stack<Character> oper = new Stack<>();
		// inNum标记当前是否可以输入数字
		boolean inNum = true;
		// hasDot标记是否已经输入小数点
		boolean hasDot = true;
		int p = 0; // 扫描指针位置
		int cnt = 0; // 符号或者数字计数器
		int sign = 1; // 表示正负符号

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
				case '÷':
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
		// 将题目和答案的可变字符串转成二级制
		byte[] bytes = new String(strbuf).getBytes();
		// 将二进制传入文件输出流输出成out.txt
		try {
			FileOutputStream fos = new FileOutputStream("out.txt");
			try {
				fos.write(bytes);
			} catch (IOException e) {
				System.out.println("文件写出错误");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// 输出程序执行的结果
		System.out.println("题目已经生成，详情请见out.txt");
	}
}
