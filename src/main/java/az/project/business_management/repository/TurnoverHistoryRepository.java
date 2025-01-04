package az.project.business_management.repository;

import az.project.business_management.entity.TurnoverHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Repository
public interface TurnoverHistoryRepository extends JpaRepository<TurnoverHistory, String> {
    Optional<TurnoverHistory> findByLocalDate(LocalDate date);

    @Modifying
    @Query("""
    UPDATE TurnoverHistory th 
    SET 
        th.totalTurnover = th.totalTurnover - :totalRefundedAmount
    WHERE th.id = (
        SELECT sr.turnoverHistory.id FROM SalesRecord sr WHERE sr.id = :salesRecordId
    )
""")
    void updateTurnoverHistory(
            @Param("salesRecordId") String salesRecordId,
            @Param("totalRefundedAmount") Double totalRefundedAmount
    );
}
