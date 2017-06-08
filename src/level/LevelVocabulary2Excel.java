//package level;
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.InputStreamReader;
//
//import jxl.write.WritableSheet;
//import jxl.write.WritableWorkbook;
//import model.Word;
//import util.Utils;
//import util.XlsUtil;
//
//public class LevelVocabulary2Excel {
//
//	private WordVisitor wordParser = new WordVisitor();
//	private static WritableWorkbook book = null;
//	private static WritableSheet sheet = null;
//	private static int mRow = 0;//单词的词频/已生成的excel行数
//	// 1.读xml单词文件
//	// 2.解析成JavaBean/Model
//	// 3.写入文件/数据库
//
//	public void traversalDocumentByVisitor(String xmlWordFile) {
//		try {
//			wordParser.getDocument(xmlWordFile).accept(wordParser);
//		} catch (Exception ex) {
//			Utils.writerFileTest(errFile, xmlWordFile);
//		}
//	}
//
//	public void readFileTest(String filename) {
//		String path = "./vocabulary_ciba";
//		String file = "";
//
//		// StringBuffer bs = new StringBuffer();
//		try {
//			FileInputStream f = new FileInputStream(filename);
//			// DataInputStream dr = new DataInputStream(f);
//			BufferedReader dr = new BufferedReader(new InputStreamReader(f));
//			String line = dr.readLine();
//			while (line != null) {
//				String[] arr = line.trim().split("\t");
//				// line = line.trim().replaceFirst("\t", "-");
//				file = arr[0] + "-" + arr[1] + ".xml";
//				System.out.println(file);
//				traversalDocumentByVisitor(path + "/" + file);
//				Word word = wordParser.getWord();
//				word.setWordFrequency(arr[0]);
//				// System.out.println(word.toString());
//				// System.out.println(word.toStringList(arr[0]));
//				
//				XlsUtil.addXLS(sheet, word, mRow);
//				if (word.getPartsOfSpeech().size() == 0) {
//					mRow+=1;	//累加写入excel下一个单词的起始位置
//				} else {
//					mRow += word.getPartsOfSpeech().size();
//				}
//				//sheet = XlsUtil.addXLS(sheet, word.toStringList(arr[0]),
//				//		Integer.parseInt(arr[0])-1);
//				line = dr.readLine();
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		try {
//			Utils.deleteFile(errFile);
//			Utils.deleteFile("LevelDict.xls");
//			//book = XlsUtil.createXLS("LevelDict.xls", "vocabulary", 0);
//			//sheet = book.getSheet(0);
//			book  = XlsUtil.createXLS("LevelDict.xls");
//			sheet = XlsUtil.createSheet(book,"vocabulary", 0);
//			
//			// // 打开文件
//			// book = Workbook.createWorkbook(new File("LevelDict.xls"));
//			// // 生成名为“第一页”的工作表，参数0表示这是第一页
//			// sheet = book.createSheet("vocabulary", 0);
//			String filename = "./src/vocabulary.txt";
//			LevelVocabulary2Excel levelVoc = new LevelVocabulary2Excel();
//			levelVoc.readFileTest(filename);
//			XlsUtil.closeXLS(book);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	private static String errFile = "ErrFile.txt";
//
//}
