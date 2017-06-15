package level;

import java.util.List;
import java.util.Vector;

import com.genericsdao.bean.Word;

import model.XmlSent;
import model.XmlWord;

public class Xml2SqlitePOSsLine extends XmlWordIntoSqlite {
	public Xml2SqlitePOSsLine() {
		super();
	}

	// 每一个单词,插入词性个数条记录,词频/单词/音标等,都是重复的
	public void word2Vector(String line) {
		XmlWord word = wordParser.getWord(line);
		List<XmlSent> sents = word.getSents();
		String sentence = "";
		for (int i = 0; i < sents.size(); i++) {
			XmlSent sent = sents.get(i);
			if (!sentence.isEmpty()) {
				// sentence = sentence + "||";
			}
			sentence = sentence + (i + 1) + ". " + sent.getOrig() + "/"
					+ sent.getTrans() + " ";
		}
		List<String> partsOfSpeech = word.getPartsOfSpeech();
		List<String> meaning = word.getMeaning();
		for (int i = 0; i < partsOfSpeech.size(); i++) {
			Word dbWord = new Word();
			dbWord.setId(vecWords.size()+"");
			dbWord.setFrequency(word.getWordFrequency());
			dbWord.setSpelling(word.getKey());
			dbWord.setPhoneticDJ(word.getPs());
			dbWord.setPhoneticKK(word.getPs2());
			dbWord.setLevel(null);
			dbWord.setPartsOfSpeech(partsOfSpeech.get(i));
			dbWord.setMeanings(meaning.get(i));
			if (i == 0) {
				dbWord.setSents(sentence);
			} else {
				dbWord.setSents(null);
			}
			vecWords.add(dbWord);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Xml2SqlitePOSsLine levelSqlite = new Xml2SqlitePOSsLine();
			levelSqlite.xmlFiles2Words();
			levelSqlite.doInsert2DB();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
