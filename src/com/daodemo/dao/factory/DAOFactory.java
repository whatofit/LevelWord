package com.daodemo.dao.factory;

import com.daodemo.dao.IEmpDAO;
import com.daodemo.dao.impl.EmpDAOProxy;

public class DAOFactory {
	public static IEmpDAO getInstance() {
		IEmpDAO dao = null;
		try {
			dao = new EmpDAOProxy();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dao;
	}
}