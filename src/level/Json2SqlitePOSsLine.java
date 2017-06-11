package level;

import model.JsonWord;

public class Json2SqlitePOSsLine extends XmlWordIntoSqlite {
	public Json2SqlitePOSsLine() {
		super();
	}

	// 每一个单词,插入词性个数条记录,词频/单词/音标等,都是重复的
	public void word2Vector(String line) {
		JsonWord word = wordParser.getJsonWord(line);
		word.add2Vector(vecWords,word);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Json2SqlitePOSsLine levelSqlite = new Json2SqlitePOSsLine();
			levelSqlite.xmlFiles2Words();
			String sqlCreate = "CREATE TABLE IF NOT EXISTS LevelWordTab (frequency,spelling,DJ,KK,level,partsOfSpeech,Meaning,sents);";
			String sqlInsert = "INSERT INTO LevelWordTab VALUES(?,?,?,?,?,?,?,?)";
			levelSqlite.doInsert2DB(sqlCreate, sqlInsert);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
