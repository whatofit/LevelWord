package com.genericsdao.dao;

import java.util.Vector;

import model.Word;

//Word Dao接口额外功能
public interface WordDao extends BaseDao<Word> {
	Vector getTableTitle();
	Vector selectAll();
}
