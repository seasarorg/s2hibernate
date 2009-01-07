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

import java.math.BigDecimal;
import java.util.List;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.hibernate3.dao.EmployeeAutoDao;
import org.seasar.hibernate3.dto.EmployeeSalDto;

public class EmployeeHQLDtoTest extends S2TestCase {

    private EmployeeAutoDao dao_;

    public EmployeeHQLDtoTest(String arg0) {
        super(arg0);
    }

    protected void setUp() throws Exception {
        super.setUp();
        include("EmployeeAutoDao.dicon");
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetEmployeeBySalDto() {
        EmployeeSalDto dto = new EmployeeSalDto();
        dto.setFromSal(new BigDecimal(1000));
        dto.setToSal(new BigDecimal(2000));

        List ret = dao_.getEmployeeBySalDto(dto);
        assertEquals(6, ret.size());

    }
}