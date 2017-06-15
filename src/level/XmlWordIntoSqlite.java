package level;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import util.Utils;

import com.genericsdao.daoimp.WordDaoImpl;

/**
 * implementation
 * 
 * @author Dave
 * 
 * @version 1.0 2017/06/10
 */
public abstract class XmlWordIntoSqlite {
	protected final String mWordListFile = "./src/vocabulary.txt";
	// protected final String mXmlFileFolder = "./vocabulary_ciba";
	protected static String mErrFileList = "ErrFile.txt";
	protected XmlWordVisitor wordParser = new XmlWordVisitor();
	protected Vector vecWords = new Vector();

	// 1.读xml单词文件
	// 2.解析成JavaBean/Model
	// 3.写入文件/数据库
	public XmlWordIntoSqlite() {
		Utils.deleteFile(mErrFileList);
	}

	// /**
	// *
	// */
	// public void traversalDocumentByVisitor(String xmlWordFile) {
	// try {
	// wordParser.getDocument(xmlWordFile).accept(wordParser);
	// } catch (Exception ex) {
	// Utils.writerFileTest(mErrFileList, xmlWordFile);
	// }
	// }

	// 根据filename中单词的顺序,读取vocabulary_ciba文件夹下的xml文件列表
	public void xmlFiles2Words() {
		try {
			FileInputStream fis = new FileInputStream(mWordListFile);
			// DataInputStream dr = new DataInputStream(f);
			BufferedReader dr = new BufferedReader(new InputStreamReader(fis));
			String line = dr.readLine();
			while (line != null) {
				word2Vector(line);
				line = dr.readLine();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 子类中重写本函数
	public abstract void word2Vector(String line);
/*
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
*/

	public int doInsert2DB() {
		WordDaoImpl wordDao = new WordDaoImpl();
		int nCount = wordDao.create();
		if (nCount != 1) {
			System.out.println("Xml2JsonSqlite mSqlCreate nCount=" + nCount);
		}
		int affectRowCount = wordDao.insert(vecWords);
		System.out.println("affectRowCount=" + affectRowCount);
		return affectRowCount;
	}

} // End of Class XmlWordIntoSqlite
