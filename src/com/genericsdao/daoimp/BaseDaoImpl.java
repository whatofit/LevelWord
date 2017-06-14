package com.genericsdao.daoimp;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.genericsdao.dao.IBaseDao;
import com.genericsdao.dbc.DBHelper;

//ͨ通用DAO的实现

//1.首先我们如果想对User表进行操作,那么我们首先需要获取User类型.告诉BaseDaoImp,我们当前是需要对User表进行操作.因此构造函数就是用来干这个的.
//2. 当我们获取了User类型之后,如果想要对其进行操作,那么首先需要知道 sql 语句,因此我们需要对sql语句进行拼接.那么拼接过程中,我们需要知道User表内部到底声明了哪些变量.这样就需要使用反射机制.通过反射机制来获取 User实体类中声明的变量,然后对sql进行相关的拼接.那么getsql函数用来完成sql的拼接过程.
//3. 那么拼接完之后还是不行,因为拼接出来的sql语句是这样的:insert into User(id,username,password,email,grade) values(?,?,?,?,?)我们需要对占位符进行赋值操作.那么首先我们需要获取具体的值,那么setArgs就是来获取属性的具体值的.
//4.当获取了具体的值之后,我们就可以通过sql提供给我们的相关函数来执行sql语句了.

//子类里面的T是给父类里面T赋值，子类里面的T是实参，父类里面的t是形参

//GenericDAOImpl
public class BaseDaoImpl<T> implements IBaseDao<T> {

	/** 操作常量 */
	public static final String SQL_CREATE = "create";// 创建table
	public static final String SQL_INSERT = "insert";
	public static final String SQL_DELETE = "delete";
	public static final String SQL_UPDATE = "update";
	public static final String SQL_SELECT = "select";

	// 泛型的Class
	private Class<T> EntityClass; // 获取实体类
	// private Class<T> clazz;

	private PreparedStatement statement;

	private String sql;

	private Object argType[];

	private ResultSet rs;

	// //仿照 DbUtils中的List results = (List) qr.query(conn,
	// "select id,name from guestbook", new BeanListHandler(Guestbook.class));
	// // 创建构造器,new BaseDaoImpl<User>(User.class),防止泛型擦除
	// public BaseDaoImpl(Class<T> clazz) {
	// super();
	// this.clazz = clazz;
	// }

	// private String className;
	// // 通过构造器 注入 类名
	// public BaseDaoImpl(String className) {
	// this.className = className;
	// }

	/*
	 * 在父类中，要执行一段代码，执行的时间是在子类创建对象的时候，那么有两种解决方案 1、使用static代码块 2、利用父类的构造函数
	 * 
	 * 分析：如果需要使用到this，那么就要使用父类的构造函数，如果不需要使用daothis，可以使用static代码块
	 * 因为下面需要使用this，获取ParameterizedType，所以使用父类的构造函数 如何获取泛型里面的class
	 */
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {// 使用父类的构造函数来实现对获取泛型的Class
		/**
		 * 传递User就是 com.example.daoimp.BaseDaoImp<com.example.bean.User>
		 * 传递Shop就是 com.example.daoimp.BaseDaoImp<com.example.bean.Shop>
		 * */
		// ParameterizedType,就是泛型，这里的this不是BaseDaoImpl，而是BaseDaoImpl的子类的this，
		// 也就是一个类会继承BaseDaoImpl，然后通过它来获取其父类BaseDaoImpl的泛型T
		ParameterizedType type = (ParameterizedType) getClass()
				.getGenericSuperclass();

		/**
		 * 这里如果传递的是User.那么就是class com.example.bean.User 如果传递的是Shop. 那么就是class
		 * com.example.bean.Shop
		 * */
		// 获取到泛型的Class
		EntityClass = (Class<T>) type.getActualTypeArguments()[0];
	}

	@Override
	public int create() {
		sql = this.getSql(SQL_CREATE); // 获取sql.
		int affectRowCount = 0;
		// 赋值.
		try {
			// argType = setArgs(t, SQL_CREATE);
			statement = DBHelper.getPreparedStatement(sql); // 实例化PreparedStatement.
			// // 为sql语句赋值.
			// statement = DBHelper.setPreparedStatementParam(statement,
			// argType);
			affectRowCount = statement.executeUpdate(); // 执行语句.
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelper.release(statement, null); // 释放资源.
		}
		return affectRowCount;
	}

	@Override
	public void insert(T t) {
		sql = this.getSql(SQL_INSERT); // 获取sql.
		// 赋值.
		try {
			argType = setArgs(t, SQL_INSERT);
			statement = DBHelper.getPreparedStatement(sql); // 实例化PreparedStatement.
			// 为sql语句赋值.
			statement = DBHelper.setPreparedStatementParam(statement, argType);
			statement.executeUpdate(); // 执行语句.
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelper.release(statement, null); // 释放资源.
		}
	}

	@Override
	public int insert(Collection<T> entities) {
		int affectRowCount = -1;// 影响的行数
		try {
			sql = this.getSql(SQL_INSERT); // 获取sql.
			/** 执行SQL预编译 **/
			statement = DBHelper.getPreparedStatement(sql); // 实例化PreparedStatement.
			/** 设置不自动提交，以便于在出现异常的时候数据库回滚 **/
			DBHelper.getConnection().setAutoCommit(false);
			// System.out.println(sql);
//			for (int i = 0; i < entities.size(); i++) {
//				T param = (T) entities..get(i);
//				for (int j = 0; j < param.size(); j++) {
//					statement.setObject(j + 1, param.get(j));
//				}
//				statement.addBatch();
//			}
			int[] arr = statement.executeBatch();
			DBHelper.getConnection().commit();
			affectRowCount = arr.length;
			// System.out.println("成功了插入了" + affectRowCount + "行");
			// System.out.println();
		} catch (Exception e) {
			if (DBHelper.getConnection() != null) {
				try {
					DBHelper.getConnection().rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			DBHelper.release(statement, null); // 释放资源.
		}
		return affectRowCount;
	}	
	
		
	
	@Override
	public void delete(T t) {
		sql = this.getSql(SQL_DELETE);
		try {
			argType = this.setArgs(t, SQL_DELETE);
			statement = DBHelper.getPreparedStatement(sql);
			statement = DBHelper.setPreparedStatementParam(statement, argType);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelper.release(statement, null);
		}
	}

	@Override
	public void update(T t) {
		sql = this.getSql(SQL_UPDATE);
		try {
			argType = setArgs(t, SQL_UPDATE);
			statement = DBHelper.getPreparedStatement(sql);
			statement = DBHelper.setPreparedStatementParam(statement, argType);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelper.release(statement, null);
		}
	}

	/*
	 * 有人会问，为什么要获取泛型的Class，对应操作数据库来说，获取一个泛型的实例，需要通过泛型的Class来获取
	 * 说白了，没有泛型的Class,就无法获取泛型的实例对象
	 */
	@Override
	public T select(T t) {
		sql = this.getSql(SQL_SELECT);
		T obj = null;
		try {
			argType = setArgs(t, SQL_SELECT);
			statement = DBHelper.getPreparedStatement(sql);
			statement = DBHelper.setPreparedStatementParam(statement, argType);
			rs = statement.executeQuery();
			Field fields[] = EntityClass.getDeclaredFields();
			while (rs.next()) {
				obj = EntityClass.newInstance();
				for (int i = 0; i < fields.length; i++) {
					fields[i].setAccessible(true);
					fields[i].set(obj, rs.getObject(fields[i].getName()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelper.release(statement, rs);
		}
		return obj;
	}

	@Override
	public List<T> selectAll() {
		List<T> list = new ArrayList<T>();
		// 存储所有字段的信息
		// 通过反射获得要查询的字段
		StringBuffer fieldNames = new StringBuffer();
		// 通过反射获取实体类中的所有变量
		Field fields[] = EntityClass.getDeclaredFields();
		for (int i = 0; fields != null && i < fields.length; i++) {
			fields[i].setAccessible(true); // 这句话必须要有,否则会抛出异常.
			String column = fields[i].getName();
			fieldNames.append(column).append(",");
		}
		fieldNames.deleteCharAt(fieldNames.length() - 1);

		// 拼装SQL
		String sqlSel = "select " + fieldNames + " from "
				+ EntityClass.getSimpleName();
		// String sql = "select * from " + tableName;

		try {
			statement = DBHelper.getPreparedStatement(sqlSel);
			rs = statement.executeQuery();
			//Field[] tbFields = EntityClass.getDeclaredFields();
			// while (rs.next()) {
			// T t = EntityClass.newInstance();
			// for (int i = 0; i < tbFields.length; i++) {
			// tbFields[i].setAccessible(true);
			// tbFields[i].set(t, rs.getObject(tbFields[i].getName()));
			// }
			// list.add(t);
			// }
			while (rs.next()) {
				T t = EntityClass.newInstance();
				initObject(t, fields, rs);
				list.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelper.release(statement, rs);
		}
		return list;
	}

	/**
	 * 根据结果集初始化对象
	 */
	private void initObject(T t, Field[] fields, ResultSet rs)
			throws SQLException, IntrospectionException,
			IllegalAccessException, InvocationTargetException {
		for (Field field : fields) {
			String propertyName = field.getName();
			Object paramVal = null;
			Class<?> clazzField = field.getType();
			if (clazzField == String.class) {
				paramVal = rs.getString(propertyName);
			} else if (clazzField == short.class || clazzField == Short.class) {
				paramVal = rs.getShort(propertyName);
			} else if (clazzField == int.class || clazzField == Integer.class) {
				paramVal = rs.getInt(propertyName);
			} else if (clazzField == long.class || clazzField == Long.class) {
				paramVal = rs.getLong(propertyName);
			} else if (clazzField == float.class || clazzField == Float.class) {
				paramVal = rs.getFloat(propertyName);
			} else if (clazzField == double.class || clazzField == Double.class) {
				paramVal = rs.getDouble(propertyName);
			} else if (clazzField == boolean.class
					|| clazzField == Boolean.class) {
				paramVal = rs.getBoolean(propertyName);
			} else if (clazzField == byte.class || clazzField == Byte.class) {
				paramVal = rs.getByte(propertyName);
			} else if (clazzField == char.class
					|| clazzField == Character.class) {
				paramVal = rs.getCharacterStream(propertyName);
			} else if (clazzField == Date.class) {
				paramVal = rs.getTimestamp(propertyName);
			} else if (clazzField.isArray()) {
				paramVal = rs.getString(propertyName).split(","); // 以逗号分隔的字符串
			}
			PropertyDescriptor pd = new PropertyDescriptor(propertyName,
					t.getClass());
			pd.getWriteMethod().invoke(t, paramVal);
		}
	}

	// sql拼接函数 形如 : insert into User(id,username,password,email,grade)
	// values(?,?,?,?,?)
	private String getSql(String operator) {
		StringBuffer sql = new StringBuffer();
		// 通过反射获取实体类中的所有变量
		Field fields[] = EntityClass.getDeclaredFields();
		if (operator.equals(SQL_CREATE)) {// 创建新表操作
			// CREATE TABLE IF NOT EXISTS levelWordTable
			// (frequency,spelling,minLevel,partsOfSpeech,meaning,exampleSentence);
			sql.append("create table if not exists "
					+ EntityClass.getSimpleName());
			sql.append("(");
			for (int i = 0; fields != null && i < fields.length; i++) {
				fields[i].setAccessible(true); // 这句话必须要有,否则会抛出异常.
				String column = fields[i].getName();
				sql.append(column).append(",");
			}
			sql = sql.deleteCharAt(sql.length() - 1);
			sql.append(");");
		} else if (operator.equals(SQL_INSERT)) { // 增加/插入操作
			sql.append("insert into " + EntityClass.getSimpleName());
			sql.append("(");
			for (int i = 0; fields != null && i < fields.length; i++) {
				fields[i].setAccessible(true); // 这句话必须要有,否则会抛出异常.
				String column = fields[i].getName();
				sql.append(column).append(",");
			}
			sql = sql.deleteCharAt(sql.length() - 1);
			sql.append(") values (");
			for (int i = 0; fields != null && i < fields.length; i++) {
				sql.append("?,");
			}
			sql.deleteCharAt(sql.length() - 1);
			// 是否需要添加分号
			sql.append(")");
		} else if (operator.equals(SQL_DELETE)) {
			sql.append("delete from " + EntityClass.getSimpleName()
					+ " where id=?");
		} else if (operator.equals(SQL_UPDATE)) {
			sql.append("update " + EntityClass.getSimpleName() + " set ");
			for (int i = 0; fields != null && i < fields.length; i++) {
				fields[i].setAccessible(true);
				String column = fields[i].getName();
				if (column.equals("id")) {
					continue;
				}
				sql.append(column).append("=").append("?,");
			}
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" where id=?");
		} else if (operator.equals(SQL_SELECT)) {
			sql.append("select * from " + EntityClass.getSimpleName()
					+ " where id=?");
		}
		return sql.toString();
	}

	// 获取参数.
	private Object[] setArgs(T entity, String operator)
			throws IllegalArgumentException, IllegalAccessException {

		Field fields[] = EntityClass.getDeclaredFields();
		if (operator.equals(SQL_CREATE)) {
		} else if (operator.equals(SQL_INSERT)) {
			Object obj[] = new Object[fields.length];
			for (int i = 0; obj != null && i < fields.length; i++) {
				fields[i].setAccessible(true);
				obj[i] = fields[i].get(entity);
			}
			return obj;
		} else if (operator.equals(SQL_DELETE)) {
			Object obj[] = new Object[1];
			fields[0].setAccessible(true);
			obj[0] = fields[0].get(entity);
			return obj;
		} else if (operator.equals(SQL_UPDATE)) {
			Object Tempobj[] = new Object[fields.length];
			for (int i = 0; Tempobj != null && i < fields.length; i++) {
				fields[i].setAccessible(true);
				Tempobj[i] = fields[i].get(entity);
			}

			Object obj[] = new Object[fields.length];
			System.arraycopy(Tempobj, 1, obj, 0, Tempobj.length - 1);
			obj[obj.length - 1] = Tempobj[0];
			return obj;
		} else if (operator.equals(SQL_SELECT)) {
			Object obj[] = new Object[1];
			fields[0].setAccessible(true);
			obj[0] = fields[0].get(entity);
			return obj;
		}
		return null;
	}
}
