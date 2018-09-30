package com.it666.com;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class checkTest {
	boolean expected = true;
	static String[] str;
	static String cont = "";

	@Parameters
	public static Collection<Object[]> t() {
		return Arrays.asList(new Object[][] { { false, new String[] { "-n", "-10", "-grade", "3" }, "请输入正整数\r\n" },
				{ false, new String[] { "-n", "10", "-grade", "5" }, "年级参数错误，只能在[1~3]以内\r\n" },
				{ false, new String[] { "-n", "100000", "-grade", "2" }, "题目数量太多了，重新输入！！\r\n" },
				{ false, new String[] { "-grade", "2", "-n", "100000" }, "题目数量太多了，重新输入！！\r\n" },
				{ false, new String[] { "asd", "100", "-grade", "1" }, "-grade和-n标识符错误！！\r\n" },
				{ false, new String[] { "asd", "100", "-grade", "1" }, "-grade和-n标识符错误！！\r\n" },
				{ true, new String[] { "-n", "100", "-grade", "1" }, "" },
				{ false, new String[] { "-n", "-1", "-grade", "3" }, "请输入正整数\r\n" },
				{ false, new String[] {}, "参数数量有误！！\r\n" }, { false, new String[] { "100" }, "参数数量有误！！\r\n" },
				{ false, new String[] { "-n", "10.5", "-grade", "1" }, "请输入正整数\r\n" },
				{ false, new String[] { "-n", "ascc", "-grade", "2" }, "请输入正整数\r\n" },
				{ false, new String[] { "-n", "10", "-grade", "vsdv" }, "年级参数错误，只能在[1~3]以内\r\n" },
				{ true, new String[] { "-n", "00001", "-grade", "3" }, "" },
				{ false, new String[] { "-n", "1000", "-grade", "2.3" }, "年级参数错误，只能在[1~3]以内\r\n" },
				{ true, new String[] { "-n", "10", "-grade", "002" }, "" },
				{ false, new String[] { "-n", "10000", "-grade", "3" }, "题目数量太多了，重新输入！！\r\n" },
				{ false, new String[] { "1000", "-n", "-grade", "2" }, "-grade和-n标识符错误！！\r\n" },
				{ false, new String[] { "-n", "10", "-grade", "-3" }, "年级参数错误，只能在[1~3]以内\r\n" },
				{ false, new String[] { "-n", "1000", "2", "-grade" }, "-grade和-n标识符错误！！\r\n" },
				{ true, new String[] { "-grade", "2", "-n", "1000" }, "" },
				{ false, new String[] { "-grade", "0.1", "-n", "800" }, "年级参数错误，只能在[1~3]以内\r\n" },
				{ false, new String[] { "-grade", "a1", "-n", "10" }, "年级参数错误，只能在[1~3]以内\r\n" },
				{ true, new String[] { "-grade", "001", "-n", "20" }, "" },
				{ true, new String[] { "-grade", "1", "-n", "0000000002" }, "" },
				{ false, new String[] { "-grade", "0.1", "-n", "0.1" }, "年级参数错误，只能在[1~3]以内\r\n" },
				{ false, new String[] { "-n", "a1", "-grade", "a1" }, "年级参数错误，只能在[1~3]以内\r\n" },
				{ true, new String[] { "-n", "10", "-grade", "3" }, "" },
				{ false, new String[] { "-n", "-n", "10", "-grade", "3" }, "参数数量有误！！\r\n" }

		});
	}

	public checkTest(boolean expected, String[] str, String cont) {
		this.expected = expected;
		this.str = str;
		this.cont = cont;
	}

	@Test
	public void testCheck() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		assertEquals(expected, new MathExam().check(str));
		assertEquals(cont, outContent.toString());

	}

	@Test
	public void testOutPut() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		StringBuffer strbuf = new StringBuffer();
		strbuf.append("asdasak佳世客劳动竞赛啊是的as ");
		new MathExam().outPutFile(strbuf);
		assertEquals("题目已经生成，详情请见out.txt\r\n", outContent.toString());

	}

	@Test
	public void testinit() {
		new MathExam().Init();
	}

	@Test
	public void testNode() {
		new MathExam().new Node(1.0, 'a');
	}

	@Test
	public void testInit1_2() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		String[] ars = {"900","2"};
		new MathExam().init1_2(new StringBuffer(), ars);
		assertEquals("", outContent.toString());
		String[] ars1 = {"3","300"};
		new MathExam().init1_2(new StringBuffer(), ars1);
		assertEquals("输入年级有误！！\r\n", outContent.toString());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testinit3() throws Exception {
		String[] str = new String[] { "-grade", "3", "-n", "300" };
		StringBuffer init3 = new MathExam().init3(str);
		assertEquals(true, init3!=null);
	}
	
	@Test
	public void testmain() throws Exception {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		new MathExam().main(new String[] { "-grade", "3", "-n", "300" });
		assertEquals("题目已经生成，详情请见out.txt\r\n", outContent.toString());
		new MathExam().main(new String[] { "-grade", "2", "-n", "300" });
		assertEquals("题目已经生成，详情请见out.txt\r\n", outContent.toString());
	}
	
}
