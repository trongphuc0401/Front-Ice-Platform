package vn.edu.likelion.front_ice.common.query;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;

public enum SortDirection {

    ASC {
        @Override
        public <T> Order build(Expression<?> expression, CriteriaBuilder cb) {
            return cb.asc(expression);
        }
    },

    DESC {
        @Override
        public <T> Order build(Expression<?> expression, CriteriaBuilder cb) {
            return cb.desc(expression);
        }
    };

    public abstract <T> Order build(Expression<?> expression, CriteriaBuilder cb);
}


