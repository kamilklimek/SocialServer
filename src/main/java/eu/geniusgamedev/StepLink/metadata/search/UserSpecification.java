package eu.geniusgamedev.StepLink.metadata.search;

import eu.geniusgamedev.StepLink.metadata.entity.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserSpecification implements Specification<User> {
    private final String searchValue;

    public UserSpecification(String searchValue) {
        this.searchValue = addLikeMarks(searchValue);
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.or(
                criteriaBuilder.like(root.get("name"), searchValue),
                criteriaBuilder.like(root.get("lastName"), searchValue)
        );
    }

    private String addLikeMarks(String searchValue) {
        return "%" + searchValue + "%";
    }
}
