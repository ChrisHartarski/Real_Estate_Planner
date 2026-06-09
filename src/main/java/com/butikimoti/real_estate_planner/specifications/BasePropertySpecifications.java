package com.butikimoti.real_estate_planner.specifications;

import com.butikimoti.real_estate_planner.model.entity.BaseProperty;
import com.butikimoti.real_estate_planner.model.entity.City;
import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.model.entity.Neighbourhood;
import com.butikimoti.real_estate_planner.model.enums.OfferType;
import com.butikimoti.real_estate_planner.model.enums.PropertyType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BasePropertySpecifications {

    public static Specification<BaseProperty> propertiesPageFilters(
            Company company,
            OfferType offerType,
            PropertyType propertyType,
            City city,
            List<Neighbourhood> neighbourhoods,
            String contactPhone,
            Double minPrice,
            Double maxPrice,
            boolean isArchived
    ) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("ownerCompany"), company));

            if(offerType != null) {
                predicates.add(criteriaBuilder.equal(root.get("offerType"), offerType));
            }

            if (propertyType != null) {
                predicates.add(criteriaBuilder.equal(root.get("propertyType"), propertyType));
            }

            if (city != null) {
                predicates.add(criteriaBuilder.equal(root.get("city"), city));
            }

            if (neighbourhoods != null && !neighbourhoods.isEmpty()) {
                List<Neighbourhood> filtered = neighbourhoods.stream()
                        .filter(Objects::nonNull)
                        .toList();

                if (!filtered.isEmpty()) {
                    predicates.add(root.get("neighbourhood").in(filtered));
                }
            }

            if (contactPhone != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("contactPhone")), "%" + contactPhone + "%"));
            }

            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }

            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            predicates.add(criteriaBuilder.equal(root.get("isArchived"), isArchived));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
