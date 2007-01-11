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
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.seasar.hibernate3.S2SessionFactory;
import org.seasar.hibernate3.dao.criteria.EqCriteriaCommand;
import org.seasar.hibernate3.dao.criteria.FirstResultCriteriaCommand;
import org.seasar.hibernate3.dao.criteria.GeCriteriaCommand;
import org.seasar.hibernate3.dao.criteria.GtCriteriaCommand;
import org.seasar.hibernate3.dao.criteria.InCriteriaCommand;
import org.seasar.hibernate3.dao.criteria.LeCriteriaCommand;
import org.seasar.hibernate3.dao.criteria.LikeCriteriaCommand;
import org.seasar.hibernate3.dao.criteria.LtCriteriaCommand;
import org.seasar.hibernate3.dao.criteria.MaxResultsCriteriaCommand;
import org.seasar.hibernate3.dao.criteria.OrderByCriteriaCommand;

/**
 * @author kenichi_okazaki
 */
public abstract class AbstractAutoQueryCommand extends AbstractQueryHibernateCommand {

    private String[] eagerFields_;
    protected List criteriaCommandList_ = new ArrayList();

    public AbstractAutoQueryCommand(S2SessionFactory s2sessionFactory, Class beanClass,
            Method method) {
        super(s2sessionFactory, beanClass, method);

    }

    public Object execute(Object[] args) {
        List ret = null;

        Criteria criteria = getArgsCriteria(getSession(), args);

        criteria = setEagerFetch(criteria);
        ret = criteria.list();

        return getReturnObject(getMethod(), ret);

    }

    protected abstract Criteria getArgsCriteria(Session session, Object[] args);

    private Criteria setEagerFetch(Criteria criteria) {
        if (eagerFields_ != null) {
            for (int i = 0; i < eagerFields_.length; i++) {
                criteria.setFetchMode(eagerFields_[i], FetchMode.JOIN);
            }
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        }
        return criteria;
    }

    public void setEagerFields(String[] eagerFields) {
        eagerFields_ = eagerFields;
    }

    public void buildCriterionList() {
        ArgsMetaData argsMeta = getArgsMeta();

        for (int i = 0; i < argsMeta.getArgsCount(); i++) {

            Argument args = argsMeta.getArgument(i);
            String fieldName = args.getFieldName();
            String expression = args.getExpression();
            String dtoFieldName = args.getDtoFieldName();

            if (expression.equals("")) {
                if (fieldName.equals("orderBy")) {
                    criteriaCommandList_.add(new OrderByCriteriaCommand(fieldName, dtoFieldName));

                }
                else if (fieldName.equals("firstResult")) {
                    criteriaCommandList_
                            .add(new FirstResultCriteriaCommand(fieldName, dtoFieldName));

                }
                else if (fieldName.equals("maxResults")) {
                    criteriaCommandList_
                            .add(new MaxResultsCriteriaCommand(fieldName, dtoFieldName));

                }
                else {
                    criteriaCommandList_.add(new EqCriteriaCommand(fieldName, dtoFieldName));
                }
            }
            else if (expression.equals("=")) {
                criteriaCommandList_.add(new EqCriteriaCommand(fieldName, dtoFieldName));

            }
            else if (expression.equals(">")) {
                criteriaCommandList_.add(new GtCriteriaCommand(fieldName, dtoFieldName));

            }
            else if (expression.equals(">=")) {
                criteriaCommandList_.add(new GeCriteriaCommand(fieldName, dtoFieldName));

            }
            else if (expression.equals("<")) {
                criteriaCommandList_.add(new LtCriteriaCommand(fieldName, dtoFieldName));

            }
            else if (expression.equals("<=")) {
                criteriaCommandList_.add(new LeCriteriaCommand(fieldName, dtoFieldName));

            }
            else if (expression.equals("like")) {
                criteriaCommandList_.add(new LikeCriteriaCommand(fieldName, dtoFieldName));

            }
            else if (expression.equals("in")) {
                criteriaCommandList_.add(new InCriteriaCommand(fieldName, dtoFieldName));

            }
        }
    }
}