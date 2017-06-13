//package dict3;
//
////由胡正开发的星际译王是Linux平台上很强大的一个开源的翻译软件（也有Windows版本的）支持多种词库、多种语言版本。尤其词库设计比较合理。
////之前看到一篇博文《星际译王词库应用-自制英汉词典》中用简短的程序就实现了词典的基本功能，不过那个是Linux 下的C/C++版本的，于是决定参考移植一个JAVA版本。
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.*;
//
///**
// * 
// * Java版词典测试版，可以在控制台下输入要查询的单词，回车后会给出单词在词典中的释义 词典采用星际译王的词典，本程序主要针对英汉词典
// * 
// * @author dave
// * @updateDate 2012-12-31
// * @version v1.0.0
// * 
// *          相关参考链接： http://blog.chinaunix.net/uid-20454005-id-1675913.html
// *          http://hi.baidu.com/sean_zhu_xiang/item/1581342f88be430e73863eee
// *          http://blog.csdn.net/ranxiedao/article/details/7787342
// *          http://www.stardict.cn/ http://www.huzheng.org/
// *          http://code.google.com/p/stardict-3/downloads/list
// * 
// */
//public class JavaStarDict {
//	final static int MAX_WORD = 256;// 最长输入单词字符数
//	final static int MAX_KEYS = 27;// 26个字母+"-"开头的后缀
//	final static int SIZEINT = 4;
//	final static String KEY[] = {// 26个字母索引+"-"开头的后缀，不区分大小写
//	"A", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
//			"p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "-" };
//	public static InputStream isidx = null;// 读取idx文件时所要的流
//	public static InputStream isdict = null;// 读取dict文件时所要的流
//	public static long STREAM_LOCAL = 0;// 记录单词索引在文件流中的位置
////	public static String idxfileString = "oxford-gb.idx";// idx文件路径
////	public static String dictfileString = "oxford-gb.dict";// dict文件路径
//	public static String idxfileString = "./src/powerword2007_pwqec.idx";// idx文件路径
//	public static String dictfileString = "./src/powerword2007_pwqec.dict";// dict文件路径
//
//	/**
//	 * 从idx文件中获取当前目标单词
//	 * 
//	 * @param word_buf
//	 *            保存的是c/c++字符串数组转换为JAVA字符串
//	 * @param data_poffset
//	 *            用来保存单词的data偏移位置信息
//	 * @param data_plength
//	 *            用来保存单词的data长度信息
//	 * @param len
//	 * @return
//	 */
//	public static boolean get_word(String[] word_buf, int[] data_poffset,
//			int[] data_plength, int[] len) {
//		// int len = 0;
//		boolean flag = true;
//		len[0] = 0;
//		int index = -1;
//		byte wd[] = new byte[MAX_WORD];
//		int value = 0;
//		try {
//			// 读取单词，对每个字母开头的单词都进行搜索，最多考虑256个字符的单词，
//			// 读到单词结束符\0时赋值表达式的值就不满足while条件而退出
//			while (true) {
//				index = isidx.read();
//				STREAM_LOCAL++;// 每读取一次，位置标识加一以记录下单词在文件流中的起始位置
//				if (index == -1) {
//					// isidx.reset();
//					flag = false;
//					break;
//				}
//				if ((index != 0) && (len[0] < MAX_WORD)) {
//					wd[len[0]] = (byte) index;// 将int转换为byte
//					len[0]++;
//				} else {
//					break;
//				}
//			}
//			// 转换为JAVA字符串
//			// 此处不用再需要像c/c++那样去掉了最后那个结束符了
//			byte wd2[] = new byte[len[0]];
//			for (int i = 0; i < len[0]; i++) {
//				wd2[i] = wd[i];
//			}
//			word_buf[0] = new String(wd2);
//			// System.out.println("get_word:"+word_buf[0]+" len:"+len[0]);
//			// wd = null;// 释放内存
//			// wd2 = null;
//			// 读取偏移量值
//			for (int i = 0; i < SIZEINT; i++) {
//				// 将4个byte转换为int
//				int shift = (4 - 1 - i) * 8;
//				index = isidx.read();
//				STREAM_LOCAL++;// 每读取一次，位置标识加一以记录下单词在文件流中的起始位置
//				if (index == -1) {
//					// isidx.reset();
//					flag = false;
//					return flag;
//				}
//				value += (index & 0x00FF) << shift;
//			}
//			data_poffset[0] = value;
//			// 读取区块大小值
//			value = 0;
//			for (int i = 0; i < SIZEINT; i++) {
//				// 将4个byte转换为int
//				int shift = (4 - 1 - i) * 8;
//				index = isidx.read();
//				STREAM_LOCAL++;// 每读取一次，位置标识加一以记录下单词在文件流中的起始位置
//				if (index == -1) {
//					// isidx.reset();
//					flag = false;
//					return flag;
//				}
//				value += (index & 0x00FF) << shift;
//			}
//			data_plength[0] = value;
//		} catch (Exception e) {
//			System.out.println("idx file read error!");
//		}
//		// System.out.println("Now local is:"+STREAM_LOCAL);
//		// 得到单词字符长度
//		return flag;
//	}
//
//	/**
//	 * 通过偏移位置offset和长度length 来从dict文件中获取data内容UTF-8编码的字符
//	 * 
//	 * @param offset
//	 *            要读取的内容的起始偏移，为字节数
//	 * @param length
//	 *            要读取的内容的数据块大小，为字节数
//	 * @return 字节数组的data int
//	 */
//	public static byte[] get_data(int[] offset, int[] length) {
//		long oft = offset[0];
//		long len = length[0];
//		long skip;
//		byte data_buf[] = new byte[length[0]];
//		System.out.println("This word's" + "offset:" + offset[0] + "len:"
//				+ length[0]);
//		try {
//			isdict.reset();
//			long valuedata = isdict.available();
//			if (valuedata < oft + len) {
//				System.out.println("No so much value data! " + valuedata);
//			}
//			// skip=isdict.skip(oft);
//			skip = skipBytesFromStream(isdict, oft);
//			if (skip != oft) {
//				System.out.println("Skip" + skip + " dict file error!");
//			}
//			if (isdict.read(data_buf) == -1) {
//				System.out.println("Arrive at the end of file!");
//			}
//			// // Unicode
//			// StringBuffer sb = new StringBuffer();
//			//
//			// int size =isdict.read(data_buf);
//			//
//			// for (int j = 0; j < size;)
//			// {
//			//
//			// int l = data_buf[j++];
//			//
//			// int h = data_buf[j++];
//			//
//			// char c = (char) ((l & 0xff) | ((h << 8) & 0xff00));
//			//
//			// sb.append(c);
//			//
//			// }
//			//
//			// // return sb.toString();
//		} catch (Exception e) {
//			data_buf = null;
//			System.out.println("dict file read error!");
//			e.printStackTrace();
//		}
//		if (data_buf == null) {
//			return null;
//		}
//		return data_buf;
//	}
//
//	/**
//	 * utf8解码 参考自http://hi.baidu.com/leo10086/item/d6853813373b19001994ec24 用法:
//	 * 假如 newContent 为UTF8编码的字符串 byte[] b = newContent.getBytes(); newContent =
//	 * URLEncoder.UTF8Decode( b, 0, b.length );
//	 * 
//	 * @param in
//	 *            要进行解码的UTF8编码的字节数组
//	 * @param offset
//	 * @param length
//	 * @return
//	 */
//	public static String UTF8Decode(byte in[], int offset, int length) {
//		StringBuffer buff = new StringBuffer();
//		int max = offset + length;
//		for (int i = offset; i < max; i++) {
//			char c = 0;
//			if ((in[i] & 0x80) == 0) {
//				c = (char) in[i];
//			} else if ((in[i] & 0xe0) == 0xc0) // 11100000
//			{
//				c |= ((in[i] & 0x1f) << 6); // 00011111
//				i++;
//				c |= ((in[i] & 0x3f) << 0); // 00111111
//			} else if ((in[i] & 0xf0) == 0xe0) // 11110000
//			{
//				c |= ((in[i] & 0x0f) << 12); // 00001111
//				i++;
//				c |= ((in[i] & 0x3f) << 6); // 00111111
//				i++;
//				c |= ((in[i] & 0x3f) << 0); // 00111111
//			} else if ((in[i] & 0xf8) == 0xf0) // 11111000
//			{
//				c |= ((in[i] & 0x07) << 18); // 00000111 (move 18, not 16?)
//				i++;
//				c |= ((in[i] & 0x3f) << 12); // 00111111
//				i++;
//				c |= ((in[i] & 0x3f) << 6); // 00111111
//				i++;
//				c |= ((in[i] & 0x3f) << 0); // 00111111
//			} else {
//				c = ' ';
//			}
//			buff.append(c);
//		}
//		return buff.toString();
//	}
//
//	public static byte[] UTF8Encode(String str) {
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		try {
//			int strlen = str.length();
//			for (int i = 0; i < strlen; i++) {
//				char t = str.charAt(i);
//				int c = 0;
//				c |= (t & 0xffff);
//				if (c >= 0 && c < 0x80) {
//					bos.write((byte) (c & 0xff));
//				} else if (c > 0x7f && c < 0x800) {
//					bos.write((byte) (((c >>> 6) & 0x1f) | 0xc0));
//					bos.write((byte) (((c >>> 0) & 0x3f) | 0x80));
//				} else if (c > 0x7ff && c < 0x10000) {
//					bos.write((byte) (((c >>> 12) & 0x0f) | 0xe0)); // <--
//					// correction
//					// (mb)
//					bos.write((byte) (((c >>> 6) & 0x3f) | 0x80));
//					bos.write((byte) (((c >>> 0) & 0x3f) | 0x80));
//				} else if (c > 0x00ffff && c < 0xfffff) {
//					bos.write((byte) (((c >>> 18) & 0x07) | 0xf0));
//					bos.write((byte) (((c >>> 12) & 0x3f) | 0x80));
//					bos.write((byte) (((c >>> 6) & 0x3f) | 0x80));
//					bos.write((byte) (((c >>> 0) & 0x3f) | 0x80));
//				}
//			}
//			bos.flush();
//		} catch (Exception e) {
//		}
//		return bos.toByteArray();
//	}
//
//	/**
//	 * 将UTF-8字节数据转化为Unicode字符串
//	 * 
//	 * @param utf_data
//	 *            byte[] - UTF-8编码字节数组
//	 * @param len
//	 *            int - 字节数组长度
//	 * @return String - 变换后的Unicode编码字符串
//	 */
//	public static String UTF2Uni(byte[] utf_data, int len) {
//		StringBuffer unis = new StringBuffer();
//		char unic = 0;
//		int ptr = 0;
//		int cntBits = 0;
//		for (; ptr < len;) {
//			cntBits = getCntBits(utf_data[ptr]);
//			if (cntBits == -1) {
//				++ptr;
//				continue;
//			} else if (cntBits == 0) {
//				unic = UTFC2UniC(utf_data, ptr, cntBits);
//				++ptr;
//			} else {
//				unic = UTFC2UniC(utf_data, ptr, cntBits);
//				ptr += cntBits;
//			}
//			unis.append(unic);
//		}
//		return unis.toString();
//	}
//
//	/**
//	 * 将指定的UTF-8字节组合成一个Unicode编码字符
//	 * 
//	 * @param utf
//	 *            byte[] - UTF-8字节数组
//	 * @param sptr
//	 *            int - 编码字节起始位置
//	 * @param cntBits
//	 *            int - 编码字节数
//	 * @return char - 变换后的Unicode字符
//	 */
//	public static char UTFC2UniC(byte[] utf, int sptr, int cntBits) {
//		/*
//		 * Unicode <-> UTF-8 U-00000000 - U-0000007F: 0xxxxxxx U-00000080 -
//		 * U-000007FF: 110xxxxx 10xxxxxx U-00000800 - U-0000FFFF: 1110xxxx
//		 * 10xxxxxx 10xxxxxx U-00010000 - U-001FFFFF: 11110xxx 10xxxxxx 10xxxxxx
//		 * 10xxxxxx U-00200000 - U-03FFFFFF: 111110xx 10xxxxxx 10xxxxxx 10xxxxxx
//		 * 10xxxxxx U-04000000 - U-7FFFFFFF: 1111110x 10xxxxxx 10xxxxxx 10xxxxxx
//		 * 10xxxxxx 10xxxxxx
//		 */
//		int uniC = 0; // represent the unicode char
//		byte firstByte = utf[sptr];
//		int ptr = 0; // pointer 0 ~ 15
//		// resolve single byte UTF-8 encoding char
//		if (cntBits == 0)
//			return (char) firstByte;
//		// resolve the first byte
//		firstByte &= (1 << (7 - cntBits)) - 1;
//		// resolve multiple bytes UTF-8 encoding char(except the first byte)
//		for (int i = sptr + cntBits - 1; i > sptr; --i) {
//			byte utfb = utf[i];
//			uniC |= (utfb & 0x3f) << ptr;
//			ptr += 6;
//		}
//		uniC |= firstByte << ptr;
//		return (char) uniC;
//	}
//
//	/**
//	 * 根据给定字节计算UTF-8编码的一个字符所占字节数，UTF-8规则定义，字节标记只能为0或2~6
//	 * 
//	 * @param b
//	 * @return
//	 */
//	private static int getCntBits(byte b) {
//		int cnt = 0;
//		if (b == 0)
//			return -1;
//		for (int i = 7; i >= 0; --i) {
//			if (((b >> i) & 0x1) == 1)
//				++cnt;
//			else
//				break;
//		}
//		return (cnt > 6 || cnt == 1) ? -1 : cnt;
//	}
//
//	/**
//	 * 显示data内容
//	 * 
//	 * @param data_buf
//	 *            UTF-8的单词释义数组
//	 * @param data_length
//	 *            UTF-8的单词释义数组长度
//	 */
//	public static void display_data(byte[] data_buf, int data_length[]) {
//		// 将UTF-8byte字节数组转为当前环境字符并显示
//		// String tempString = UTF8Decode(data_buf, 0, data_length[0]);
//		String tempString = UTF2Uni(data_buf, data_length[0]);
//		// String tempString = new String(data_buf);
//		data_buf = null;
//		System.out.println(tempString);
//	}
//
//	/**
//	 * 从idx文件中搜索由word指定的单词，并保存相应的偏移和长度信息
//	 * 
//	 * @param word
//	 * @param data_poffset
//	 * @param data_plength
//	 * @return 是否搜索成功
//	 */
//	public static boolean search_word(String word, int[] data_poffset,
//			int[] data_plength) {
//		String wd[] = new String[1];
//		boolean temp = false;
//		int len[] = new int[1];
//		// 从idx文件中获取当前目标单词
//		// for (get_word(wd, data_poffset, data_plength); end; get_word(wd,
//		// data_poffset, data_plength))
//		// {
//		while (get_word(wd, data_poffset, data_plength, len)) {
//			// System.out.println("compared_word:"+wd[0]);
//			// if (wd[0].compareToIgnoreCase(word) == 0) //
//			// 比较字符串s1和s2，但不区分字母的大小写
//			if (strsEqualsIgnoreCase(wd[0], word) == 0) {
//				System.out.println("compared_word:" + word + " " + wd[0]);
//				temp = true;
//				break;
//			}
//		}
//		return temp;
//	}
//
//	/**
//	 * 从标准输入获取待查询的单词，控制台下为GBK字符，字典索引中的英文单词字母也是如此
//	 * 
//	 * @param max_len
//	 * @param count
//	 * @return
//	 */
//	public static String get_input(int max_len, int[] count) {
//		byte input_buf[] = new byte[max_len];
//		count[0] = 0;
//		String tempString[] = new String[1];
//		try {
//			count[0] = System.in.read(input_buf) - 2;// 返回实际读取到的字符数，减去2个控制字符
//			byte temp_buf[] = new byte[count[0]];
//			for (int i = 0; i < count[0]; i++) {
//				temp_buf[i] = input_buf[i];
//			}
//			tempString[0] = new String(temp_buf);
//		} catch (Exception e) {
//			System.out.println("Input error!");
//		}
//		System.out.println("Your input is:" + tempString[0]);
//		return tempString[0];
//	}
//
//	/**
//	 * 从标准输入获取待查询的单词，控制台下为GBK字符，字典索引中的英文单词字母也是如此
//	 * 
//	 * @param input_buf
//	 * @param count
//	 * @return
//	 */
//	public static byte[] get_input(byte[] input_buf, int[] count) {
//		try {
//			count[0] = System.in.read(input_buf) - 2;// 返回实际读取到的字符数，减去2个控制字符
//		} catch (Exception e) {
//			input_buf = null;
//			System.out.println("Input error!");
//		}
//		return input_buf;
//	}
//
//	/**
//	 * 缓存KEYS在idx中的偏移信息，以便加快search_word的搜索速度
//	 * 
//	 * @param idx_cache
//	 *            保存每个单字母单词对应的起始位置
//	 * @return
//	 */
//	public static void cache_idx(long[] idx_cache) {
//		int i;
//		long[] p = idx_cache;
//		int unused1[] = new int[1];
//		int unused2[] = new int[1];
//		try {
//			// 将文件内部的位置指针重新指向一个流（数据流/文件）的开头返回FILE指针当前位置，
//			// 然后重新遍历整个文件搜寻下一个字母开头的单词
//			isidx.reset();
//			STREAM_LOCAL = 0;
//			for (i = 0; i < MAX_KEYS; i++) {
//				// System.out.println("Start search_word：" + KEY[i]);
//				if (search_word(KEY[i], unused1, unused2))// 从idx文件中搜索由word指定的单词，并保存相应的偏移和长度信息
//				{
//					p[i] = STREAM_LOCAL; // 返回当前文件位置
//					// String tempString = Long.toString(STREAM_LOCAL);
//					// System.out.println(KEY[i] + "'s local is:" + tempString);
//					System.out.println(KEY[i] + "'s local is:" + STREAM_LOCAL
//							+ " offset:" + unused1[0] + "length:" + unused2[0]);
//				} else
//					p[i] = 0;
//			}
//			// isidx.reset();
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
//
//	/**
//	 * 定位由word指定的单词在idx文件中的大概偏移位置
//	 * 
//	 * @param word
//	 * @param idx_cache
//	 * @return
//	 */
//	public static long locate_idx(String word, long[] idx_cache) {
//		int i = 0;
//		int pre = 0;
//		String tempString = word.toLowerCase();
//		while (i < MAX_KEYS && KEY[i].charAt(0) < tempString.charAt(0)) {
//			pre = i;
//			++i;
//		}
//		if (tempString.charAt(0) == '-') {
//			pre = 0;
//		}
//		System.out.println("Now word's locate is:" + idx_cache[pre]);
//		return idx_cache[pre];
//	}
//
//	/**
//	 * 主要查询函数
//	 */
//	public static void consult() {
//		byte data[] = null;// 释义数据，UTF-8数据
//		long idx[] = new long[MAX_KEYS];// 26个字母孤立单词+"-"开头的后缀对应的索引缓冲
//		int offset[] = new int[1];
//		int length[] = new int[1];
//		System.out.println("Start cache_idx....！");
//		try {
//			System.out.println("Open files....!");
//			// 读取字典索引文件
//			isidx = new BufferedInputStream(new FileInputStream(idxfileString));
//			isidx.mark(isidx.available() + 1);
//			if (!isidx.markSupported()) {
//				System.out.println("This stream do not support mark....!");
//			}
//		} catch (Exception e) {
//			System.out.println("Open files error!");
//			e.printStackTrace();
//		}
//		cache_idx(idx);// 缓存KEYS在idx中的偏移信息，以便加快search_word的搜索速度
//		try {
//			isdict = new BufferedInputStream(
//					new FileInputStream(dictfileString));
//			isdict.mark(isdict.available() + 1);
//			if (!isdict.markSupported()) {
//				System.out.println("This stream do not support mark....!");
//			}
//		} catch (Exception e) {
//			System.out.println("Open files error!");
//			e.printStackTrace();
//		}
//		while (true) {
//			System.out.println("INPUT A WORD OR PHRASE: ");
//			int count[] = new int[1];
//			String word = get_input(MAX_WORD, count);
//			long skips1, skips2;
//			if (count[0] > 0)// 从控制台得到输入单词字符
//			{
//				try {
//					// 从文件开头跳到单词大致索引所在位置
//					// isidx.mark(0);
//					isidx.reset();
//					skips1 = locate_idx(word, idx);
//					// skips2 = isidx.skip(skips1);
//					skips2 = skipBytesFromStream(isidx, skips1);
//					System.out
//							.println("skips1:" + skips1 + " skips2:" + skips2);
//				} catch (Exception e) {
//					System.out.println("locate_idx run error");
//					e.printStackTrace();
//				}
//				if (search_word(word, offset, length)) {
//					data = get_data(offset, length);
//					display_data(data, length);
//					data = null;
//				} else
//					System.out.println("SORRY " + word + " CANNOT BE FOUND!\n");
//				System.out
//						.println("\n----------------------------------------\n\n");
//			} else
//				break;
//		}
//	}
//
//	/**
//	 * 不区分大小写比较两个字符串
//	 * 
//	 * @param s1
//	 * @param s2
//	 * @return
//	 */
//	public static int strsEqualsIgnoreCase(String s1, String s2) {
//		int n1 = s1.length(), n2 = s2.length();
//		for (int i1 = 0, i2 = 0; i1 < n1 && i2 < n2; i1++, i2++) {
//			char c1 = s1.charAt(i1);
//			char c2 = s2.charAt(i2);
//			if (c1 != c2) {
//				// 源字符串全部都转为大写字符串
//				c1 = Character.toUpperCase(c1);
//				c2 = Character.toUpperCase(c2);
//				if (c1 != c2) {
//					// 源字符串全部都转为小写字符串
//					c1 = Character.toLowerCase(c1);
//					c2 = Character.toLowerCase(c2);
//					if (c1 != c2) {
//						return c1 - c2;
//					}
//				}
//			}
//		}
//		return n1 - n2;// 如果其中一个或者两个String都比较完了还没有同样的char的话，那就return两个String的长度差距
//	}
//
//	/**
//	 * 重写了Inpustream 中的skip(long n) 方法，将数据流中起始的n 个字节跳过
//	 * 参考：http://blog.csdn.net/ranxiedao/article/details/7787342
//	 * 
//	 * @param inputStream
//	 * @param n
//	 * @return
//	 */
//	private static long skipBytesFromStream(InputStream inputStream, long n) {
//		long remaining = n; // SKIP_BUFFER_SIZE is used to determine the size of
//		// skipBuffer
//		int SKIP_BUFFER_SIZE = 2048; // skipBuffer is initialized in
//		// skip(long), if needed.
//		byte[] skipBuffer = null;
//		int nr = 0;
//		if (skipBuffer == null) {
//			skipBuffer = new byte[SKIP_BUFFER_SIZE];
//		}
//		byte[] localSkipBuffer = skipBuffer;
//		if (n <= 0) {
//			return 0;
//		}
//		while (remaining > 0) {
//			try {
//				nr = inputStream.read(localSkipBuffer, 0,
//						(int) Math.min(SKIP_BUFFER_SIZE, remaining));
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			if (nr < 0) {
//				break;
//			}
//			remaining -= nr;
//		}
//		return n - remaining;
//	}
//
//	/**
//	 * 主函数
//	 * 
//	 * @param args
//	 */
//	public static void main(String args[]) {
//		consult();
//		try {
//			isidx.close();
//			isdict.close();
//		} catch (Exception e) {
//			System.out.println("Close files error!");
//			e.printStackTrace();
//		}
//	}
//}
//
//
////如果要在windows平台下编译http://blog.chinaunix.net/uid-20454005-id-1675913.html文章中的程序代码最好保存为cpp文件以C++项目编译执行，而且strcasecmp函数应该换为stricmp函数，并且上面作者原来的程序是在linux平台下的，字符编码本身就是UTF8的不需要进行编码转换，但在windows平台下中文为gb232编码，就需要进行编码的转换，下面为需要添加修改上的字符编码转换后的程序。
//////UTF-8到GB2312的转换
////char* U2G(const char* utf8)
////{
////	int len = MultiByteToWideChar(CP_UTF8, 0, utf8, -1, NULL, 0);
////	wchar_t* wstr = new wchar_t[len+1];
////	memset(wstr, 0, len+1);
////	MultiByteToWideChar(CP_UTF8, 0, utf8, -1, wstr, len);
////	len = WideCharToMultiByte(CP_ACP, 0, wstr, -1, NULL, 0, NULL, NULL);
////	char* str = new char[len+1];
////	memset(str, 0, len+1);
////	WideCharToMultiByte(CP_ACP, 0, wstr, -1, str, len, NULL, NULL);
////	if(wstr) delete[] wstr;
////	return str;
////}
////
//////GB2312到UTF-8的转换
////char* G2U(const char* gb2312)
////{
////	int len = MultiByteToWideChar(CP_ACP, 0, gb2312, -1, NULL, 0);
////	wchar_t* wstr = new wchar_t[len+1];
////	memset(wstr, 0, len+1);
////	MultiByteToWideChar(CP_ACP, 0, gb2312, -1, wstr, len);
////	len = WideCharToMultiByte(CP_UTF8, 0, wstr, -1, NULL, 0, NULL, NULL);
////	char* str = new char[len+1];
////	memset(str, 0, len+1);
////	WideCharToMultiByte(CP_UTF8, 0, wstr, -1, str, len, NULL, NULL);
////	if(wstr) delete[] wstr;
////	return str;
////}
////
/////*
////* 显示data内容
////*/
////void display_data(char *data_buf, unsigned int data_length)
////{
////	fwrite(data_buf,data_length,1,stdout);
////	char *data=(char *)malloc(data_length);
////	memcpy(data,data_buf,data_length);
////	char *p=U2G(data_buf);
////	printf("%s\n",p);
////	free(data);
////	delete p;
// //}