package pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class KeycloakRoleDto {
    private String id;
    private String name;
    private String description;
    private String containerId;
    private boolean composite;
    private boolean clientRole;
}
