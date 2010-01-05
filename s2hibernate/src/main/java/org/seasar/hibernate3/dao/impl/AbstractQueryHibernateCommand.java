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

import org.seasar.hibernate3.S2SessionFactory;

/**
 * @author kenichi_okazaki
 * 
 */
public abstract class AbstractQueryHibernateCommand extends AbstractHibernateCommand {

    private Class beanClass_;
    private Method method_;
    private ArgsMetaData argsMeta_;

    public AbstractQueryHibernateCommand(S2SessionFactory s2sessionFactory, Class beanClass,
            Method method) {
        super(s2sessionFactory);
        beanClass_ = beanClass;
        method_ = method;

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

    public Method getMethod() {
        return method_;
    }

    public void setMethod(Method method) {
        method_ = method;
    }

    public Class getBeanClass() {
        return beanClass_;
    }

    public void setBeanClass(Class beanClass) {
        beanClass_ = beanClass;
    }

    public ArgsMetaData getArgsMeta() {
        return argsMeta_;
    }

    public void setArgsMeta(ArgsMetaData argsMeta) {
        argsMeta_ = argsMeta;
    }
}
