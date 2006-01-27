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

import java.lang.reflect.Method;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.seasar.hibernate3.S2SessionFactory;
import org.seasar.hibernate3.dao.criteria.CriteriaCommand;

/**
 * @author kenichi_okazaki
 */
public class AutoArgsQueryCommand extends AbstractAutoQueryCommand {

    public AutoArgsQueryCommand(S2SessionFactory s2sessionFactory, Class beanClass, Method method) {
        super(s2sessionFactory, beanClass, method);
    }

    protected Criteria getArgsCriteria(Session s2session, Object[] args) {
        ArgsMetaData argsMeta = getArgsMeta();

        Criteria criteria = s2session.createCriteria(getBeanClass());
        for (int i = 0; i < argsMeta.getArgsCount(); i++) {
            Object value = argsMeta.getValue(args, i);
            if (value != null) {
                CriteriaCommand criteriaCommand = (CriteriaCommand) criteriaCommandList_.get(i);
                criteria = criteriaCommand.getCriteria(criteria, value);
            }
        }
        return criteria;
    }
}