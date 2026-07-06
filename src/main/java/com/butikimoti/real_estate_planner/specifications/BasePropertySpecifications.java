package com.butikimoti.real_estate_planner.specifications;

import com.butikimoti.real_estate_planner.model.dto.util.filter.PropertyFilter;
import com.butikimoti.real_estate_planner.model.entity.BaseProperty;
import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.model.enums.OfferType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class BasePropertySpecifications {

    public static Specification<BaseProperty> propertiesPageFilters(
            Company company,
            OfferType offerType,
            boolean isArchived,
            PropertyFilter filter
    ) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("ownerCompany"), company));

            if(offerType != null) {
                predicates.add(criteriaBuilder.equal(root.get("offerType"), offerType));
            }

            if (filter.getPropertyType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("propertyType"), filter.getPropertyType()));
            }

            if (filter.getCity() != null) {
                predicates.add(criteriaBuilder.equal(root.get("city").get("id"), filter.getCity()));
            }

            if (filter.getNeighbourhoods() != null && !filter.getNeighbourhoods().isEmpty()) {
                List<UUID> filtered = filter.getNeighbourhoods().stream()
                        .filter(Objects::nonNull)
                        .toList();

                if (!filtered.isEmpty()) {
                    predicates.add(root.get("neighbourhood").get("id").in(filtered));
                }
            }

            if (filter.getContactPhone() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("contactPhone")), "%" + filter.getContactPhone() + "%"));
            }

            if (filter.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice()));
            }

            if (filter.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice()));
            }

            predicates.add(criteriaBuilder.equal(root.get("isArchived"), isArchived));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
