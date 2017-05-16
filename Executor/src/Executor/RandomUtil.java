package Executor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomUtil {

	private static final int RANDOM_SEED = 10000;

	private static final int SIZE = 10000;

	/**
	 * 产生10000万个随机数(范围1~9999)，并将这些数据添加到指定文件中去。
	 * 
	 * 例如：
	 * 
	 * 1=7016 2=7414 3=3117 4=6711 5=5569 ... ... 9993=1503 9994=9528 9995=9498
	 * 9996=9123 9997=6632 9998=8801 9999=9705 10000=2900
	 */
	public static void generatedRandomNbrs(String filepath) {
		Random random = new Random();
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(new File(filepath)));
			for (int i = 0; i < SIZE; i++) {
				bw.write((i + 1) + "=" + random.nextInt(RANDOM_SEED));
				bw.newLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != bw) {
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					bw = null;
				}
			}
		}
	}

	/**
	 * 从指定文件中提取已经产生的随机数集
	 */
	public static int[] populateValuesFromFile(String filepath) {
		BufferedReader br = null;
		int[] values = new int[SIZE];

		try {
			br = new BufferedReader(new FileReader(new File(filepath)));
			int count = 0;
			String line = null;
			while (null != (line = br.readLine())) {
				values[count++] = Integer.parseInt(line.substring(line.indexOf("=") + 1));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					br = null;
				}
			}
		}
		return values;
	}

}