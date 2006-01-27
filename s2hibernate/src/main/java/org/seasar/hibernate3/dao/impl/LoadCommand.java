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

import java.io.Serializable;
import java.lang.reflect.Method;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.seasar.hibernate3.S2SessionFactory;

/**
 * @author kenichi_okazaki
 */
public class LoadCommand extends AbstractHibernateCommand {

    private Class beanClass_;
    private LockMode lockMode_;

    public LoadCommand(S2SessionFactory s2sessionFactory, Class beanClass, Method method) {
        super(s2sessionFactory);
        beanClass_ = beanClass;
    }

    public Object execute(Object[] args) {
        Session session = getSession();
        if (lockMode_ == null) {
            return session.get(beanClass_, (Serializable) args[0]);
        }
        else {
            return session.get(beanClass_, (Serializable) args[0], lockMode_);
        }
    }

    public void setLockMode(String lockMode) {
        final String NONE = "NONE";
        final String READ = "READ";
        final String UPGRADE = "UPGRADE";

        if (lockMode.equals(NONE)) {
            lockMode_ = LockMode.NONE;

        }
        else if (lockMode.equals(READ)) {
            lockMode_ = LockMode.READ;

        }
        else if (lockMode.equals(UPGRADE)) {
            lockMode_ = LockMode.UPGRADE;

        }
        else {
            lockMode_ = null;
        }
    }
}
