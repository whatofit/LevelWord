package level;

import java.util.List;
import java.util.Vector;

import model.Sent;
import model.Word;

public class Xml2SqlitePOSsLine extends XmlWordIntoSqlite  {
	public Xml2SqlitePOSsLine() {
		super();
	}

	//每一个单词,插入词性个数条记录,词频/单词/音标等,都是重复的
	public void word2Vector(Word word) {
		List<Sent> sents = word.getSents();
		String sentence = "";
		for (int i = 0; i < sents.size(); i++) {
			Sent sent = sents.get(i);
			if (!sentence.isEmpty()) {
				//sentence = sentence + "||";
			}
			sentence = sentence + (i+1) + ". " + sent.getOrig() + "/"+ sent.getTrans() + " ";
		}
		List<String> partsOfSpeech = word.getPartsOfSpeech();
		List<String> meaning = word.getMeaning();
		for (int i = 0; i < partsOfSpeech.size(); i++) {
			Vector vecWord = new Vector();
			vecWord.add(word.getWordFrequency());
			vecWord.add(word.getKey());
			vecWord.add(word.getPs());
			vecWord.add(word.getPs2());
			vecWord.add(null);
			vecWord.add(partsOfSpeech.get(i));
			vecWord.add(meaning.get(i));
			if (i== 0) {
				vecWord.add(sentence);
			}else{
				vecWord.add(null);
			}
			vecWords.add(vecWord);
		}
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Xml2SqlitePOSsLine levelSqlite = new Xml2SqlitePOSsLine();
			levelSqlite.xmlFiles2Words();
			String sqlCreate = "CREATE TABLE IF NOT EXISTS LevelWordTab (frequency,spelling,DJ,KK,level,partsOfSpeech,Meaning,sents);";
			String sqlInsert = "INSERT INTO LevelWordTab VALUES(?,?,?,?,?,?,?,?)";
			levelSqlite.doInsert2DB(sqlCreate, sqlInsert);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

 }
