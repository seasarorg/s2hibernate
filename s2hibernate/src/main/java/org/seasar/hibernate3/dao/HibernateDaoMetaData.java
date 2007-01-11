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
package org.seasar.hibernate3.dao;

import org.seasar.framework.beans.MethodNotFoundRuntimeException;

/**
 * @author kenichi_okazaki
 */
public interface HibernateDaoMetaData {

    public String BEAN_KEY = "BEAN";
    public String PROPERTY_KEY_SUFFIX = "_PROPERTY";
    public String ARGS_KEY_SUFFIX = "_ARGS";
    public String HQL_KEY_SUFFIX = "_HQL";
    public String LOCK_KEY_SUFFIX = "_LOCK";
    public String EAGER_KEY_SUFFIX = "_EAGER";

    public Class getBeanClass();

    public boolean hasHibernateCommand(String methodName);

    public HibernateCommand getHibernateCommand(String methodName)
            throws MethodNotFoundRuntimeException;
}
