package pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtResponse {
    private String token;

    @JsonProperty("access_token")
    private void getToken(String accessToken) {
        token = accessToken;
    }
}