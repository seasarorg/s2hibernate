package org.seasar.hibernate3.dao.criteria;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

/**
 * @author kenichi_okazaki
 */
public class EqCriteriaCommand extends abstractCriteriaCommand {

    public EqCriteriaCommand(String fieldName, String dtoFieldName) {
        super(fieldName, dtoFieldName);
    }

    public Criteria getCriteria(Criteria criteria, Object value) {

        return criteria.add(Expression.eq(fieldName_, value));
    }
}
