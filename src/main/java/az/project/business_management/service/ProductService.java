package az.project.business_management.service;

import az.project.business_management.entity.Product;
import az.project.business_management.entity.SalesRecord;
import az.project.business_management.error.exception.ProductIsOutOfStockException;
import az.project.business_management.error.exception.ResourceNotFoundException;
import az.project.business_management.model.jwt.UserInfo;
import az.project.business_management.model.request.InsertProductRequest;
import az.project.business_management.model.request.SellProductRequest;
import az.project.business_management.model.response.ProductResponse;
import az.project.business_management.repository.ProductRepository;
import az.project.business_management.repository.SalesRecordRepository;
import az.project.business_management.util.DateHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserInfo userInfo;
    private final SalesRecordRepository salesRecordRepository;
    private final TurnoverService turnoverService;

    public void insertProduct(InsertProductRequest insertProductRequest) {
        Product product = productBuilder(insertProductRequest);
        productRepository.save(product);
    }

    public void insertProductAsList(List<InsertProductRequest> insertProductRequest) {
        List<Product> products = insertProductRequest.stream().map(this::productBuilder).toList();
        productRepository.saveAll(products);
    }

    public List<ProductResponse> getAll() {
        List<Product> responseList = productRepository.findAllByOrganisationId(userInfo.getOrganisation().getId());
        return responseList.stream().map(this::productResponseMapper).toList();
    }

    private ProductResponse productResponseMapper(Product product) {

        return ProductResponse.builder()
                .productId(product.getId())
                .name(product.getName())
                .code(product.getCode())
                .colour(product.getColour())
                .quantity(product.getQuantity())
                .cost(product.getCost())
                .otherDetails(product.getOtherInfos())
                .organisationName(product.getOrganisation().getName())
                .insertedAt(DateHelper.convertUTCDateToAzeDateWithMillis(product.getCreateDate()))
                .build();
    }


    private Product productBuilder(InsertProductRequest insertProductRequest) {
        return Product.builder()
                .name(insertProductRequest.productName())
                .code(insertProductRequest.productCode())
                .colour(insertProductRequest.colour())
                .quantity(insertProductRequest.quantity())
                .cost(insertProductRequest.cost())
                .otherInfos(insertProductRequest.otherDetails())
                .organisation(userInfo.getOrganisation())
                .build();
    }

    @Transactional
    public void sellProduct(SellProductRequest sellProductRequest) {
        Product product = productRepository.findById(sellProductRequest.productId()).orElseThrow(() ->
                new ResourceNotFoundException("Product not found with this id:" + sellProductRequest.productId()));

        if (product.getQuantity() < sellProductRequest.quantity())
            throw new ProductIsOutOfStockException("The product quantity is not sufficient for the entered quantity.");

        SalesRecord salesRecord = salesRecordRepository.save(buildSalesRecord(sellProductRequest, product));
        product.setQuantity(product.getQuantity() - sellProductRequest.quantity());


        var turnoverHistory = turnoverService.updateDailyTurnover(salesRecord);
        salesRecord.setTurnoverHistory(turnoverHistory);
        salesRecordRepository.save(salesRecord);

        productRepository.save(product);

    }

    private SalesRecord buildSalesRecord(SellProductRequest sellProductRequest, Product product) {
        Double profit = sellProductRequest.sellingPrice() - product.getCost();
        return SalesRecord.builder()
                .product(product)
                .user(userInfo.getUser())
                .sellingPrice(sellProductRequest.sellingPrice())
                .totalProfit(profit * sellProductRequest.quantity())
                .quantityOfProductSold(sellProductRequest.quantity())
                .build();
    }
}
