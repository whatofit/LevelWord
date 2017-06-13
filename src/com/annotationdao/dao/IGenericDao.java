package com.annotationdao.dao;

import java.util.List;
import java.util.Map;

public interface IGenericDao<T> {
	// 增删查改，分页，通过ID查找实体
	public int create(T t) throws Exception;// 创建表格

	public void save(T t) throws Exception;
	
	public void delete(Object id,Class<T> clazz) throws Exception;
	
	public void update(T t) throws Exception;
	
	public T get(Object id,Class<T> clazz) throws Exception;
	
	/**
	 * 根据条件查询
	 * @param sqlWhereMap key：条件字段名 value：条件字段值
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public List<T> findAllByConditions(Map<String,Object> sqlWhereMap,Class<T> clazz) throws Exception;
	
}