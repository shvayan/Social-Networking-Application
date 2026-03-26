package org.example.helloapp.repository.searchStrategies;

import jakarta.persistence.criteria.*;

import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;

public class GenericSpecification<T> implements Specification<T> {

    private final SearchCriteria criteria;

    public GenericSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public  Predicate toPredicate(@NonNull Root<T> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {

        Path<?> path = getPath(root, criteria.getKey());



        switch (criteria.getOperation()) {

            case ">":
                return cb.greaterThan(path.as(String.class), criteria.getValue().toString());

            case "<":
                return cb.lessThan(path.as(String.class), criteria.getValue().toString());

            case ":":
                if (criteria.getValue() == null) {
                    return null;
                }

                if (path.getJavaType() == String.class) {
                    String value = criteria.getValue().toString().toLowerCase();

                    return cb.like(
                            cb.lower(path.as(String.class)),
                            "%" + value + "%"
                    );
                }

                return null;
            case "=":
                return cb.equal(path, criteria.getValue());
            default:
                return null;
        }
    }


    private Path<?> getPath(Root<T> root, String key) {

        String[] parts = key.split("\\.");

        From<?, ?> join = root;

        for (int i = 0; i < parts.length - 1; i++) {
            join = join.join(parts[i]);
        }

        return join.get(parts[parts.length - 1]);
    }
}