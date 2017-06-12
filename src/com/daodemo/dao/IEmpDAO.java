package com.daodemo.dao;

import java.util.List;

import com.daodemo.vo.Emp;

//CRUD是指在做计算处理时的增加(Create)、读取查询(Retrieve)、更新(Update)和删除(Delete)几个单词的首字母简写。
//增 insert
//删 delete
//改 update
//查 select
//DAO只完成增删改查，虽然可以1-n，n-n，1-1关联，模糊、动态、子查询都可以。但是无论多么复杂的查询，dao只是封装增删改查。至于增删查改如何去实现一个功能，dao是不管的。
public interface IEmpDAO {
	// create
	public boolean insert(Emp emp) throws Exception;

	// query/find/retrieve by Id
	public Emp selectById(int empId) throws Exception;

	public List<Emp> selectAll() throws Exception;

	public void update(Emp emp) throws Exception;

	public void delete(int empId) throws Exception;
}