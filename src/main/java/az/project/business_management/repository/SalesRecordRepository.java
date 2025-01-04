package az.project.business_management.repository;

import az.project.business_management.entity.SalesRecord;
import az.project.business_management.model.request.RecordsFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface SalesRecordRepository extends JpaRepository<SalesRecord, String>, JpaSpecificationExecutor<SalesRecord> {


    @Modifying
    @Query("""
    UPDATE SalesRecord sr 
    SET 
        sr.totalSale = sr.totalSale - :quantityRefunded * sr.sellingPrice,
        sr.totalProfit = (sr.sellingPrice - sr.product.cost) * (sr.quantityOfProductSold - :quantityRefunded),
        sr.quantityOfProductSold = sr.quantityOfProductSold - :quantityRefunded
    WHERE sr.id = :salesRecordId
""")
    void updateSalesRecordDetails(
            @Param("salesRecordId") String salesRecordId,
            @Param("quantityRefunded") Double quantityRefunded
    );
}
