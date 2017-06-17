package com.ormlitedao.daoimpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ormlitedao.bean.Word;

//public class CellPhoneDao extends BaseDaoImpl<CellPhone, String>  {
public class WordDaoImpl extends BaseDaoImpl<Word, String>
//implements Dao<Word, String>
{
	private Dao<Word, String> wordDao;

	public WordDaoImpl(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, Word.class);
		
		//Setup our database and DAOs
		wordDao = DaoManager.createDao(connectionSource, Word.class);
		// create table
		TableUtils.createTableIfNotExists(connectionSource, Word.class);
	}
	
	public Vector<String> getTableTitle() {
		Vector<String> titleVector = new Vector<String>(); // headVector/column Names/表头集合
		try {
//			GenericRawResults<String[]> rawResults = wordDao.queryRaw("select * from " + wordDao.getTableName());
//			if (rawResults == null) {
//				System.out.println("rawResults == null");
//			} else {
//				//List<String[]> results = rawResults.getResults();
//				String[] columnNames = rawResults.getColumnNames();
//				titleVector =  new Vector<String>(Arrays.asList(columnNames));// array -> vector
//			}
			titleVector.addElement("id");
			titleVector.addElement(Word.FIELD_NAME_FREQUENCY);
			titleVector.addElement(Word.FIELD_NAME_SPELLING);
			titleVector.addElement(Word.FIELD_NAME_DJ);
			titleVector.addElement(Word.FIELD_NAME_KK);
			titleVector.addElement(Word.FIELD_NAME_LEVEL);
			titleVector.addElement(Word.FIELD_NAME_POS);
			titleVector.addElement(Word.FIELD_NAME_MEANINGS);
			titleVector.addElement(Word.FIELD_NAME_SENTS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		titleVector.addElement("operate");//todo
		return titleVector;
	}

	public Vector<Vector<Object>> selectAll2Vector() {
		Vector<Vector<Object>> cellsVector = new Vector<Vector<Object>>(); // rowsVector/rows data/数据体集合
		try {
			List<Word> wordList = wordDao.queryForAll();
			for (Word dbWord : wordList) {
				// curRow.add(rs.getString(i));//ResultSetMetaData rsmd
				Vector<Object> curRow = new Vector<Object>();
				curRow.addElement(dbWord.getId());
				curRow.addElement(dbWord.getFrequency());
				curRow.addElement(dbWord.getSpelling());
				curRow.addElement(dbWord.getPhoneticDJ());
				curRow.addElement(dbWord.getPhoneticKK());
				curRow.addElement(dbWord.getLevel());
				curRow.addElement(dbWord.getPartsOfSpeech());
				curRow.addElement(dbWord.getMeanings());
				curRow.addElement(dbWord.getSents());
				cellsVector.addElement(curRow); // rows.add(curRow);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cellsVector;
	}

}
