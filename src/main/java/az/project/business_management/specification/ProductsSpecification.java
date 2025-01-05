package az.project.business_management.specification;

import az.project.business_management.entity.Product;
import az.project.business_management.model.request.ProductsFilter;
import az.project.business_management.util.DateHelper;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ProductsSpecification implements Specification<Product> {
    private ProductsFilter filter;
    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (filter == null) {
            cb.and(predicates.toArray(Predicate[]::new));
        }
        if (filter.getCode() != null) {
            predicates.add(cb.like(cb.lower(root.get("code")), prepareSearchText(filter.getCode())));
        }
        if (filter.getColour() != null) {
            predicates.add(cb.like(cb.lower(root.get("colour")), prepareSearchText(filter.getColour())));
        }
        if (filter.getName() != null) {
            predicates.add(cb.like(cb.lower(root.get("name")), prepareSearchText(filter.getName())));
        }

        if (filter.getCost() != null) {
            predicates.add(cb.equal(root.get("cost"), filter.getCost()));
        }

        if (filter.getOtherInfos() != null) {
            predicates.add(cb.like(cb.lower(root.get("otherInfos")),filter.getOtherInfos()));
        }
        return cb.and(predicates.toArray(Predicate[]::new));
    }
    private String prepareSearchText(String searchText) {
        return "%" + searchText.toLowerCase() + "%";
    }

}
