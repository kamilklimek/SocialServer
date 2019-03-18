package eu.geniusgamedev.StepLink.metadata.search;

import org.springframework.data.jpa.domain.Specification;

public abstract class EntitySpecification<T> implements Specification<T> {
    protected final String searchValue;

    protected EntitySpecification(String searchValue) {
        this.searchValue = addLikeMarks(searchValue);
    }

    private String addLikeMarks(String searchValue) {
        return "%" + searchValue + "%";
    }

    protected boolean isEmpty() {
        return searchValue.isEmpty();
    }
}
