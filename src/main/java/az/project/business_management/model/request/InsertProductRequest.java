package az.project.business_management.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated

public record InsertProductRequest(@NotEmpty String productName,
                                   @NotEmpty String productCode,
                                   @NotEmpty String colour,
                                   @NotNull Double quantity,
                                   @NotNull Double cost,
                                   String otherDetails
                                   ) {
}
