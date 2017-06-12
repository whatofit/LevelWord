package com.genericsdao.daoimp;

import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import model.Word;

import com.genericsdao.bean.User;
import com.genericsdao.dao.WordDao;
import com.genericsdao.dbc.DBHelper;

public class WordDaoImpl extends BaseDaoImpl<Word> implements WordDao {
	private Class<?> EntityClass;

	private String sql;

	private PreparedStatement statement;

	private ResultSet rs;

	private List<User> list;

	public WordDaoImpl() {
		ParameterizedType type = (ParameterizedType) getClass()
				.getGenericSuperclass();
		EntityClass = (Class<?>) type.getActualTypeArguments()[0];
	}

	@Override
	public Vector getTableTitle() {
		Vector titleVector = new Vector(); // headVector/column Names/表头集合
		sql = "select * from " + EntityClass.getSimpleName() + " where 1=2";
		try {
			statement = DBHelper.getPreparedStatement(sql);
			ResultSetMetaData rsmd = statement.executeQuery().getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
				titleVector.addElement(rsmd.getColumnName(i));// columnNames.add(rsmd.getColumnName(i));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		titleVector.addElement("operate");
		return titleVector;
	}

	@Override
	public Vector selectAll() {
		Vector cellsVector = new Vector(); // rowsVector/rows data/数据体集合
		sql = "select * from " + EntityClass.getSimpleName() + ";";
		try {
			statement = DBHelper.getPreparedStatement(sql);
			ResultSet rs = statement.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			if (rs != null) {
				while (rs.next()) {
					Vector curRow = new Vector();
					for (int i = 1; i <= columnCount; ++i) {
						curRow.addElement(rs.getString(i));// curRow.add(rs.getString(i));
					}
					cellsVector.addElement(curRow); // rows.add(curRow);
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return cellsVector;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
