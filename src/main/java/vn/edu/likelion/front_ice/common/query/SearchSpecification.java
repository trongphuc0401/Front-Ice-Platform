package vn.edu.likelion.front_ice.common.query;

import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class SearchSpecification<T> implements Specification<T> {

    private static final long serialVersionUID = -9153865343320750644L;
    private final transient SearchRequest request;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate predicate = cb.equal(cb.literal(Boolean.TRUE), Boolean.TRUE);

        for (FilterRequest filter : this.request.getFilters()) {
            // Sử dụng resolveExpression để xử lý nested attributes
            Expression<?> expression = resolveExpression(root, filter.getKey());
            predicate = filter.getOperator().build(expression, cb, filter, predicate);
        }

        // Sắp xếp (sort)
        List<Order> orders = new ArrayList<>();
        for (SortRequest sort : this.request.getSorts()) {
            Expression<?> expression = resolveExpression(root, sort.getKey());
            orders.add(sort.getDirection().build(expression, cb));
        }

        query.orderBy(orders);
        return predicate;
    }

    /**
     * Phương thức để xử lý nested attributes (các thuộc tính phân cấp).
     * Ví dụ: "category.title" hoặc "challengePoint.level"
     */
    private Expression<?> resolveExpression(Root<T> root, String key) {
        if (key.contains(".")) {
            return resolveJoinPath(root, key);
        } else {
            return root.get(key);
        }
    }

    /**
     * Xử lý join đến các thuộc tính phân cấp.
     * Ví dụ: "category.title" hoặc "challengePoint.level"
     */
    private Expression<?> resolveJoinPath(Root<T> root, String key) {
        String[] parts = key.split("\\.");
        From<?, ?> from = root;

        // Lặp qua các phần của thuộc tính để join các thực thể
        for (int i = 0; i < parts.length - 1; i++) {
            from = from.join(parts[i], JoinType.LEFT); // Sử dụng LEFT JOIN
        }

        return from.get(parts[parts.length - 1]);
    }

    public static Pageable getPageable(Integer page, Integer size) {
        return PageRequest.of(Objects.requireNonNullElse(page, 0), Objects.requireNonNullElse(size, 10));
    }
}