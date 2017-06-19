package com.annotationdao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.annotationdao.dao.IGenericDao;
import com.annotationdao.impl.GenericDaoImpl;
import com.annotationdao.model.Book;

/**
 * 测试泛型DAO的CRUD操作
 */
public class GenericDaoTest {

    private IGenericDao<Book> bookDao = new GenericDaoImpl<Book>();

    public void testSave() throws Exception {
        List<Book> books = new ArrayList<Book>();
        for (Book book : books) {
            bookDao.save(book);
        }
    }

    public void testStudentFindAll1() throws Exception {
        System.out
                .println("\n-------------更新、删除前,测试查询所有记录--------------------");
        List<Book> books = bookDao.findAllByConditions(null, Book.class);
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void testDelete() throws Exception {
        System.out.println("\n-------------测试删除一条记录--------------------");
        bookDao.delete("9787111349662", Book.class);
    }

    public void testGet() throws Exception {
        System.out.println("\n-------------测试查询一条记录--------------------");
        Book book = bookDao.get("9787121025389", Book.class);
        System.out.println(book);
    }

    public void testUpdate() throws Exception {
        System.out.println("\n-------------测试修改一条记录--------------------");
        Book book = new Book();
        book.setIsbn("9787121025389");
        book.setName("JAVA面向对象编程");
        book.setAuthor("孙卫琴");
        book.setPublishing("电子工业出版社");
        book.setPubdate(new Date(2006, 07, 25));
        book.setPrice(50.6);
        bookDao.update(book);
    }

    public void testStudentFindAll2() throws Exception {
        System.out
                .println("\n-------------更新、删除前,测试根据条件查询所有记录--------------------");
        Map<String, Object> sqlWhereMap = new HashMap<String, Object>();
        // sqlWhereMap.put("t_isbn", "9787111213826");
        // sqlWhereMap.put("t_name", "Java");
        sqlWhereMap.put("t_publishing", "机械工业出版社");
        // sqlWhereMap.put("t_pubdate", new Date(new
        // SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2007-01-01 12:06:00").getTime()));
        List<Book> books = bookDao.findAllByConditions(null, Book.class);
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public static void main(String[] args) throws Exception {
        GenericDaoTest dao = new GenericDaoTest();
        dao.bookDao.create(new Book());
        dao.testSave();
        dao.testStudentFindAll1();
        dao.testDelete();
        dao.testGet();
        dao.testUpdate();
        dao.testStudentFindAll2();
    }

}