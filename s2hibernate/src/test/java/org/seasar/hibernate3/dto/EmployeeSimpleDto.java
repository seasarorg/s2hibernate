/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.hibernate3.dto;

import org.seasar.hibernate3.entity.Employee;

/**
 * @author kenichi_okazaki
 */
public class EmployeeSimpleDto extends Employee {

    private static final long serialVersionUID = 1L;

    private String orderBy;

    /**
     * @return Returns the orderBy.
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * @param orderBy
     *            The orderBy to set.
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
