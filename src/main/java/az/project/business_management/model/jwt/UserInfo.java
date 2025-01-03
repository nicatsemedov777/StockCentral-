package az.project.business_management.model.jwt;

import az.project.business_management.entity.Organisation;
import az.project.business_management.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfo {
    private User user;
    private Organisation organisation;
}
