package org.example.helloapp.repository.searchStrategies;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCriteria {

    private String key;      // field name (e.g., name, price)
    private String operation; // =, >, <, like
    private Object value;
    private String condition; // AND, OR

    public SearchCriteria(String key, String operation, Object value,String condition) {
        this.key = key;
        this.operation = operation;
        this.value = value;
        this.condition = condition;
    }
}