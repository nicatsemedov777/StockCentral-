package az.project.business_management.repository;

import az.project.business_management.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
    Page<Product> findAllByOrganisationId(String id, Pageable pageable);
    @Modifying
    @Query("""
    UPDATE Product p 
    SET 
        p.quantity = p.quantity + :quantityRefunded
    WHERE p.id = (
        SELECT sr.product.id FROM SalesRecord sr WHERE sr.id = :salesRecordId
    )
""")
    void updateProductQuantity(
            @Param("salesRecordId") String salesRecordId,
            @Param("quantityRefunded") Double quantityRefunded
    );
}
