package az.project.business_management.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordsFilter {
    private String userId;
    private String productId;
    private Long minDate;
    private Long maxDate;
    private String organisationId;
}
