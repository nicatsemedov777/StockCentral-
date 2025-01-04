package az.project.business_management.service;

import az.project.business_management.entity.SalesRecord;
import az.project.business_management.entity.TurnoverHistory;
import az.project.business_management.model.jwt.UserInfo;
import az.project.business_management.repository.TurnoverHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class TurnoverService {

    private final TurnoverHistoryRepository turnoverHistoryRepository;
    private final UserInfo userInfo;

    @Transactional
    public TurnoverHistory updateDailyTurnover(SalesRecord salesRecord) {
        ZonedDateTime nowUtc = ZonedDateTime.now(ZoneOffset.UTC);

        LocalDate todayInAzerbaijan = nowUtc.withZoneSameInstant(ZoneId.of("Asia/Baku")).toLocalDate();


        TurnoverHistory turnoverHistory = turnoverHistoryRepository.findByLocalDate(todayInAzerbaijan).orElse(null);
        if (turnoverHistory == null) {
            turnoverHistory = buildTurnoverHistory(salesRecord.getTotalSale(), todayInAzerbaijan);
            turnoverHistoryRepository.save(turnoverHistory);
            return turnoverHistory;

        }
        var updatedTurnover = turnoverHistory.getTotalTurnover() + salesRecord.getTotalSale();
        turnoverHistory.setTotalTurnover(updatedTurnover);
        turnoverHistoryRepository.save(turnoverHistory);
        return turnoverHistory;
    }

    public Double getDailyTurnover() {
        ZonedDateTime nowUtc = ZonedDateTime.now(ZoneOffset.UTC);

        LocalDate todayInAzerbaijan = nowUtc.withZoneSameInstant(ZoneId.of("Asia/Baku")).toLocalDate();
        TurnoverHistory turnoverHistory = turnoverHistoryRepository.findByLocalDate(todayInAzerbaijan).orElse(null);
        if (turnoverHistory == null) {
            turnoverHistory = turnoverHistoryRepository.save(buildTurnoverHistory(0.0, todayInAzerbaijan));
        }
        return turnoverHistory.getTotalTurnover();

    }

    private TurnoverHistory buildTurnoverHistory(Double totalSale, LocalDate today) {
        return TurnoverHistory.builder()
                .totalTurnover(totalSale)
                .localDate(today)
                .organisation(userInfo.getOrganisation())
                .build();
    }
}