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
package org.seasar.hibernate3.dao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.cfg.Configuration;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.MethodNotFoundRuntimeException;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.FieldUtil;
import org.seasar.framework.util.MethodUtil;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.hibernate3.S2SessionFactory;
import org.seasar.hibernate3.dao.HibernateCommand;
import org.seasar.hibernate3.dao.HibernateDaoMetaData;

/**
 * @author kenichi_okazaki
 */
public class HibernateDaoMetaDataImpl implements HibernateDaoMetaData {

    private static final String[] INSERT_NAMES = new String[] { "insert", "create", "add", "save" };

    private static final String[] UPDATE_NAMES = new String[] { "update", "merge" };

    private static final String[] DELETE_NAMES = new String[] { "delete", "remove" };

    private static final String[] SAVE_OR_UPDATE_NAMES = new String[] { "saveOrUpdate" };

    private static final String[] LOAD_NAMES = new String[] { "load" };

    private Class daoClass_;

    private BeanDesc daoBeanDesc_;

    private Class beanClass_;

    private Map hibernateCommands_ = new HashMap();

    private S2SessionFactory s2sessionFactory_;

    private Map namedQueries_;

    /**
     * @param daoClass
     */
    public HibernateDaoMetaDataImpl(S2SessionFactory s2sessionFactory, Class daoClass) {

        s2sessionFactory_ = s2sessionFactory;

        daoClass_ = daoClass;
        daoBeanDesc_ = BeanDescFactory.getBeanDesc(daoClass);
        Field beanField = daoBeanDesc_.getField(BEAN_KEY);
        beanClass_ = (Class) FieldUtil.get(beanField, null);

        setupHibernateNamedQuerys();

        setupHibernateCommand();

    }

    private void setupHibernateNamedQuerys() {
        Configuration cfg = new Configuration();

        String hibernateConfigPath = s2sessionFactory_.getConfigPath();
        cfg.configure(ResourceUtil.getResource(hibernateConfigPath));
        namedQueries_ = cfg.getNamedQueries();
    }

    private void setupHibernateCommand() {
        String[] names = daoBeanDesc_.getMethodNames();
        for (int i = 0; i < names.length; ++i) {
            Method[] methods = daoBeanDesc_.getMethods(names[i]);
            if (methods.length == 1 && MethodUtil.isAbstract(methods[0])) {
                setupMethod(methods[0]);
            }
        }
    }

    private void setupMethod(Method method) {
        String methodName = method.getName();

        if (isMethod(SAVE_OR_UPDATE_NAMES, methodName)) {
            setupSaveOrUpdateMethodByAuto(method);

        }
        else if (isMethod(INSERT_NAMES, methodName)) {
            setupInsertMethodByAuto(method);

        }
        else if (isMethod(UPDATE_NAMES, methodName)) {
            setupUpdateMethodByAuto(method);

        }
        else if (isMethod(DELETE_NAMES, methodName)) {
            setupDeleteMethodByAuto(method);

        }
        else if (isMethod(LOAD_NAMES, methodName)) {
            setupLoadMethodByAuto(method);

        }
        else {

            setupSelectMethod(method);
        }
    }

    private boolean isMethod(String[] methodNameList, String methodName) {
        for (int i = 0; i < methodNameList.length; ++i) {
            if (methodName.startsWith(methodNameList[i])) {
                return true;
            }
        }
        return false;
    }

    private void setupSelectMethod(Method method) {
        String namedQueryLongPath = daoClass_.getName() + "_" + method.getName();
        String namedQueryShortPath = method.getName();
        String hqlQuery = getQuery(method.getName());

        if (hqlQuery != "") {
            setupSelectMethodByHql(method);
        }
        else if (isNamedQuery(namedQueryLongPath)) {
            setupSelectMethodByNamedQuery(method, namedQueryLongPath);
        }
        else if (isNamedQuery(namedQueryShortPath)) {
            setupSelectMethodByNamedQuery(method, namedQueryShortPath);
        }
        else {
            setupSelectMethodByAuto(method);
        }

    }

    private boolean isNamedQuery(String namedQueryName) {
        return namedQueries_.containsKey(namedQueryName);
    }

    private String[] getArgNames(String methodName) {
        return getSuffixValues(methodName, ARGS_KEY_SUFFIX);
    }

    private String[] getPropertyNames(String methodName) {
        return getSuffixValues(methodName, PROPERTY_KEY_SUFFIX);
    }

    private String getQuery(String methodName) {
        return getSuffixValue(methodName, HQL_KEY_SUFFIX);
    }

    private String getLockMode(String methodName) {
        return getSuffixValue(methodName, LOCK_KEY_SUFFIX);
    }

    private String[] getEager(String methodName) {
        return getSuffixValues(methodName, EAGER_KEY_SUFFIX);
    }

    private String getSuffixValue(String methodName, String suffix) {
        String queryKey = methodName + suffix;
        if (daoBeanDesc_.hasField(queryKey)) {
            Field queryNamesField = daoBeanDesc_.getField(queryKey);
            return (String) FieldUtil.get(queryNamesField, null);
        }
        else {
            return "";
        }
    }

    private String[] getSuffixValues(String methodName, String suffix) {
        String value = getSuffixValue(methodName, suffix);
        if (value.equals("")) {
            return new String[0];
        }
        else {
            // return StringUtil.split(value, " ,");
            return StringUtil.split(value, ",");
        }
    }

    private void setupInsertMethodByAuto(Method method) {
        SaveCommand cmd = new SaveCommand(s2sessionFactory_);
        hibernateCommands_.put(method.getName(), cmd);
    }

    private void setupSaveOrUpdateMethodByAuto(Method method) {
        SaveOrUpdateCommand cmd = new SaveOrUpdateCommand(s2sessionFactory_);
        hibernateCommands_.put(method.getName(), cmd);
    }

    private void setupUpdateMethodByAuto(Method method) {
        UpdateCommand cmd = new UpdateCommand(s2sessionFactory_);
        hibernateCommands_.put(method.getName(), cmd);
    }

    private void setupDeleteMethodByAuto(Method method) {
        DeleteCommand cmd = new DeleteCommand(s2sessionFactory_);
        hibernateCommands_.put(method.getName(), cmd);
    }

    private void setupSelectMethodByHql(Method method) {

        HqlQueryCommand cmd = new HqlQueryCommand(s2sessionFactory_, beanClass_, method);
        // cmd.setArgNames( getArgNames(method.getName()));
        cmd.setArgsMeta(new ArgsMetaData(method, getArgNames(method.getName()), null));

        cmd.setHqlQuery(getQuery(method.getName()));

        hibernateCommands_.put(method.getName(), cmd);
    }

    private void setupSelectMethodByAuto(Method method) {
        String[] argNames = getArgNames(method.getName());
        AbstractAutoQueryCommand cmd;

        cmd = new AutoArgsQueryCommand(s2sessionFactory_, beanClass_, method);
        cmd.setArgsMeta(new ArgsMetaData(method, argNames, getPropertyNames(method.getName())));
        cmd.setEagerFields(getEager(method.getName()));
        cmd.buildCriterionList();

        hibernateCommands_.put(method.getName(), cmd);

    }

    private void setupSelectMethodByNamedQuery(Method method, String namedQueryPath) {

        NamedQueryCommand cmd = new NamedQueryCommand(s2sessionFactory_, beanClass_, method);

        cmd.setArgsMeta(new ArgsMetaData(method, getArgNames(method.getName()), null));
        cmd.setNamedQueryPath(namedQueryPath);
        hibernateCommands_.put(method.getName(), cmd);

    }

    private void setupLoadMethodByAuto(Method method) {
        LoadCommand cmd = new LoadCommand(s2sessionFactory_, beanClass_, method);
        cmd.setLockMode(getLockMode(method.getName()));
        hibernateCommands_.put(method.getName(), cmd);
    }

    public Class getBeanClass() {
        return beanClass_;
    }

    public boolean hasHibernateCommand(String methodName) {
        if ((HibernateCommand) hibernateCommands_.get(methodName) == null) {
            return false;
        }
        else {
            return true;
        }
    }

    public HibernateCommand getHibernateCommand(String methodName)
            throws MethodNotFoundRuntimeException {
        return (HibernateCommand) hibernateCommands_.get(methodName);
    }
}