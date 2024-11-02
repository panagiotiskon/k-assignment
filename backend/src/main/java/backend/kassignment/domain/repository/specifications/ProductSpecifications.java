package backend.kassignment.domain.repository.specifications;

import backend.kassignment.domain.Product;
import backend.kassignment.web.requests.ProductSearchFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProductSpecifications {

    public static Specification<Product> searchFilterSpecification(List<ProductSearchFilter> productSearchFilterList) {
        return (Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            for (ProductSearchFilter productSearchFilter : productSearchFilterList) {
                String columnName = productSearchFilter.getColumnName();
                Object columnValue = productSearchFilter.getColumnValue();

                switch (columnName) {
                    case "company":
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("company"),columnValue ));
                        break;
                    case "minPrice":
                        if (columnValue instanceof Double) {
                            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("price"), (Double) columnValue));
                        }
                        break;
                    case "maxPrice":
                        if (columnValue instanceof Double) {
                            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("price"), (Double) columnValue));
                        }
                        break;
                    default:
                        break;
                }
            }
            return predicate;
        };
    }
}
