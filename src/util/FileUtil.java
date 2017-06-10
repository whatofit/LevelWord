package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileUtil {

	public static String readFile2(String filename) {
		StringBuffer sb = new StringBuffer();
		try {
			FileInputStream f = new FileInputStream(filename);
			InputStreamReader isr = new InputStreamReader(f);
			System.out.println("isr.getEncoding() =" + isr.getEncoding());
			BufferedReader dr = new BufferedReader(isr);
			String line = dr.readLine();
			while (line != null) {
				sb.append(new String(line.getBytes("GBK")));// gb2312
				line = dr.readLine();
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	// "d:/sql.txt"
	public static String readtxt(String filename)  {
		BufferedReader br;
		String str = "";
		try {
			br = new BufferedReader(new FileReader(filename));
			String r = br.readLine();
			while (r != null) {
				str += r;
				r = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String readFile(String filename) {
		StringBuffer sb = new StringBuffer();
		try {
			String encoding = "UTF-8"; // 字符编码(可解决中文乱码问题 )
			File file = new File(filename);
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTXT = "";
				while ((lineTXT = bufferedReader.readLine()) != null) {
					sb.append(lineTXT);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件！");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容操作出错");
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String readFile3(String filename) {
		try {
			String encoding = "UTF-8"; // 字符编码(可解决中文乱码问题 ) //"GB2312"
			File file = new File(filename);
			if (file.isFile() && file.exists()) {
				FileInputStream in = new FileInputStream(file);
				int fileSize = in.available(); // size为文件长度 ，这里一次性读完
				byte[] buffer = new byte[fileSize];
				in.read(buffer);
				in.close();
				return new String(buffer, encoding);
			} else {
				System.out.println("找不到指定的文件！");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容操作出错");
			e.printStackTrace();
		}
		return "";
	}

	public static String readFile4(String filename) {
		try {
			String encoding = "UTF-8"; // 字符编码(可解决中文乱码问题 ) //"GB2312"
			File file = new File(filename);
			if (file.isFile() && file.exists()) {
				FileInputStream fis = new FileInputStream(file);
				FileChannel fc = fis.getChannel();
				ByteBuffer bb = ByteBuffer.allocate(new Long(file.length())
						.intValue());
				// fc向buffer中读入数据
				fc.read(bb);
				bb.flip();
				String str = new String(bb.array(), encoding);
				fc.close();
				fis.close();
				return str;
			} else {
				System.out.println("找不到指定的文件！");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容操作出错");
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filename = "e:/00835-wish.xml";
		String ret = FileUtil.readFile(filename);
		System.out.println("--ret---" + ret);
	}
}
