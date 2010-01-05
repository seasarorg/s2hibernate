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
package org.seasar.hibernate3.dao.impl;

import java.lang.reflect.Method;
import java.util.List;

import org.hibernate.Query;
import org.seasar.hibernate3.S2SessionFactory;

/**
 * @author kenichi_okazaki
 * 
 */
public class HqlQueryCommand extends AbstractHQLHibernateCommand {

    private String hqlQuery_;

    public HqlQueryCommand(S2SessionFactory s2sessionFactory, Class beanClass, Method method) {
        super(s2sessionFactory, beanClass, method);
    }

    public Object execute(Object[] args) {
        Query query = getSession().createQuery(hqlQuery_);

        List ret = queryExecute(query, args);
        return getReturnObject(getMethod(), ret);
    }

    public void setHqlQuery(String hqlQuery) {
        hqlQuery_ = hqlQuery;
    }
}
