/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.hibernate3.dao.impl;

import java.util.List;

import org.seasar.hibernate3.S2SessionFactory;
import org.seasar.hibernate3.dao.EmployeeDao;
import org.seasar.hibernate3.entity.Employee;

public class EmployeeDaoImpl implements EmployeeDao {

    private static final String HQL = "from Employee where empno = ?";
    private S2SessionFactory sessionFactory_;

    public EmployeeDaoImpl(S2SessionFactory sessionFactory) {

        sessionFactory_ = sessionFactory;
    }

    public Employee getEmployee(int empno) {
        List result = sessionFactory_.getSession().createQuery(HQL).setInteger(0, empno).list();
        if (result.size() > 0) {
            return (Employee) result.get(0);
        }
        else {
            return null;
        }
    }

    public void save(Employee employee) {
        sessionFactory_.getSession().save(employee);
    }

}
