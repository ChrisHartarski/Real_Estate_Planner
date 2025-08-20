package com.butikimoti.real_estate_planner.specifications;

import com.butikimoti.real_estate_planner.model.entity.Company;
import com.butikimoti.real_estate_planner.model.entity.UserEntity;
import com.butikimoti.real_estate_planner.model.enums.UserRole;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserEntitySpecifications {

    public static Specification<UserEntity> usersPageFilters(
            String userFirstLastName,
            String userEmail,
            String userCompanyName,
            UserRole userRole
    ) {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (userFirstLastName != null && !userFirstLastName.trim().isEmpty()) {
                predicates.add(cb.like(
                                    cb.lower(
                                            cb.concat(
                                                 cb.concat(root.get("firstName"), " "),
                                                 root.get("lastName")
                                            )
                                    ),
                        "%" + userFirstLastName + "%"
                ));
            }

            if (userEmail != null && !userEmail.trim().isEmpty()) {
                predicates.add(cb.like(
                        cb.lower(root.get("email")),
                        "%" + userEmail + "%"
                ));
            }

            if (userCompanyName != null && !userCompanyName.trim().isEmpty()) {
                Join<UserEntity, Company> companyJoin = root.join("company");
                predicates.add(cb.like(
                        cb.lower(companyJoin.get("name")),
                        "%" + userCompanyName + "%"
                ));
            }

            if (userRole != null && !userRole.toString().isEmpty()) {
                predicates.add(cb.equal(root.get("userRole"), userRole));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}
