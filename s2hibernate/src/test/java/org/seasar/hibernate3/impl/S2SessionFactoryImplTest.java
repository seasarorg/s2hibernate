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

import java.util.List;

import org.hibernate.Session;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.framework.container.impl.PropertyDefImpl;

public class S2SessionFactoryImplTest extends S2TestCase {

    private static final String CONFIG_PATH = "org/seasar/hibernate3/impl/hibernate.cfg.xml";
    private S2SessionFactoryImpl s2SessionFactory_;

    public S2SessionFactoryImplTest(String arg0) {
        super(arg0);
    }

    protected void setUp() throws Exception {
        super.setUp();
        include("j2ee.dicon");
        ComponentDef cd = new ComponentDefImpl(S2SessionFactoryImpl.class, "s2SessionFactory");
        cd.addPropertyDef(new PropertyDefImpl("configPath", CONFIG_PATH));
        register(cd);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetSessionFactory() {
        assertNotNull("1", s2SessionFactory_.getSessionFactory());
    }

    public void testGetSession() throws Exception {
        Session session = s2SessionFactory_.getSession();
        try {
            assertNotNull("1", session);
            assertEquals("2", 0, s2SessionFactory_.getTxSessionSize());
        }
        finally {
            session.close();
        }

    }

    public void testGetSessionTx() throws Exception {
        Session session = s2SessionFactory_.getSession();
        Session session2 = s2SessionFactory_.getSession();
        assertSame("1", session, session2);

        List result = session.createQuery("from Emp where empno = ?").setInteger(0, 7900).list();
        assertEquals("2", 1, result.size());
        Emp emp = (Emp) result.get(0);
        emp.setEname("JAMES");
    }
}