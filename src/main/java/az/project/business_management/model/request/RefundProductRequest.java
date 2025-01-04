package az.project.business_management.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RefundProductRequest(@NotEmpty String salesRecordId,
                                   @NotNull Double quantityOfRefundedProduct) {
}
