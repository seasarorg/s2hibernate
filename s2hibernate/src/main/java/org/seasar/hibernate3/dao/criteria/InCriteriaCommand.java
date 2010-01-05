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
package org.seasar.hibernate3.dao.criteria;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

/**
 * @author kenichi_okazaki
 */
public class InCriteriaCommand extends abstractCriteriaCommand {

    public InCriteriaCommand(String fieldName, String dtoFieldName) {
        super(fieldName, dtoFieldName);
    }

    public Criteria getCriteria(Criteria criteria, Object value) {
        if (value instanceof List) {
            return criteria.add(Expression.in(fieldName_, (List) value));
        }
        return criteria.add(Expression.in(fieldName_, (Object[]) value));
    }
}
