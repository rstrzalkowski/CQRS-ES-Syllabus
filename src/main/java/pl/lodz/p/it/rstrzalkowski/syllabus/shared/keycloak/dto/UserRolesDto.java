package pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRolesDto {

    @JsonProperty("realmMappings")
    private List<KeycloakRoleDto> roles;
}
