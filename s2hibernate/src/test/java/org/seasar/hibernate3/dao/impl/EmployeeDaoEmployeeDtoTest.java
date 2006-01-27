/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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

import org.seasar.extension.unit.S2TestCase;
import org.seasar.hibernate3.dao.EmployeeAutoDao;
import org.seasar.hibernate3.entity.Employee;

public class EmployeeDaoEmployeeDtoTest extends S2TestCase {

    private EmployeeAutoDao dao_;

    public EmployeeDaoEmployeeDtoTest(String arg0) {
        super(arg0);
    }

    protected void setUp() throws Exception {
        super.setUp();

        include("EmployeeAutoDao.dicon");
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetEmployeeByDtoAuto_Empno() {
        Employee emp = new Employee();
        emp.setEmpno(new Integer(7369));

        List ret = dao_.getEmployeeByEmployeeDtoAuto(emp);

        assertEquals(1, ret.size());
        assertEquals("SMITH", ((Employee) ret.get(0)).getEname());
    }

    public void testGetEmployeeByDtoAuto_Ename() {
        Employee emp = new Employee();
        emp.setEname("SMITH");

        List ret = dao_.getEmployeeByEmployeeDtoAuto(emp);

        assertEquals(1, ret.size());
        assertEquals(7369, ((Employee) ret.get(0)).getEmpno().intValue());
    }

    public void testGetEmployeeByDtoAuto_JobAndDeptno() {
        Employee emp = new Employee();

        emp.setJob("MANAGER");
        emp.setDeptno(new Integer(20));

        List ret = dao_.getEmployeeByEmployeeDtoAuto(emp);

        assertEquals(1, ret.size());
        assertEquals("JONES", ((Employee) ret.get(0)).getEname());
    }

}