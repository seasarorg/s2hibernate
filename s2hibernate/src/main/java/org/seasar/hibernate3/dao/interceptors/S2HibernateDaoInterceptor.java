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
package org.seasar.hibernate3.dao.interceptors;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.util.MethodUtil;
import org.seasar.hibernate3.dao.HibernateCommand;
import org.seasar.hibernate3.dao.HibernateDaoMetaData;
import org.seasar.hibernate3.dao.HibernateDaoMetaDataFactory;

/**
 * @author kenichi_okazaki
 */
public class S2HibernateDaoInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = 1L;

    private HibernateDaoMetaDataFactory hibernateDaoMetaDataFactory_;

    public S2HibernateDaoInterceptor(HibernateDaoMetaDataFactory hibernateDaoMetaDataFactory) {
        hibernateDaoMetaDataFactory_ = hibernateDaoMetaDataFactory;
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        if (!MethodUtil.isAbstract(method)) {
            return invocation.proceed();
        }
        Class daoClass = getTargetClass(invocation);
        HibernateDaoMetaData dmd = hibernateDaoMetaDataFactory_.getDaoMetaData(daoClass);
        HibernateCommand cmd = dmd.getHibernateCommand(method.getName());

        return cmd.execute(invocation.getArguments());
    }
}
