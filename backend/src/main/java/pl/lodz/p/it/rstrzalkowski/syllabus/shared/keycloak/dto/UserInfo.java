package pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInfo {
    private String email;

    private List<String> roles;

    @JsonProperty("sub")
    private UUID id;

    @JsonProperty("realm_access")
    private void unpackRolesFromRealmAccess(Map<String, List<String>> realmAccess) {
        roles = realmAccess.get("roles");
    }
}
