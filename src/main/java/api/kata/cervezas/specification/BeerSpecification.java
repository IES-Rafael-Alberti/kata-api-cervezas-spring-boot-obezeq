package api.kata.cervezas.specification;

import api.kata.cervezas.model.Beer;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BeerSpecification {

    public static Specification<Beer> withFilters(
            String name,
            Integer styleId,
            Float minAbv,
            Float maxAbv,
            Float minIbu,
            Float maxIbu) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                ));
            }

            if (styleId != null) {
                predicates.add(criteriaBuilder.equal(root.get("style").get("id"), styleId));
            }

            if (minAbv != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("abv"), minAbv));
            }

            if (maxAbv != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("abv"), maxAbv));
            }

            if (minIbu != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("ibu"), minIbu));
            }

            if (maxIbu != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("ibu"), maxIbu));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
