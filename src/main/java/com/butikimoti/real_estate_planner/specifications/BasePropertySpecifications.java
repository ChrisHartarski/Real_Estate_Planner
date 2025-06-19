package com.butikimoti.real_estate_planner.specifications;

import com.butikimoti.real_estate_planner.model.entity.BaseProperty;
import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.model.enums.OfferType;
import com.butikimoti.real_estate_planner.model.enums.PropertyType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BasePropertySpecifications {

    public static Specification<BaseProperty> propertiesPageFilters(
            Company company,
            OfferType offerType,
            PropertyType propertyType,
            String city,
            String neighbourhood,
            String address,
            Double minPrice,
            Double maxPrice
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
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("city")), "%" + city.toLowerCase() + "%"));
            }

            if (neighbourhood != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("neighbourhood")), "%" + neighbourhood + "%"));
            }

            if (address != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), "%" + address + "%"));
            }

            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }

            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
