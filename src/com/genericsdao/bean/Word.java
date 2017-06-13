package com.genericsdao.bean;

//表字段
public class Word {
	public String id;
	public String frequency;
	public String spelling;
	public String phoneticDJ;
	public String phoneticKK;
	public String level;
	public String partsOfSpeech;
	public String meanings;
	public String sents;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getSpelling() {
		return spelling;
	}

	public void setSpelling(String spelling) {
		this.spelling = spelling;
	}

	public String getPhoneticDJ() {
		return phoneticDJ;
	}

	public void setPhoneticDJ(String phoneticDJ) {
		this.phoneticDJ = phoneticDJ;
	}

	public String getPhoneticKK() {
		return phoneticKK;
	}

	public void setPhoneticKK(String phoneticKK) {
		this.phoneticKK = phoneticKK;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getPartsOfSpeech() {
		return partsOfSpeech;
	}

	public void setPartsOfSpeech(String partsOfSpeech) {
		this.partsOfSpeech = partsOfSpeech;
	}

	public String getMeanings() {
		return meanings;
	}

	public void setMeanings(String meanings) {
		this.meanings = meanings;
	}

	public String getSents() {
		return sents;
	}

	public void setSents(String sents) {
		this.sents = sents;
	}
}
