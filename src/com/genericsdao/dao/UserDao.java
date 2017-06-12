package com.genericsdao.dao;

import java.util.List;

import com.genericsdao.bean.User;

//User Dao接口额外功能
public interface UserDao extends BaseDao<User> {
	//查询User表的所有信息
	List<User> findAll();	//User表比BaseDao额外的功能操作
}
