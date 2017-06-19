package com.daodemo.daofactory;

import com.daodemo.dao.IEmpDAO;
import com.daodemo.daoimpl.EmpDAOProxyImpl;

public class DAOFactory {
    public static IEmpDAO getInstance() {
        IEmpDAO dao = null;
        try {
            dao = new EmpDAOProxyImpl();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dao;
    }
}