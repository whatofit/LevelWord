package com.genericsdao.dao;

import java.io.Serializable;
import java.util.Collection;

import com.genericsdao.bean.Department;

public interface IDepartmentDao<T> extends IBaseDao<T> {
    // 对department操作的各种额外方法
    // public void saveDepartment(Department department);
    // public void updateDepartment(Department department);
    public void deleteDepartmentById(Serializable id, String deleteMode);

    public Collection<Department> getAllDepartments();

    public Department getDepartmentById(Serializable id);
}