/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.seasar.extension.dataset.DataSet;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.hibernate3.dao.EmployeeAutoDao;
import org.seasar.hibernate3.dto.EmployeeSimpleDto;
import org.seasar.hibernate3.entity.Employee;

public class EmployeeDaoSimpleDtoTest extends S2TestCase {

    private EmployeeAutoDao dao_;

    public EmployeeDaoSimpleDtoTest(String arg0) {
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
        EmployeeSimpleDto emp = new EmployeeSimpleDto();
        emp.setEmpno(new Integer(7369));

        List ret = dao_.getEmployeeByEmployeeSimpleDtoAuto(emp);

        assertEquals(1, ret.size());
        assertEquals("SMITH", ((Employee) ret.get(0)).getEname());
    }

    public void testGetEmployeeByDtoAuto_Ename() {
        EmployeeSimpleDto emp = new EmployeeSimpleDto();
        emp.setEname("SMITH");

        List ret = dao_.getEmployeeByEmployeeSimpleDtoAuto(emp);

        assertEquals(1, ret.size());
        assertEquals(7369, ((Employee) ret.get(0)).getEmpno().intValue());
    }

    public void testGetEmployeeByDtoAuto_Job() {
        EmployeeSimpleDto emp = new EmployeeSimpleDto();
        emp.setJob("MANAGER");
        emp.setOrderBy("empno desc");

        List ret = dao_.getEmployeeByEmployeeSimpleDtoAuto(emp);

        DataSet expected = readXls("EMP_MANAGER.xls");
        assertEquals("1", expected, ret);

    }

    public void testGetEmployeeByDtoAuto_Mgr() {
        EmployeeSimpleDto emp = new EmployeeSimpleDto();
        emp.setMgr(new Short((short) 7839));
        emp.setOrderBy("empno desc");

        List ret = dao_.getEmployeeByEmployeeSimpleDtoAuto(emp);

        DataSet expected = readXls("EMP_MANAGER.xls");
        assertEquals("1", expected, ret);

    }

    public void testGetEmployeeByDtoAuto_Hiredate() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setLenient(false);
        sdf.applyPattern("yyyy/MM/dd");

        EmployeeSimpleDto emp = new EmployeeSimpleDto();
        try {
            emp.setHiredate(sdf.parse("1980/12/17"));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        emp.setOrderBy("empno desc");

        List ret = dao_.getEmployeeByEmployeeSimpleDtoAuto(emp);

        assertEquals(1, ret.size());
        assertEquals("SMITH", ((Employee) ret.get(0)).getEname());

    }

    public void testGetEmployeeByDtoAuto_Sal() {
        EmployeeSimpleDto emp = new EmployeeSimpleDto();
        emp.setSal(new BigDecimal(5000));
        emp.setOrderBy("empno desc");

        List ret = dao_.getEmployeeByEmployeeSimpleDtoAuto(emp);

        assertEquals(1, ret.size());
        assertEquals("KING", ((Employee) ret.get(0)).getEname());

    }

    public void testGetEmployeeByDtoAuto_Comm() {
        EmployeeSimpleDto emp = new EmployeeSimpleDto();
        emp.setComm(new Float(300));
        emp.setOrderBy("empno desc");

        List ret = dao_.getEmployeeByEmployeeSimpleDtoAuto(emp);

        assertEquals(1, ret.size());
        assertEquals("ALLEN", ((Employee) ret.get(0)).getEname());

    }

    public void testGetEmployeeByDtoAuto_Deptno() {
        EmployeeSimpleDto emp = new EmployeeSimpleDto();
        emp.setDeptno(new Integer(20));
        emp.setOrderBy("empno desc");

        List ret = dao_.getEmployeeByEmployeeSimpleDtoAuto(emp);

        assertEquals(5, ret.size());

    }

    public void testGetEmployeeByDtoAuto_JobAndDeptno() {
        EmployeeSimpleDto emp = new EmployeeSimpleDto();

        emp.setJob("MANAGER");
        emp.setDeptno(new Integer(20));
        emp.setOrderBy("empno desc");

        List ret = dao_.getEmployeeByEmployeeSimpleDtoAuto(emp);

        assertEquals(1, ret.size());
        assertEquals("JONES", ((Employee) ret.get(0)).getEname());
    }

}