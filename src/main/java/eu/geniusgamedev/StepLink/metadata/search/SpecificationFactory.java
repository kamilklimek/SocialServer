package eu.geniusgamedev.StepLink.metadata.search;

import org.springframework.stereotype.Component;

@Component
public class SpecificationFactory {
    public SpecificationProxy createProxy(EntitySpecification entitySpecification) {
        return new SpecificationProxy(entitySpecification);
    }
}
