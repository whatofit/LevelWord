package level.ormlitedao;

import org.apache.commons.lang3.StringUtils;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Example account object that is persisted to disk by the DAO and other example
 * classes.
 */
@DatabaseTable(tableName = "levelWords")
public class Word {
	// for QueryBuilder to be able to find the fields
	public static final String FIELD_NAME_FREQUENCY = "frequency";
	public static final String FIELD_NAME_SPELLING = "spelling";
	public static final String FIELD_NAME_DJ = "phoneticDJ";
	public static final String FIELD_NAME_KK = "phoneticKK";
	public static final String FIELD_NAME_LEVEL = "level";
	public static final String FIELD_NAME_POS = "partsOfSpeech";
	public static final String FIELD_NAME_MEANINGS = "meanings";
	public static final String FIELD_NAME_SENTS = "sents";

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(columnName = FIELD_NAME_FREQUENCY)
	private String frequency;

	@DatabaseField(columnName = FIELD_NAME_SPELLING, canBeNull = false)
	private String spelling;

	@DatabaseField(columnName = FIELD_NAME_DJ)
	private String phoneticDJ;

	@DatabaseField(columnName = FIELD_NAME_KK)
	private String phoneticKK;

	@DatabaseField(columnName = FIELD_NAME_LEVEL)
	private String level;

	@DatabaseField(columnName = FIELD_NAME_POS)
	private String partsOfSpeech;

	@DatabaseField(columnName = FIELD_NAME_MEANINGS)
	private String meanings;

	@DatabaseField(columnName = FIELD_NAME_SENTS)
	private String sents;

	public Word() {
		// all persisted classes must define a no-arg constructor with at least
		// package visibility
	}

	public Word(String spelling) {
		this.spelling = spelling;
	}

	public Word(String frequency, String spelling) {
		this.frequency = frequency;
		this.spelling = spelling;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	@Override
	public int hashCode() {
		String key = spelling;
		if (partsOfSpeech != null) {
			key = key + partsOfSpeech;
		}
		return key.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == null || other.getClass() != getClass()) {
			return false;
		}
		Word oWord = (Word) other;
		if (spelling.equals(oWord.spelling)) {
			if (StringUtils.isBlank(partsOfSpeech)
					&& StringUtils.isBlank(oWord.partsOfSpeech)) {
				return true;
			} else {
				if (StringUtils.equals(partsOfSpeech, oWord.partsOfSpeech)) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "id=" + id + ",frequency=" + frequency + ",spelling" + spelling
				+ ", partsOfSpeech=" + partsOfSpeech;
	}
}
