package az.project.business_management.service;

import az.project.business_management.entity.SalesRecord;
import az.project.business_management.model.jwt.UserInfo;
import az.project.business_management.model.request.RecordsFilter;
import az.project.business_management.model.response.SalesRecordsResponse;
import az.project.business_management.repository.SalesRecordRepository;
import az.project.business_management.specification.SalesRecordsSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesRecordService {
    private final SalesRecordRepository salesRecordRepository;
    private final UserInfo userInfo;


    public Page<SalesRecordsResponse> getSalesRecordsPage(RecordsFilter recordsFilter, Pageable pageable) {

        recordsFilter.setOrganisationId(userInfo.getOrganisation().getId());

        SalesRecordsSpecification recordsSpecification = new SalesRecordsSpecification(recordsFilter);

        Page<SalesRecord> recordsPage = salesRecordRepository.findAll(recordsSpecification, pageable);

        List<SalesRecordsResponse> salesRecordsResponseList = recordsPage.get().toList().stream().map(SalesRecordService::buildSalesRecordResponse).toList();

        return new PageImpl<>(salesRecordsResponseList,pageable,salesRecordsResponseList.size());

    }

    private static SalesRecordsResponse buildSalesRecordResponse(SalesRecord salesRecord) {
        return SalesRecordsResponse.builder()
                .salesRecordId(salesRecord.getId())
                .saleDate(salesRecord.getSaleDate().getTime())
                .quantityOfProductSold(salesRecord.getQuantityOfProductSold())
                .sellingPriceOfProduct(salesRecord.getSellingPriceOfProduct())
                .username(salesRecord.getUser().getUsername())
                .productId(salesRecord.getProduct().getId())
                .build();
    }
}
