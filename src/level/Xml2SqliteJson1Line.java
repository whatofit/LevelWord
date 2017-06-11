package level;

import java.util.Vector;

import json.FastJsonUtil;
import model.Word;

public class Xml2SqliteJson1Line extends XmlWordIntoSqlite {

	public Xml2SqliteJson1Line() {
		super();
	}

	// 所有词性/词义在一行的json中
	public void word2Vector(String line) {
		Word word = wordParser.getWord(line);
		String wordJson = FastJsonUtil.obj2json(word);
		Vector vecWord = new Vector();
		vecWord.add(word.getWordFrequency());
		vecWord.add(word.getKey());
		vecWord.add(wordJson);
		vecWords.add(vecWord);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Xml2SqliteJson1Line levelSqlite = new Xml2SqliteJson1Line();
			levelSqlite.xmlFiles2Words();
			String sqlCreate = "CREATE TABLE IF NOT EXISTS LevelWordTab (frequency,spelling,wordMore);";
			String sqlInsert = "INSERT INTO LevelWordTab VALUES(?,?,?)";
			levelSqlite.doInsert2DB(sqlCreate, sqlInsert);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
