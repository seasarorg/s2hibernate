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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.seasar.extension.dataset.DataSet;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.hibernate3.dao.EmployeeAutoDao;
import org.seasar.hibernate3.dto.EmployeeSearchDto;
import org.seasar.hibernate3.entity.Employee;

public class EmployeeDaoTest extends S2TestCase {

    private EmployeeAutoDao dao_;

    public EmployeeDaoTest(String arg0) {
        super(arg0);
    }

    protected void setUp() throws Exception {
        super.setUp();
        include("EmployeeAutoDao.dicon");
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetAllUserListTx() {
        List ret = dao_.getAllEmployee();
        assertNotNull("1", dao_.getAllEmployee());
        DataSet expected = readXls("EMP.xls");
        assertEquals("2", expected, ret);
    }

    public void testGetUserTx() {
        assertEquals(3, dao_.getEmployeeByJobDeptno(null, (int) 10).size());
    }

    public void testGetEmployeeByJobDeptnoEqTx() {
        assertEquals(3, dao_.getEmployeeByJobDeptnoEq(null, (int) 10).size());
    }

    public void testSaveTx() {
        Employee emp = new Employee();
        emp.setEmpno(new Integer(100010));
        emp.setEname("test");
        emp.setDeptno(new Integer(10));

        dao_.save(emp);
        Employee emp2 = dao_.getEmployeeByEmpNo(new Integer(100010));

        assertEquals("test", emp2.getEname());
    }

    public void testDeleteTx() {
        Employee emp = new Employee();
        emp.setEmpno(new Integer(100010));
        emp.setEname("test");
        emp.setDeptno(new Integer(10));

        dao_.save(emp);

        Employee empTmp = dao_.getEmployeeByEmpNo(new Integer(100010));
        dao_.delete(empTmp);

        assertNull("1", dao_.getEmployeeByEmpNo(new Integer(100010)));
    }

    public void testLoad() {
        Employee emp = dao_.load(new Integer(7698));
        assertEquals("BLAKE", emp.getEname());
    }

    public void testGetEmployeeList() {
        List ret = dao_.getEmployeeList(8, 4);
        assertEquals("1", 4, ret.size());

        DataSet expected = readXls("EMP_GetEmployeeList.xls");
        assertEquals("2", expected, ret);
    }

    public void testGetEmployeeCount() {
        int ret = dao_.getEmployeeCount();
        assertEquals(14, ret);
    }

    public void testGetEmployeeNameById() {
        String ret = dao_.getEmployeeNameById(new Integer(7369));
        assertEquals("SMITH", ret);
    }

    public void testGetEmployeeByJob() {
        List ret = dao_.getEmployeeByJob("MANAGER");
        DataSet expected = readXls("EMP_MANAGER.xls");
        assertEquals("1", expected, ret);
    }

    public void testGetSQLEmployeeNameById() {
        String ret = dao_.getNamedQueryEmployeeNameById(new Integer(7369));
        assertEquals("SMITH", ret);
    }

    public void testGetSQLEmployeeIdByName() {
        Integer ret = dao_.getNamedQueryEmployeeIdByName("SMITH");
        assertEquals(new Integer(7369), ret);
    }

    public void testGetAllEmployee() {
        List ret = dao_.getAllEmployee();
        assertEquals(14, ret.size());
    }

    public void testGetEmployeeByIdList() {
        List empnoList = new ArrayList();
        empnoList.add(new Integer(7499));
        empnoList.add(new Integer(7521));

        List ret = dao_.getEmployeeByIdList(empnoList);
        assertEquals(2, ret.size());
    }

    public void testGetEmployeeOrderByField() {
        List ret = dao_.getEmployeeOrderByField("empno desc");
        DataSet expected = readXls("EMP_DESC.xls");

        assertEquals("1", expected, ret);
    }

    public void testLoadEmployee() {
        Employee ret = dao_.loadLock(new Integer(7499));
        assertNotNull(ret);
    }

    public void testGetEmployeeByGtSal() {
        List ret = dao_.getEmployeeByGtSal(new BigDecimal(1000));
        assertEquals(12, ret.size());
    }

    public void testGetEmployeeByLtSal() {
        List ret = dao_.getEmployeeByLtSal(new BigDecimal(2000));
        assertEquals(8, ret.size());
    }

    public void testGetEmployeeByGtLtSal() {
        List ret = dao_.getEmployeeByGtLtSal(new BigDecimal(1000), new BigDecimal(2000));
        assertEquals(6, ret.size());

        List ret2 = dao_.getEmployeeByGtLtSal(new BigDecimal(1000), null);
        assertEquals(12, ret2.size());

        List ret3 = dao_.getEmployeeByGtLtSal(null, new BigDecimal(2000));
        assertEquals(8, ret3.size());
    }

    public void testGetEmployeeByLikeEmane() {
        List ret = dao_.getEmployeeByLikeEmane("%L%");
        assertEquals(4, ret.size());
    }

    public void testGetEmployeeByInIdList() {
        List empno = new ArrayList();
        empno.add(new Integer(7499));
        empno.add(new Integer(7521));

        List ret = dao_.getEmployeeByInIdList(empno);
        assertEquals(2, ret.size());
    }

    public void testGetEmployeeByGtLtSalDto() {
        EmployeeSearchDto dto1 = new EmployeeSearchDto();
        dto1.setFromSal(new BigDecimal(1000));
        dto1.setToSal(new BigDecimal(2000));
        List ret = dao_.getEmployeeByDto(dto1);
        assertEquals(6, ret.size());

        EmployeeSearchDto dto2 = new EmployeeSearchDto();
        dto2.setFromSal(new BigDecimal(1000));
        List ret2 = dao_.getEmployeeByDto(dto2);
        assertEquals(12, ret2.size());

        EmployeeSearchDto dto3 = new EmployeeSearchDto();
        dto3.setToSal(new BigDecimal(2000));
        List ret3 = dao_.getEmployeeByDto(dto3);
        assertEquals(8, ret3.size());
    }
}