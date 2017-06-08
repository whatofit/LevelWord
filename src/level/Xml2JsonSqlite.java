package level;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;

import db.DBUtil;
import json.FastJsonUtil;
import model.Word;
import util.Utils;
import util.XlsUtil;

public class Xml2JsonSqlite {
//	private Connection Conn = null;
	private WordVisitor wordParser = new WordVisitor();
	private static int mRow = 0;//单词的词频/已生成的excel行数
	// 1.读xml单词文件
	// 2.解析成JavaBean/Model
	// 3.写入文件/数据库
	public Xml2JsonSqlite() {
		//Conn = DBUtil.getConn();
	}
	public void traversalDocumentByVisitor(String xmlWordFile) {
		try {
			wordParser.getDocument(xmlWordFile).accept(wordParser);
		} catch (Exception ex) {
			Utils.writerFileTest(errFile, xmlWordFile);
		}
	}
	
	

	public void readFileTest(String filename) {
		String path = "./vocabulary_ciba";
		String file = "";

		// StringBuffer bs = new StringBuffer();
		try {
			FileInputStream f = new FileInputStream(filename);
			// DataInputStream dr = new DataInputStream(f);
			BufferedReader dr = new BufferedReader(new InputStreamReader(f));
			String line = dr.readLine();
			while (line != null) {
				String[] arr = line.trim().split("\t");
				// line = line.trim().replaceFirst("\t", "-");
				file = arr[0] + "-" + arr[1] + ".xml";
				System.out.println(file);
				traversalDocumentByVisitor(path + "/" + file);
				Word word = wordParser.getWord();
				word.setWordFrequency(arr[0]);
				
				// System.out.println(word.toString());
				// System.out.println(word.toStringList(arr[0]));
				
				String wordJson = FastJsonUtil.obj2json(word);
				//insert sqlite
				String insertSql = "";
				int nCount = DBUtil.ExecuteUpdate(insertSql);
				//wordJson
				line = dr.readLine();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Utils.deleteFile(errFile);
			Utils.deleteFile("LevelDict.xls");
			//book = XlsUtil.createXLS("LevelDict.xls", "vocabulary", 0);
			//sheet = book.getSheet(0);
			String filename = "./src/vocabulary.txt";
			Xml2JsonSqlite levelSqlite = new Xml2JsonSqlite();
			levelSqlite.readFileTest(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String errFile = "ErrFile.txt";

}
