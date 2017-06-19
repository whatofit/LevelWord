package com.genericsdao;

import java.util.List;

import com.genericsdao.bean.User;
import com.genericsdao.daoimp.UserDaoImpl;

//泛型是java中一种类型，泛型是被参数化的类型。
//类型-->class
//参数化-->class类型可以是任意参数
//泛型存在的意义：泛型可以在编译的时候，告诉class传递的参数是什么类型，如果类型发送错误，在编译的时候，就会报错。
public class GenericsDaoMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
        UserDaoImpl imp = new UserDaoImpl();

        List<User> list = imp.findAll();
        for (User user : list) {
            System.out.println(user.getId() + " " + user.getUsername() + " "
                    + user.getPassword() + " " + user.getEmail() + " "
                    + user.getGrade());
        }

        // insert
        User user = new User();
        user.setId(1);
        user.setUsername("name 刘");
        user.setPassword("123456");
        user.setEmail("123");
        user.setGrade(5);
        imp.insert(user);

        // update
        User user_1 = new User();
        user.setId(1);
        user.setUsername("dave 赵");
        user.setPassword("123456");
        user.setEmail("123");
        user.setGrade(5);
        imp.update(user_1);
    }

}
