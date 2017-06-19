package com.genericsdao.daoimp;

import java.lang.reflect.ParameterizedType;

import com.genericsdao.bean.Shop;
import com.genericsdao.bean.User;
import com.genericsdao.dao.IShopDao;
import com.genericsdao.dbc.DBHelper;

public class ShopDaoImpl extends BaseDaoImpl<Shop> implements IShopDao {

    public ShopDaoImpl() {
        ParameterizedType type = (ParameterizedType) getClass()
                .getGenericSuperclass();
        EntityClass = (Class<Shop>) type.getActualTypeArguments()[0];
    }

    @Override
    public Shop findById(int id) {
        StringBuffer b = new StringBuffer();
        Shop shop = new Shop();
        sql = b.append(
                "select * from " + EntityClass.getSimpleName() + " WHERE id=?")
                .toString();
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
