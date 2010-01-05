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

import java.math.BigDecimal;

public class EmployeeSalDto {

    private BigDecimal fromSal;
    private BigDecimal toSal;

    public EmployeeSalDto() {
    }

    /**
     * @return Returns the fromSal.
     */
    public BigDecimal getFromSal() {
        return fromSal;
    }

    /**
     * @param fromSal
     *            The fromSal to set.
     */
    public void setFromSal(BigDecimal fromSal) {
        this.fromSal = fromSal;
    }

    /**
     * @return Returns the toSal.
     */
    public BigDecimal getToSal() {
        return toSal;
    }

    /**
     * @param toSal
     *            The toSal to set.
     */
    public void setToSal(BigDecimal toSal) {
        this.toSal = toSal;
    }
}
