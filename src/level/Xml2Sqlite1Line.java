package level;

import java.util.List;
import java.util.Vector;

import model.Sent;
import model.Word;

public class Xml2Sqlite1Line extends XmlWordIntoSqlite {

	public Xml2Sqlite1Line() {
		super();
	}

	// 所有词性/词义在一行
	public void word2Vector(Word word) {
		Vector vecWord = new Vector();
		vecWord.add(word.getWordFrequency());
		vecWord.add(word.getKey());
		// vecWord.add(wordJson);
		// frequency,spelling,DJ,KK,level,posMeanings,sents);";
		vecWord.add(word.getPs());
		vecWord.add(word.getPs2());
		vecWord.add(null);

		List<String> partsOfSpeech = word.getPartsOfSpeech();
		List<String> meaning = word.getMeaning();
		String posMeanings = "";
		for (int i = 0; i < partsOfSpeech.size(); i++) {
			String pos = partsOfSpeech.get(i);
			String mean = meaning.get(i);
			if (!posMeanings.isEmpty()) {
				// posMeanings = posMeanings + "||";
			}
			posMeanings = posMeanings + pos + mean;
		}
		vecWord.add(posMeanings);

		List<Sent> sents = word.getSents();
		String sentence = "";
		for (int i = 0; i < sents.size(); i++) {
			Sent sent = sents.get(i);
			if (!sentence.isEmpty()) {
				// sentence = sentence + "||";
			}
			sentence = sentence + (i + 1) + ". " + sent.getOrig() + "/"
					+ sent.getTrans() + " ";
		}
		vecWord.add(sentence);
		vecWords.add(vecWord);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Xml2Sqlite1Line levelSqlite = new Xml2Sqlite1Line();
			levelSqlite.xmlFiles2Words();
			String sqlCreate = "CREATE TABLE IF NOT EXISTS LevelWordTab (frequency,spelling,DJ,KK,level,posMeanings,sents);";
			String sqlInsert = "INSERT INTO LevelWordTab VALUES(?,?,?,?,?,?,?)";
			levelSqlite.doInsert2DB(sqlCreate, sqlInsert);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
