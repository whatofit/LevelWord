package level;

import java.util.Vector;

import org.apache.commons.lang3.StringEscapeUtils;

import com.genericsdao.bean.Word;

import json.FastJsonUtil;
import model.XmlWord;

public class Xml2SqliteJson1Line extends XmlWordIntoSqlite {

	public Xml2SqliteJson1Line() {
		super();
	}

	// 所有词性/词义在一行的json中
	public void word2Vector(String line) {
		XmlWord xmlWord = wordParser.getWord(line);
		String wordJson = FastJsonUtil.obj2json(xmlWord);
		Word dbWord = new Word();
		dbWord.setId(vecWords.size()+"");
		dbWord.setFrequency(xmlWord.getWordFrequency());
		dbWord.setSpelling(xmlWord.getKey());// 单词
		dbWord.setPhoneticDJ(null);// 英语音标
		dbWord.setPhoneticKK(null); // 美语音标
		dbWord.setLevel(null);
		dbWord.setPartsOfSpeech(null);// 词性
		dbWord.setMeanings(null);// 词义列表
		dbWord.setSents(wordJson);// 本词性对应的所有字段
		vecWords.add(dbWord);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Xml2SqliteJson1Line levelSqlite = new Xml2SqliteJson1Line();
			levelSqlite.xmlFiles2Words();
			levelSqlite.doInsert2DB();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
