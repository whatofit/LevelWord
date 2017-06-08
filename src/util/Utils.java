package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	// 1����֤����·���Ƿ�Ϊ��ȷ��·����(Windowsϵͳ������ϵͳδʹ��)
	// ��֤�ַ����Ƿ�Ϊ��ȷ·������������ʽ
	private static String matches = "[A-Za-z]:\\\\[^:?\"><*]*";

	// ͨ�� sPath.matches(matches) �����ķ���ֵ�ж��Ƿ���ȷ
	// sPath Ϊ·���ַ���
	/** checkString�Ƿ�Ϊnull����ַ���"" */
	public static boolean isEmpty(String str) {
		if (str == null || "".equalsIgnoreCase(str)) {
			return true;
		}
		return false;
	}

	/** ��ȡ�ļ����ƣ���������չ�� */
	public static String getFileName(String path) {
		if (Utils.isEmpty(path)) {
			return "";
		}

		// String name =
		// "D:/Jobs/DevIDEs/workspace/FmyVocabulary/./vocabulary_ciba/00058-some.xml";
		// "./vocabulary_ciba/00058-some.xml"
		int index = path.lastIndexOf("/");
		if (index != -1) {
			// name = name.substring(name.lastIndexOf("\\") + 1, name.length());

			return path.substring(index + 1, path.length() - 4);
		}
		return path;
	}

	/**
	 * �ָ��ļ�������������չ�� "./vocabulary_ciba/00058-some.xml" �ָ�ɣ�00058��some
	 * */
	public static String[] splitFileName(String path) {
		if (Utils.isEmpty(path)) {
			return new String[] { "", "" };
		}

		// String name =
		// "D:/Jobs/DevIDEs/workspace/FmyVocabulary/./vocabulary_ciba/00058-some.xml";
		// "./vocabulary_ciba/00058-some.xml"
		int index = path.lastIndexOf("/");
		if (index != -1) {
			// name = name.substring(name.lastIndexOf("\\") + 1, name.length());

			return path.substring(index + 1, path.length() - 4).split("-");
		}
		return new String[] { path, "" };
	}

	/**
	 * �ж��ַ������Ƿ���һ�Լ������а������֣�����У��Ѽ������滻Ϊ������
	 * 
	 * �⣬�����������壻�չ⣬������<ʫ>���������� �⣬�����������壻�չ⣬������[ʫ]����������
	 * 
	 * @param str
	 *            ������ַ���
	 * @return
	 */
	// ^[\u2E80-\u9FFF]+$
	// ƥ�����ж�����������
	// ^[\u4E00-\u9FFF]+$
	// ƥ�����ͷ���
	// ^[\u4E00-\u9FA5]+$
	// ƥ�����

	public static String replaceAngleBrackets(String body) {
		String regExp = "([<]{1}([\u0391-\uFFE5]+)[>]{1})";

		Pattern p = Pattern.compile(regExp);
		boolean result = false;
		do {
			Matcher m = p.matcher(body);
			result = m.find();
			// System.out.println(result);
			if (result) {
				// System.out.println(m.group(0));
				String matcher1 = m.group(1);
				// System.out.println(ma);
				String matcher2 = m.group(2);
				// System.out.println(ma2);
				String replacement = "[" + matcher2 + "]";// <(\w+)>
				body = body.replaceAll(matcher1, replacement);
				// System.out.println(body);
			}
		} while (result);
		return body;
	}

	/** ��body��ӵ�filename�ļ�ĩβ */
	public static void writerFileTest(String filename, String body) {
		FileWriter fw;
		try {
			// "C:/add2.txt"
			fw = new FileWriter(filename, true);
			fw.write(body);
			fw.write("\r\n");
			fw.flush();
			fw.close();
		} catch (IOException e) {
			System.out.println("IOException when write file: " + filename);
		}
	}

	// 2��ʵ��ɾ���ļ��ķ���
	/**
	 * ɾ�������ļ�
	 * 
	 * @param sPath
	 *            ��ɾ���ļ����ļ���
	 * @return �����ļ�ɾ���ɹ�����true�����򷵻�false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// ·��Ϊ�ļ��Ҳ�Ϊ�������ɾ��
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	// 3��ͨ�õ��ļ��л��ļ�ɾ��������ֱ�ӵ��ô˷���������ʵ��ɾ���ļ��л��ļ��������ļ����µ������ļ�
	/**
	 * ����·��ɾ��ָ����Ŀ¼���ļ������۴������
	 * 
	 * @param sPath
	 *            Ҫɾ����Ŀ¼���ļ�
	 * @return ɾ���ɹ����� true�����򷵻� false��
	 */
	public static boolean DeleteFolder(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// �ж�Ŀ¼���ļ��Ƿ����
		if (!file.exists()) { // �����ڷ��� false
			return flag;
		} else {
			// �ж��Ƿ�Ϊ�ļ�
			if (file.isFile()) { // Ϊ�ļ�ʱ����ɾ���ļ�����
				return deleteFile(sPath);
			} else { // ΪĿ¼ʱ����ɾ��Ŀ¼����
				return deleteDirectory(sPath);
			}
		}
	}

	// 4��ʵ��ɾ���ļ��еķ�����
	/**
	 * ɾ��Ŀ¼���ļ��У��Լ�Ŀ¼�µ��ļ�
	 * 
	 * @param sPath
	 *            ��ɾ��Ŀ¼���ļ�·��
	 * @return Ŀ¼ɾ���ɹ�����true�����򷵻�false
	 */
	public static boolean deleteDirectory(String sPath) {
		// ���sPath�����ļ��ָ�����β���Զ�����ļ��ָ���
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// ���dir��Ӧ���ļ������ڣ����߲���һ��Ŀ¼�����˳�
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// ɾ���ļ����µ������ļ�(������Ŀ¼)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// ɾ�����ļ�
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // ɾ����Ŀ¼
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// ɾ����ǰĿ¼
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Utils hfc = new Utils();
		// String path = "D:\\Abc\\123\\Ab1";
		// // boolean result = hfc.CreateFolder(path);
		// // System.out.println(result);
		// path = "D:\\Abc\\124";
		// boolean result = hfc.DeleteFolder(path);
		// System.out.println(result);

		// String name = Utils.getFileName("./vocabulary_ciba/00058-some.xml");
		// System.out.println(name);
		//
		// String[] name2 =
		// Utils.splitFileName("./vocabulary_ciba/00058-some.xml");
		// System.out.println(name2[0] +"-----" + name2[1]);

		String mXmlWordFile = "./src/06169-corn.xml";
		String body = FileUtil.readFile(mXmlWordFile);
		System.out.println("--body---" + body);
		// String body = "��ʾ<����>��";
		String ret = Utils.replaceAngleBrackets(body);
		System.out.println("--ret---" + ret);
	}

}
