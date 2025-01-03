package az.project.business_management.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SalesRecordsResponse {
    String salesRecordId;
    String productId;
    Double sellingPriceOfProduct;
    Double quantityOfProductSold;
    String username;
    Long saleDate;


}
