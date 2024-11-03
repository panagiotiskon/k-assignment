package backend.kassignment.domain.repository.specifications;

import backend.kassignment.domain.Product;
import backend.kassignment.web.requests.ProductSearchFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<Product> searchFilterSpecification(ProductSearchFilter filter) {
        return (Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (filter.getCompany() != null && !filter.getCompany().isEmpty()) {
                CriteriaBuilder.In<String> companyPredicate = criteriaBuilder.in(root.get("company"));
                for (String country : filter.getCompany()) {
                    companyPredicate.value(country);
                }
                predicate = criteriaBuilder.and(predicate, companyPredicate);
            }

            if (filter.getMinPrice() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice()));
            }

            if (filter.getMaxPrice() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice()));
            }

            return predicate;
        };
    }
}
