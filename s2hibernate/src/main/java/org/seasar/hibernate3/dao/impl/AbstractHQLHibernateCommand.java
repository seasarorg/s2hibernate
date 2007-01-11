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

import java.lang.reflect.Method;
import java.util.List;

import org.hibernate.Query;
import org.seasar.hibernate3.S2SessionFactory;

/**
 * @author kenichi_okazaki
 * 
 */
public abstract class AbstractHQLHibernateCommand extends AbstractQueryHibernateCommand {

    public AbstractHQLHibernateCommand(S2SessionFactory s2sessionFactory, Class beanClass,
            Method method) {
        super(s2sessionFactory, beanClass, method);

    }

    protected List queryExecute(Query query, Object[] args) {
        ArgsMetaData argsMeta = getArgsMeta();

        for (int i = 0; i < argsMeta.getArgsCount(); ++i) {

            Object value = argsMeta.getValue(args, i);
            String name = argsMeta.getArgument(i).getFieldName();
            if (value != null) {
                if (name.equals("firstResult") == true) {
                    query.setFirstResult(((Integer) value).intValue());

                }
                else if (name.equals("maxResults") == true) {
                    query.setMaxResults(((Integer) value).intValue());

                }
                else {
                    if (value instanceof List) {
                        query.setParameterList(name, (List) value);

                    }
                    else {
                        query.setParameter(name, value);
                    }
                }
            }
        }

        return query.list();

    }

    protected Object getReturnObject(Method method, List ret) {
        if (method.getReturnType().isAssignableFrom(List.class)) {
            return ret;
        }
        else {
            if (ret == null || ret.size() == 0) {
                return null;
            }
            else {
                return ret.get(0);
            }
        }
    }
}
