package level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import model.Word;
import util.Utils;
import db.DBUtil;

/**
 * implementation
 *
 * @author Dave
 * 
 * @version 1.0 2017/06/10
 */
public class XmlWordIntoSqlite {
	protected final String mWordListFile = "./src/vocabulary.txt";
	protected final String mXmlFileFolder = "./vocabulary_ciba";
	protected static String mErrFileList = "ErrFile.txt";
	protected XmlWordVisitor wordParser = new XmlWordVisitor();
	protected Vector vecWords = new Vector();
	protected String mSqlCreate = "";
	protected String mSqlInsert = "";

	// 1.读xml单词文件
	// 2.解析成JavaBean/Model
	// 3.写入文件/数据库
	public XmlWordIntoSqlite() {
		Utils.deleteFile(mErrFileList);
	}

	/**
	 * 
	 */
	public void traversalDocumentByVisitor(String xmlWordFile) {
		try {
			wordParser.getDocument(xmlWordFile).accept(wordParser);
		} catch (Exception ex) {
			Utils.writerFileTest(mErrFileList, xmlWordFile);
		}
	}

	// 根据filename中单词的顺序,读取vocabulary_ciba文件夹下的xml文件列表
	public void xmlFiles2Words() {
		try {
			FileInputStream fis = new FileInputStream(mWordListFile);
			// DataInputStream dr = new DataInputStream(f);
			BufferedReader dr = new BufferedReader(new InputStreamReader(fis));
			String line = dr.readLine();
			while (line != null) {
				String[] arr = line.trim().split("\t");
				// line = line.trim().replaceFirst("\t", "-");
				String xmlFileName = arr[0] + "-" + arr[1] + ".xml";
				String xmlWordFile = mXmlFileFolder + File.separator + xmlFileName;
				System.out.println(xmlWordFile);
				Word word;
				try {
					word = wordParser.getWord(xmlWordFile);
				} catch (Exception ex) {
					word = new Word();
					word.setKey(arr[1]);
					Utils.writerFileTest(mErrFileList, xmlWordFile);
				}
				word.setWordFrequency(arr[0]);
				word2Vector(word);
				line = dr.readLine();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//子类中重写本函数
	public void word2Vector(Word word) {
	}

	public int doInsert2DB(String sqlCreate, String sqlInsert) {
		mSqlCreate = sqlCreate;
		mSqlInsert = sqlInsert;
		DBUtil dbMgr = new DBUtil();
		int nCount = dbMgr.executeUpdate(mSqlCreate);
		if (nCount != 1) {
			System.out.println("Xml2JsonSqlite mSqlCreate nCount=" + nCount);
		}
		int affectRowCount = dbMgr.executeBatchInsert(mSqlInsert, vecWords);
		System.out.println("affectRowCount=" + affectRowCount);
		dbMgr.closeConn();
		return affectRowCount;
	}

} // End of Class XmlWordIntoSqlite
