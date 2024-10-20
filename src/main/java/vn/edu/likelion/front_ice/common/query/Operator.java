package vn.edu.likelion.front_ice.common.query;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import vn.edu.likelion.front_ice.common.enums.BaseEnum;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public enum Operator {

    EQUAL {
        @Override
        public <T> Predicate build(Expression<?> expression, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
            Object value = request.getFieldType().parse(request.getValue().toString());

            if (BaseEnum.class.isAssignableFrom(expression.getJavaType())) {
                value = getEnumValue((Class<BaseEnum<?>>) expression.getJavaType(), value.toString());
            }

            return cb.and(cb.equal(expression, value), predicate);
        }
    },

    NOT_EQUAL {
        @Override
        public <T> Predicate build(Expression<?> expression, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
            Object value = request.getFieldType().parse(request.getValue().toString());

            if (BaseEnum.class.isAssignableFrom(expression.getJavaType())) {
                value = getEnumValue((Class<BaseEnum<?>>) expression.getJavaType(), value.toString());
            }

            return cb.and(cb.notEqual(expression, value), predicate);
        }
    },

    LIKE {
        @Override
        public <T> Predicate build(Expression<?> expression, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
            Object value = request.getFieldType().parse(request.getValue().toString());

            return cb.and(cb.like(cb.upper((Expression<String>) expression), "%" + value.toString().toUpperCase() + "%"), predicate);
        }
    },

    IN {
        @Override
        public <T> Predicate build(Expression<?> expression, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
            List<Object> values = request.getValues();
            CriteriaBuilder.In<Object> inClause = cb.in(expression);
            for (Object value : values) {
                if (BaseEnum.class.isAssignableFrom(expression.getJavaType())) {
                    value = getEnumValue((Class<BaseEnum<?>>) expression.getJavaType(), value.toString());
                }
                inClause.value(value);
            }
            return cb.and(inClause, predicate);
        }
    },

    BETWEEN {
        @Override
        public <T> Predicate build(Expression<?> expression, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
            Object value = request.getFieldType().parse(request.getValue().toString());
            Object valueTo = request.getFieldType().parse(request.getValueTo().toString());

            if (request.getFieldType() == FieldType.DATE) {
                LocalDateTime startDate = (LocalDateTime) value;
                LocalDateTime endDate = (LocalDateTime) valueTo;
                return cb.and(cb.and(cb.greaterThanOrEqualTo((Expression<LocalDateTime>) expression, startDate), cb.lessThanOrEqualTo((Expression<LocalDateTime>) expression, endDate)), predicate);
            }

            if (request.getFieldType() != FieldType.CHAR && request.getFieldType() != FieldType.BOOLEAN) {
                Number start = (Number) value;
                Number end = (Number) valueTo;
                return cb.and(cb.and(cb.ge((Expression<Number>) expression, start), cb.le((Expression<Number>) expression, end)), predicate);
            }

            log.info("Cannot use BETWEEN for {} field type.", request.getFieldType());
            return predicate;
        }
    };

    @SuppressWarnings("unchecked")
    private static <E extends BaseEnum<?>> E getEnumValue(Class<E> enumType, String value) {
        try {
            return (E) enumType.getEnumConstants()[0].fromValue(value);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid enum value: " + value, e);
        }
    }

    // Điều chỉnh phương thức abstract để sử dụng Expression thay vì Root
    public abstract <T> Predicate build(Expression<?> expression, CriteriaBuilder cb, FilterRequest request, Predicate predicate);
}
