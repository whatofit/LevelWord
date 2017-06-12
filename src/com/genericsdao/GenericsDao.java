package com.genericsdao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import com.genericsdao.bean.Person;
import com.genericsdao.dbc.DBHelper;

//泛型方法
public class GenericsDao {
	//传入po 根据po的值  返回匹配的list
	public static  <T> List<T>  queryList(String sql,Object[] params,T t){
		Class clazz=t.getClass();
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<T> list=new ArrayList<T>();
		try {
			conn=DBHelper.getConnection();
			ps=conn.prepareStatement(sql);
			//填坑
			if(params!=null && params.length>0){
				for(int i=0;i<params.length;i++){
					ps.setObject(i+1, params[i]);
				}
			}
			rs=ps.executeQuery();
			ResultSetMetaData metaData=rs.getMetaData();
			int count=metaData.getColumnCount();
			while(rs.next()){
				T temp=(T)clazz.newInstance();
				for(int i=0;i<count;i++){
					String fieldName=metaData.getColumnName(i+1);
					Field field=clazz.getDeclaredField(fieldName);
					Method method=clazz.getMethod(getSetter(fieldName), field.getType());
					method.invoke(temp, rs.getObject(i+1));
				}
				list.add(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBHelper.release(ps, rs);
		}
		return list;
	}
	
	//根据id查询po
	public static <T> T queryPo(String sql,int id,T t){
		Class clazz=t.getClass();
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		T obj=null;
		try {
			conn=DBHelper.getConnection();
			ps=conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs=ps.executeQuery();
			ResultSetMetaData metaData=rs.getMetaData();
			int count=metaData.getColumnCount();
			obj=(T)clazz.newInstance();
			while(rs.next()){
				for(int i=0;i<count;i++){
					String fieldName=metaData.getColumnName(i+1);
					Field field =clazz.getDeclaredField(fieldName);
					Method method=clazz.getMethod(getSetter(fieldName), field.getType());
					method.invoke(obj, rs.getObject(i+1));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBHelper.release(ps, rs);
		}
		
		return obj;
	}
	
	public static  void changePo(String sql,Object[] params,Class clazz){
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			conn=DBHelper.getConnection();
			ps=conn.prepareStatement(sql);
			//填坑
			if(params!=null && params.length>0){
				for(int i=0;i<params.length;i++){
					ps.setObject(i+1, params[i]);
				}
			}
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBHelper.release(ps, null);
		}
	}
	
	public static String getSetter(String fieldName){
		//传入属性名 拼接set方法  
		return "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);	
		
	}
	public static void main(String[] args) {
//		List<Person> list=BaseDao2.queryList("select * from person ", null, new Person());
//		System.out.println(list.size());
		Person person=queryPo("select * from person where id=?",4,new Person());
		System.out.println(person.getName());
	}
}
