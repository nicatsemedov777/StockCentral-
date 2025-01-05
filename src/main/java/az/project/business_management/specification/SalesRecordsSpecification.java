package az.project.business_management.specification;

import az.project.business_management.entity.SalesRecord;
import az.project.business_management.model.request.RecordsFilter;
import az.project.business_management.util.DateHelper;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Builder
@Setter
@Getter
public class SalesRecordsSpecification implements Specification<SalesRecord> {

    private RecordsFilter filter;


    @Override
    public Predicate toPredicate(Root<SalesRecord> root, CriteriaQuery<?> query,
                                 CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter == null) {
            cb.and(predicates.toArray(Predicate[]::new));
        }
        if (filter.getOrganisationId() != null) {
            predicates.add(cb.equal(root.get("user").get("organisation").get("id"), filter.getOrganisationId()));
        }
        if (filter.getProductId() != null) {
            predicates.add(cb.equal(root.get("product").get("id"), filter.getProductId()));
        }
        if (filter.getUserId() != null) {
            predicates.add(cb.equal(root.get("user").get("id"), filter.getUserId()));
        }

        if (filter.getMinDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("saleDate"), DateHelper.convertAzeDateToUTC(filter.getMinDate())));
        }

        if (filter.getMaxDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("saleDate"), DateHelper.convertAzeDateToUTC(filter.getMaxDate())));
        }
        return cb.and(predicates.toArray(Predicate[]::new));
    }
}
