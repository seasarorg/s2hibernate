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

import org.seasar.extension.unit.S2TestCase;
import org.seasar.hibernate3.dao.DepartmentAutoDao;
import org.seasar.hibernate3.entity.Department;

public class DepartmentDaoTest extends S2TestCase {

    private DepartmentAutoDao dao;

    public DepartmentDaoTest(String arg0) {
        super(arg0);
    }

    protected void setUp() throws Exception {
        super.setUp();
        include("DepartmentAutoDao.dicon");
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetDepartmentByDeptno() {
        Department dept = dao.getDepartmentByDeptno(30);

        assertEquals("1", 6, dept.getEmployee().size());

    }

    public void testGetDeptByDeptno() {
        Department dept = dao.getDept(30);

        assertEquals("100", 6, dept.getEmployee().size());

    }

    public void testGetDeptName() {
        String deptName = dao.getDeptName(30);

        assertEquals("100", "SALES", deptName);

    }

}