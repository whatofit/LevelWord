package com.genericsdao.dao;

//ͨ通用Dao操作
//通用表中需要进行的操作.

//在这个类中，引入了泛型T，代表需要返回或者操作的java对象是T
public interface BaseDao<T> {
	void create(T t);//创建表格
	void insert(T t); 
	void delete(T t);
	void update(T t);
	T select(T t);
}
