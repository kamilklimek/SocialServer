package eu.geniusgamedev.StepLink.metadata.search;

import eu.geniusgamedev.StepLink.metadata.entity.Event;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class EventSpecification extends EntitySpecification<Event> {
    public EventSpecification(String searchValue) {
        super(searchValue);
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.or(
                criteriaBuilder.like(root.get("name"), searchValue),
                criteriaBuilder.like(root.get("description"), searchValue)
        );
    }
}
