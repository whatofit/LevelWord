package com.genericsdao.dao;

import java.util.Collection;
import java.util.List;

//ͨ通用Dao操作

//通用表中需要进行的操作.
//在这个类中，引入了泛型T，代表需要返回或者操作的java对象是T

//IGenericDAO
public interface IBaseDao<T> {
    // 增删查改，分页，通过ID查找实体
    public int create();// 创建表格

    /**
     * 通用类型的保存操作
     * 
     * @param Obj
     */
    public void insert(T t);// add

    /**
     * 持久化多个实体。
     * 
     * @param entities
     *            处于临时状态的实体的集合。
     * @return
     */
    public int insert(Collection<T> entities);

    /**
     * 通用类型的删除操作
     * 
     * @param obj
     */
    public void delete(T t);

    // public void delete(Serializable id);
    /**
     * 批量删除
     * 
     * @param entity
     * @param propertyNames
     * @param values
     */
    // public void bulkDelete(Class<T> clazz, String[] propertyNames, Object[]
    // values);

    /**
     * 通过类型的更新操作
     * 
     * @param obj
     */
    public void update(T t);

    /**
     * 更新多个实体。
     * 
     * @param entities
     *            处于持久化状态的实体的集合。
     */
    // public void update(Collection<T> entities);

    /**
     * 使用SQL语句直接增加、更新、删除实体
     * 
     * @param queryString
     *            SQL语句
     * @return
     */
    // public int bulkUpdate(String queryString);

    /**
     * 使用带参数SQL语句直接增加、更新、删除实体
     * 
     * @param queryString
     *            SQL语句
     * @param values
     *            SQL语句参数
     * @return
     */
    // public int bulkUpdate(String queryString, Object... values);

    /**
     * 持久化或者更新实体。
     * 
     * @param entity
     *            处于临时或者持久化状态的实体。
     */
    // public void createOrUpdate(T entity);

    /**
     * 持久化或者更新多个实体。
     * 
     * @param entities
     *            处于临时或者持久化状态的实体的集合。
     */
    // public void createOrUpdate(Collection<T> entities);

    // 通过ID查询/获取操作
    public T select(T t);

    /**
     * 通过ID查询操作
     * 
     * @param id
     * @return
     */
    // public T selectById(Serializable id);
    /**
     * 查询所有操作
     * 
     * @return
     */
    public List<T> selectAll();

    /**
     * 查询记录总条数(可为生成Id用)
     * 
     * @return
     */
    public int selectTotalCount();

    /**
     * 使用SQL语句检索数据
     * 
     * @param queryString
     *            SQL语句
     * @return
     */
    // public List<?> find(String queryString);

    /**
     * 使用带参数的SQL语句检索数据
     * 
     * @param queryString
     *            SQL语句
     * @param values
     *            SQL语句参数
     * @return
     */
    // public List<?> find(String queryString, Object... values);

    /**
     * Press the SQL Query object list .
     * 
     * @param sql
     *            sqlStatement
     * @param values
     *            Number of variable parameters
     */
    // public List<T> find(String sql, Object... values);

    // 根据条件 查询
    // public List<T> findByNamedQuery(String queryName, Object... args);

    /**
     * 查询出所有的持久化实体，并排序
     * 
     * @param orderCol
     *            排序字段
     * @param orderMode
     *            排序模式（降序或升序）
     * @return 所有持久化实体的集合
     */
    // public List<T> findByOrder(String orderCol, SQLOrderMode orderMode);

    /**
     * 按照泛型的实体类型，分页查询得到所有持久化实体。
     * 
     * @return 所有持久化实体的集合
     */
    // Page<T> findAllForPage(PageRequest<T> pageRequest);
}
