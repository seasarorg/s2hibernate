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
package org.seasar.hibernate3.impl;

import java.sql.Connection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import javax.transaction.Synchronization;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

import org.hibernate.FlushMode;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.seasar.extension.jdbc.util.ConnectionUtil;
import org.seasar.extension.jdbc.util.DataSourceUtil;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.framework.util.TransactionManagerUtil;
import org.seasar.framework.util.TransactionUtil;
import org.seasar.hibernate3.S2SessionFactory;

/**
 * @author higa
 * @author koichik‚³‚ñ
 * @author ‚¤‚ç‚à‚Æ‚³‚ñ
 * @author kenichi_okazaki
 */
public final class S2SessionFactoryImpl implements S2SessionFactory {

    public static final String interceptor_BINDING = "bindingType=may";
    private static final String DEFAULT_CONFIG_PATH = "hibernate.cfg.xml";

    private final TransactionManager transactionManager_;
    private final DataSource dataSource_;
    private String configPath_ = DEFAULT_CONFIG_PATH;
    private Interceptor interceptor_;
    private SessionFactory sessionFactory_;
    private final Map txSessions_ = Collections.synchronizedMap(new HashMap());

    public S2SessionFactoryImpl(TransactionManager transactionManager, DataSource dataSource) {
        transactionManager_ = transactionManager;
        dataSource_ = dataSource;
    }

    public TransactionManager getTransactionManager() {
        return transactionManager_;
    }

    public DataSource getDataSource() {
        return dataSource_;
    }

    public String getConfigPath() {
        return configPath_;
    }

    public void setConfigPath(String configPath) {
        configPath_ = configPath;
    }

    public void setInterceptor(Interceptor interceptor) {
        this.interceptor_ = interceptor;
    }

    public synchronized SessionFactory getSessionFactory() {
        if (sessionFactory_ != null) {
            return sessionFactory_;
        }

        Configuration cfg = new Configuration();
        cfg.configure(ResourceUtil.getResource(configPath_));
        if (interceptor_ != null) {
            cfg.setInterceptor(interceptor_);
        }
        sessionFactory_ = cfg.buildSessionFactory();
        return sessionFactory_;
    }

    public int getTxSessionSize() {
        return txSessions_.size();
    }

    public Session getSession() {
        Transaction tx = getTransaction();
        if (tx == null) {
            return createSession();
        }
        Session session = (Session) txSessions_.get(tx);
        if (session != null && session.isOpen()) {
            return session;
        }
        return bindSession(tx);
    }

    private Transaction getTransaction() {
        return TransactionManagerUtil.getTransaction(getTransactionManager());
    }

    private Session createSession() {
        return getSessionFactory().openSession(getConnection());
    }

    private Connection getConnection() {
        return DataSourceUtil.getConnection(getDataSource());
    }

    private Session bindSession(Transaction tx) {
        Session session = (Session) txSessions_.get(tx);
        if (session != null && session.isOpen()) {
            return session;
        }
        session = createSession();
        txSessions_.put(tx, session);
        TransactionUtil.registerSynchronization(tx, new SynchronizationImpl(tx));
        return session;
    }

    private void closeSession(Transaction tx) {
        Session session = (Session) txSessions_.remove(tx);
        if (session == null || !session.isOpen()) {
            return;
        }
        try {
            session.clear();
        }
        finally {
            ConnectionUtil.close(session.close());
        }
    }

    private void flushSession(Transaction tx) {
        Session session = (Session) txSessions_.get(tx);
        if (session == null || !session.isOpen() || session.getFlushMode().equals(FlushMode.NEVER)) {
            return;
        }
        session.flush();
    }

    private class SynchronizationImpl implements Synchronization {

        private Transaction tx_;

        public SynchronizationImpl(Transaction tx) {
            tx_ = tx;
        }

        public void beforeCompletion() {
            flushSession(tx_);
        }

        public void afterCompletion(int arg0) {
            closeSession(tx_);
        }
    }
}
