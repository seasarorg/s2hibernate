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

import java.util.HashMap;
import java.util.Map;

import org.seasar.hibernate3.S2SessionFactory;
import org.seasar.hibernate3.dao.HibernateDaoMetaData;
import org.seasar.hibernate3.dao.HibernateDaoMetaDataFactory;

/**
 * @author kenichi_okazaki
 */
public class HibernateDaoMetaDataFactoryImpl implements HibernateDaoMetaDataFactory {

    private S2SessionFactory s2sessionFactory_;

    private Map daoMetaDataCache_ = new HashMap();

    public HibernateDaoMetaDataFactoryImpl(S2SessionFactory s2sessionFactory) {
        s2sessionFactory_ = s2sessionFactory;
    }

    public synchronized HibernateDaoMetaData getDaoMetaData(Class daoClass) {
        String key = daoClass.getName();
        HibernateDaoMetaData dmd = (HibernateDaoMetaData) daoMetaDataCache_.get(key);
        if (dmd != null) {
            return dmd;
        }
        dmd = new HibernateDaoMetaDataImpl(s2sessionFactory_, daoClass);
        daoMetaDataCache_.put(key, dmd);
        return dmd;
    }
}