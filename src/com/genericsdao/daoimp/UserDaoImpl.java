package com.genericsdao.daoimp;

import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.genericsdao.bean.User;
import com.genericsdao.dao.IUserDao;
import com.genericsdao.dbc.DBHelper;

//User DAO实现
//具体的DAO的实现
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {

	private Class<?> EntityClass;

	private String sql;

	private PreparedStatement statement;

	private ResultSet rs;

	private List<User> list;

	public UserDaoImpl() {

		ParameterizedType type = (ParameterizedType) getClass()
				.getGenericSuperclass();
		EntityClass = (Class<?>) type.getActualTypeArguments()[0];
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		StringBuffer b = new StringBuffer();
		list = new ArrayList<User>();
		sql = b.append("select * from " + EntityClass.getSimpleName())
				.toString();
		try {
			statement = DBHelper.getPreparedStatement(sql);
			rs = statement.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setUsername(rs.getString("username"));
				user.setGrade(rs.getInt("grade"));
				list.add(user);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
