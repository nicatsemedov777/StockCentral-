package az.project.business_management.model.response;

import lombok.Builder;

import java.util.Date;

@Builder
public record ProductResponse(String productId,
                              String name,
                              String code,
                              String colour,
                              Double quantity,
                              Double cost,
                              String otherDetails,
                              String organisationName,
                              Long insertedAt

) {
}
