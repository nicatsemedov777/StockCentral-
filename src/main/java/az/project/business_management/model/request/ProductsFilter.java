package az.project.business_management.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductsFilter {
    String name;
    String code;
    String colour;
    Double cost;
    String otherInfos;
    String organisationId;
}
