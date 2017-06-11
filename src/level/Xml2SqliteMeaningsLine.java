package level;

import java.util.List;
import java.util.Vector;

import model.Sent;
import model.Word;

public class Xml2SqliteMeaningsLine extends XmlWordIntoSqlite {
	protected final String regexSemicolon = "[；;]";// 以中文分号或英文分号分割

	public Xml2SqliteMeaningsLine() {
		super();
	}

	// 每一个单词,以中文分号；分割词义,插入中文词义个数条记录,词频/单词/音标等,都是重复的
	public void word2Vector(String line) {
		Word word = wordParser.getWord(line);
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
		List<String> partsOfSpeech = word.getPartsOfSpeech();
		List<String> meaning = word.getMeaning();
		for (int i = 0; i < meaning.size(); i++) {
			// String meanings =
			// "采用（某种方式）；穿着，带着；（表示位置）在…里面，（表示领域，范围）在…以内；（表示品质、能力等）在…之中；";
			String meanings = meaning.get(i);
			String[] acceptation = meanings.split(regexSemicolon);
			for (int j = 0; j < acceptation.length; j++) {
				// System.out.println("meanings,regex="+
				// Arrays.toString(acceptation));
				// System.out.println("meanings,regex="+ acceptation[i]);
				Vector vecWord = new Vector();
				vecWord.add(word.getWordFrequency());
				vecWord.add(word.getKey());
				vecWord.add(word.getPs());
				vecWord.add(word.getPs2());
				vecWord.add(null);
				vecWord.add(partsOfSpeech.get(i));
				vecWord.add(acceptation[j]);
				if (i == 0 && j == 0) {
					vecWord.add(sentence);// 例句只放在第一条记录里
				} else {
					vecWord.add(null);
				}
				vecWords.add(vecWord);
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Xml2SqliteMeaningsLine levelSqlite = new Xml2SqliteMeaningsLine();
			levelSqlite.xmlFiles2Words();
			String sqlCreate = "CREATE TABLE IF NOT EXISTS LevelWordTab (frequency,spelling,DJ,KK,level,partsOfSpeech,Meaning,sents);";
			String sqlInsert = "INSERT INTO LevelWordTab VALUES(?,?,?,?,?,?,?,?)";
			levelSqlite.doInsert2DB(sqlCreate, sqlInsert);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
