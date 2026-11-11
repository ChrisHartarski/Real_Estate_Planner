package com.butikimoti.real_estate_planner.specifications;

import com.butikimoti.real_estate_planner.model.dto.util.filter.PropertyFilter;
import com.butikimoti.real_estate_planner.model.entity.Apartment;
import com.butikimoti.real_estate_planner.model.entity.BaseProperty;
import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.model.entity.House;
import com.butikimoti.real_estate_planner.model.enums.OfferType;
import com.butikimoti.real_estate_planner.model.enums.PropertyType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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

            // Apartment type
            if (filter.getApartmentType() != null) {
                Root<Apartment> apartment = criteriaBuilder.treat(root, Apartment.class);

                predicates.add(criteriaBuilder.equal(
                        apartment.get("apartmentType"),
                        filter.getApartmentType()
                ));
            }

            // House type
            if (filter.getHouseType() != null) {
                Root<House> house = criteriaBuilder.treat(root, House.class);

                predicates.add(criteriaBuilder.equal(
                        house.get("houseType"),
                        filter.getHouseType()
                ));
            }

            // Construction type
            if (filter.getConstructionType() != null) {
                Root<Apartment> apartment = criteriaBuilder.treat(root, Apartment.class);
                Root<House> house = criteriaBuilder.treat(root, House.class);

                Predicate apartmentConstruction = criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("propertyType"), PropertyType.APARTMENT),
                        criteriaBuilder.equal(
                                apartment.get("constructionType"),
                                filter.getConstructionType()
                        )
                );

                Predicate houseConstruction = criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("propertyType"), PropertyType.HOUSE),
                        criteriaBuilder.equal(
                                house.get("constructionType"),
                                filter.getConstructionType()
                        )
                );

                predicates.add(criteriaBuilder.or(
                        apartmentConstruction,
                        houseConstruction
                ));
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
