package com.genericsdao.daoimp;


import java.io.Serializable;
import java.util.Collection;

import com.genericsdao.bean.Department;
import com.genericsdao.dao.IDepartmentDao;

//传递泛型
public class DepartmentDaoImpl extends BaseDaoImpl<Department> implements IDepartmentDao<Department>{

	@Override
	public void deleteDepartmentById(Serializable id, String deleteMode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Department> getAllDepartments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Department getDepartmentById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

}