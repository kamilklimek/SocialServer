package eu.geniusgamedev.StepLink.metadata.search;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class SpecificationProxy<T> implements Specification<T> {
    private final EntitySpecification specification;

    public SpecificationProxy(EntitySpecification specification) {
        this.specification = specification;
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        if (specification.isEmpty()) {
            criteriaBuilder.conjunction();
        }

        return specification.toPredicate(root, criteriaQuery, criteriaBuilder);
    }
}
