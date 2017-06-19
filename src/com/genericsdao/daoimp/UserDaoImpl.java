package com.genericsdao.daoimp;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import com.genericsdao.bean.User;
import com.genericsdao.dao.IUserDao;
import com.genericsdao.dbc.DBHelper;

//User DAO实现
//具体的DAO的实现
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {

    public UserDaoImpl() {

        ParameterizedType type = (ParameterizedType) getClass()
                .getGenericSuperclass();
        EntityClass = (Class<User>) type.getActualTypeArguments()[0];
    }

    @Override
    public List<User> findAll() {
        // TODO Auto-generated method stub
        StringBuffer b = new StringBuffer();
        List<User> userList = new ArrayList<User>();
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
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

}
