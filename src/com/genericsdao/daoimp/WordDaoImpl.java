package com.genericsdao.daoimp;

import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
