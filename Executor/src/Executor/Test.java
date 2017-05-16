package Executor;

import java.util.Arrays;

public class Test {

	private static final String FILE_PATH = "D:\\RandomNumber.txt";

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		/*
		 * 如果随机数已经存在文件中，可以不再调用此方法，除非想用新的随机数据。
		 */
		generateRandomNbrs();

		process1();

		process2();

		process3();

	}

	private static void generateRandomNbrs() {
		RandomUtil.generatedRandomNbrs(FILE_PATH);
	}

	private static void process1() {
		long start = System.currentTimeMillis();
		System.out.println("没有使用Executor框架，直接使用Arrays.sort获取top 100");
		printTop100(populateTop100(RandomUtil.populateValuesFromFile(FILE_PATH)));
		long end = System.currentTimeMillis();
		System.out.println((end - start) / 1000.0);
	}

	private static void process2() {
		long start = System.currentTimeMillis();

		System.out.println("使用ExecutorCompletionService获取top 100");

		ConcurrentCalculator calculator = new ConcurrentCalculator();
		Integer[] top100 = calculator.top100(RandomUtil.populateValuesFromFile(FILE_PATH));
		for (int i = 0; i < top100.length; i++) {
			System.out.println(String.format("top%d = %d", (i + 1), top100[i]));
		}
		long end = System.currentTimeMillis();
		System.out.println((end - start) / 1000.0);
	}

	private static void process3() {
		long start = System.currentTimeMillis();
		System.out.println("使用FutureTask 获取top 100");

		ConcurrentCalculator2 calculator2 = new ConcurrentCalculator2();
		Integer[] top100 = calculator2.top100(RandomUtil.populateValuesFromFile(FILE_PATH));
		for (int i = 0; i < top100.length; i++) {
			System.out.println(String.format("top%d = %d", (i + 1), top100[i]));
		}
		long end = System.currentTimeMillis();
		System.out.println((end - start) / 1000.0);
	}

	private static int[] populateTop100(int[] values) {
		Arrays.sort(values);
		int[] top100 = new int[100];
		int length = values.length;
		for (int i = 0; i < 100; i++) {
			top100[i] = values[length - 1 - i];
		}
		return top100;
	}

	private static void printTop100(int[] top100) {
		for (int i = 0; i < top100.length; i++) {
			System.out.println(String.format("top%d = %d", (i + 1), top100[i]));
		}
	}

}