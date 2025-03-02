package eu.geniusgamedev.StepLink.metadata.search;

import eu.geniusgamedev.StepLink.metadata.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserSpecification extends EntitySpecification<User> {
    public UserSpecification(String searchValue) {
        super(searchValue);
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.or(
                criteriaBuilder.like(root.get("name"), searchValue),
                criteriaBuilder.like(root.get("lastName"), searchValue)
        );
    }

}
