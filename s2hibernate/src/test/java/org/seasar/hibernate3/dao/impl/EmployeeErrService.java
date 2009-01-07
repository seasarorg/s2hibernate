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

import org.hibernate.HibernateException;
import org.seasar.hibernate3.dao.EmployeeAutoDao;
import org.seasar.hibernate3.entity.Employee;

public class EmployeeErrService implements EmployeeService {

    EmployeeAutoDao dao_;

    public EmployeeErrService(EmployeeAutoDao dao) {
        dao_ = dao;
    }

    public void execute() {
        Employee emp = new Employee();
        emp.setEmpno(new Integer(7900));
        emp.setEname("test");
        emp.setDeptno(new Integer(10));

        dao_.save(emp);

        throw new HibernateException("");
    }
}
