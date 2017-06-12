package com.daodemo.dao.impl;

import java.util.List;

import com.daodemo.dao.IEmpDAO;
import com.daodemo.dbc.DatabaseConnection;
import com.daodemo.vo.Emp;

public class EmpDAOProxy implements IEmpDAO {
	private DatabaseConnection dbc;
	private IEmpDAO dao = null;
	
	public EmpDAOProxy() throws Exception {
		dbc = new DatabaseConnection();
		dao = new EmpDAOImpl(dbc.getConnection());
	}
	
	@Override
	public boolean insert(Emp emp) throws Exception {
		boolean flag = false;
		if (dao.selectById(emp.getEmpno()) == null) {
			flag = dao.insert(emp);
		}
		dbc.close();
		return flag;
	}
	
	@Override
	public Emp selectById(int empId) throws Exception {
		Emp emp = dao.selectById(empId);
		dbc.close();
		return emp;
	}
	@Override
	public List<Emp> selectAll() throws Exception {
		List<Emp> list = dao.selectAll();
		dbc.close();
		return list;
	}
	@Override
	public void update(Emp emp) throws Exception {
		
	}
	@Override
	public void delete(int empId) throws Exception {
		
	}

}