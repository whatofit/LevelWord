package com.daodemo;

import com.daodemo.daofactory.DAOFactory;
import com.daodemo.vo.Emp;

//一、信息系统的开发架构
//
//客户层-------显示层-------业务层---------数据层---------数据库
//1.客户层：客户层就是客户端，简单的来说就是浏览器。
//2.显示层：JSP/Servlet，用于给浏览器显示。
//3.业务层：对于数据层的原子操作进行整合。
//4.数据层：对于数据库进行的原子操作，增加、删除等；
//
//二、DAO(Data Access Object)介绍
//
//DAO应用在数据层那块,用于访问数据库，对数据库进行操作的类。
//
//三、DAO设计模式的结构
//
//DAO设计模式一般分为几个类：
//1.VO(Value Object)：一个用于存放网页的一行数据,即一条记录的类，比如网页要显示一个用户的信息，则这个类就是用户的类。
//2.DatabaseConnection：用于打开和关闭数据库。
//3.DAO接口：用于声明对于数据库的操作。
//4.DAOImpl：必须实现DAO接口，真实实现DAO接口的函数，但是不包括数据库的打开和关闭。
//5.DAOProxy：也是实现DAO接口，但是只需要借助DAOImpl即可，但是包括数据库的打开和关闭。
//6.DAOFactory：工厂类，含有getInstance()创建一个Proxy类。
//
//四、DAO的好处
//
//DAO的好处就是提供给用户的接口只有DAO的接口，所以如果用户想添加数据，只需要调用create函数即可，不需要数据库的操作。
//
//五、DAO包命名
//
//对于DAO，包的命名和类的命名一定要有层次。

public class TestEmpDAO {
    public static void main(String args[]) throws Exception {
        Emp emp = null;
        for (int i = 0; i < 5; i++) {
            emp = new Emp();
            emp.setEmpno(i);
            emp.setEname("xiazdong-" + i);
            emp.setJob("stu-" + i);
            java.util.Date date = new java.util.Date();
            emp.setHireDate(new java.sql.Date(date.getTime()));

            // pst.setDate(1,
            // java.sql.Date(date.getTime()));//这里的Date是sql中的::得到的是日期
            // pst.setTime(2, java.sql.Time(date.getTime()))//sql包中的Time::得到的是时间
            // pst.setObject(3,
            // java.sql.Timestamp(date.getTime()));//::得到的是日期及时间

            emp.setSal(500 * i);
            DAOFactory.getInstance().insert(emp);
        }
    }
}