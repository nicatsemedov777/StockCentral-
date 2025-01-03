package az.project.business_management.repository;

import az.project.business_management.entity.SalesRecord;
import az.project.business_management.model.request.RecordsFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface SalesRecordRepository extends JpaRepository<SalesRecord, String>, JpaSpecificationExecutor<SalesRecord> {
}
