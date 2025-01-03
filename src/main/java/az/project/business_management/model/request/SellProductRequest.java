package az.project.business_management.model.request;

public record SellProductRequest(String productId,
                                 Double sellingPrice,
                                 Double quantity
                                 ) {
}
