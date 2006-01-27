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
import java.util.List;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.hibernate3.dao.EmployeeAutoDao;
import org.seasar.hibernate3.dto.EmployeeSearchDto;

public class EmployeeDaoSearchDtoTest extends S2TestCase {

    private EmployeeAutoDao dao_;

    public EmployeeDaoSearchDtoTest(String arg0) {
        super(arg0);
    }

    protected void setUp() throws Exception {
        super.setUp();
        include("EmployeeAutoDao.dicon");
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetEmployeeByGtLtSalDto() {
        EmployeeSearchDto dto1 = new EmployeeSearchDto();

        dto1.setFromSal(new BigDecimal(1000));
        dto1.setToSal(new BigDecimal(2000));

        List ret = dao_.getEmployeeByDto(dto1);

        assertEquals(6, ret.size());
    }

    public void testGetEmployeeByGtLtSalDto2() {
        EmployeeSearchDto dto2 = new EmployeeSearchDto();
        dto2.setFromSal(new BigDecimal(1000));

        List ret2 = dao_.getEmployeeByDto(dto2);

        assertEquals(12, ret2.size());
    }

    public void testGetEmployeeByGtLtSalDto3() {
        EmployeeSearchDto dto3 = new EmployeeSearchDto();
        dto3.setToSal(new BigDecimal(2000));

        List ret3 = dao_.getEmployeeByDto(dto3);

        assertEquals(8, ret3.size());
    }

    public void testGetEmployeeByGtLtSalDto4() {
        EmployeeSearchDto dto = new EmployeeSearchDto();
        dto.setDeptno(new Integer(20));

        List ret = dao_.getEmployeeByDto(dto);

        assertEquals(5, ret.size());
    }

}