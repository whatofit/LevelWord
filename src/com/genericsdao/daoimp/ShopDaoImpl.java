package com.genericsdao.daoimp;

import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.genericsdao.bean.Shop;
import com.genericsdao.bean.User;
import com.genericsdao.dao.ShopDao;
import com.genericsdao.dbc.DBHelper;

public class ShopDaoImpl extends BaseDaoImpl<Shop> implements ShopDao {
	private Class<?> EntityClass;

	private String sql;

	private PreparedStatement statement;

	private ResultSet rs;

	private Shop shop;
	
	public ShopDaoImpl() {

		ParameterizedType type = (ParameterizedType) getClass()
				.getGenericSuperclass();
		EntityClass = (Class<?>) type.getActualTypeArguments()[0];
	}

	@Override
	public Shop findById(int id) {
		StringBuffer b = new StringBuffer();
		shop = new Shop();
		sql = b.append("select * from " + EntityClass.getSimpleName() + " WHERE id=?").toString();
		try {
			statement = DBHelper.getPreparedStatement(sql);
			statement.setInt(1, id);
			rs = statement.executeQuery();
			if (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setPassword(rs.getString("username"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shop;
	}

}
