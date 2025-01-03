package az.project.business_management.model.request;

import jakarta.validation.constraints.NotEmpty;


public record LoginRequest(@NotEmpty String username, @NotEmpty String password){
}
