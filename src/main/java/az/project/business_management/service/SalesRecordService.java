package az.project.business_management.service;

import az.project.business_management.entity.SalesRecord;
import az.project.business_management.error.exception.ProductIsOutOfStockException;
import az.project.business_management.error.exception.ResourceNotFoundException;
import az.project.business_management.model.jwt.UserInfo;
import az.project.business_management.model.request.RecordsFilter;
import az.project.business_management.model.request.RefundProductRequest;
import az.project.business_management.model.response.SalesRecordsResponse;
import az.project.business_management.repository.ProductRepository;
import az.project.business_management.repository.SalesRecordRepository;
import az.project.business_management.repository.TurnoverHistoryRepository;
import az.project.business_management.specification.SalesRecordsSpecification;
import az.project.business_management.util.DateHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesRecordService {
    private final SalesRecordRepository salesRecordRepository;
    private final UserInfo userInfo;
    private final TurnoverHistoryRepository turnoverHistoryRepository;
    private final ProductRepository productRepository;


    public Page<SalesRecordsResponse> getSalesRecordsPage(RecordsFilter recordsFilter, Pageable pageable) {

        recordsFilter.setOrganisationId(userInfo.getOrganisation().getId());

        SalesRecordsSpecification recordsSpecification = new SalesRecordsSpecification(recordsFilter);

        Page<SalesRecord> recordsPage = salesRecordRepository.findAll(recordsSpecification, pageable);

        List<SalesRecordsResponse> salesRecordsResponseList = recordsPage.get().toList().stream().map(SalesRecordService::buildSalesRecordResponse).toList();

        return new PageImpl<>(salesRecordsResponseList, pageable, salesRecordsResponseList.size());

    }

    private static SalesRecordsResponse buildSalesRecordResponse(SalesRecord salesRecord) {
        return SalesRecordsResponse.builder()
                .salesRecordId(salesRecord.getId())
                .saleDate(DateHelper.convertUTCDateToAzeDateWithMillis(salesRecord.getSaleDate()))
                .quantityOfProductSold(salesRecord.getQuantityOfProductSold())
                .sellingPriceOfProduct(salesRecord.getSellingPrice())
                .username(salesRecord.getUser().getUsername())
                .productId(salesRecord.getProduct().getId())
                .build();
    }

    @Transactional
    public void refundProduct(RefundProductRequest refundProductRequest) {
        SalesRecord salesRecord = salesRecordRepository.findById(refundProductRequest.salesRecordId())
                .orElseThrow(() -> new ResourceNotFoundException("Sale record not found."));

        if (refundProductRequest.quantityOfRefundedProduct() > salesRecord.getQuantityOfProductSold()) {
            throw new ProductIsOutOfStockException("You entered more product to refund than the quantity of sale");
        }

        salesRecordRepository.updateSalesRecordDetails(
                refundProductRequest.salesRecordId(),
                refundProductRequest.quantityOfRefundedProduct()
        );
        productRepository.updateProductQuantity(
                refundProductRequest.salesRecordId(),
                refundProductRequest.quantityOfRefundedProduct()
        );
        turnoverHistoryRepository.updateTurnoverHistory(
                refundProductRequest.salesRecordId(),
                refundProductRequest.quantityOfRefundedProduct()* salesRecord.getSellingPrice()
        );

        if (salesRecord.getQuantityOfProductSold() - refundProductRequest.quantityOfRefundedProduct() == 0) {
            salesRecordRepository.deleteById(refundProductRequest.salesRecordId());
        }
    }

}
