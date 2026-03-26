package org.example.helloapp.repository.searchStrategies;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class GenericSpecificationBuilder<T> {

    private final List<SearchCriteria> params = new ArrayList<>();

    public GenericSpecificationBuilder<T> where(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value, "AND"));
        return this;
    }


    public GenericSpecificationBuilder<T> whereOr(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value, "OR"));
        return this;
    }

    public Specification<T> build() {
        if (params.isEmpty()) return null;

        Specification<T> result = new GenericSpecification<>(params.get(0));
        for (int i = 1; i < params.size(); i++) {
            SearchCriteria criteria = params.get(i);

            if ("OR".equalsIgnoreCase(criteria.getCondition())) {
                result = Specification.where(result)
                        .or(new GenericSpecification<>(criteria));
            } else {
                result = Specification.where(result)
                        .and(new GenericSpecification<>(criteria));
            }
        }

        return result;
    }
}